package artifact.modules.common.dao.impl;

import artifact.modules.common.dao.BaseDao;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public abstract class BaseDaoMongoImpl<T> implements BaseDao<T> {


    private Pattern pattern = Pattern.compile("\\S+");
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public void save(T entity) {
        mongoTemplate.save(entity);
    }


    @Override

    public Long update(T entity) {
        String primary = "id";
        Map<String, Object> map = parse(entity);

        Object obj = map.get(primary);
        if (obj == null) {
            throw new RuntimeException("lack of value for primary key:id !");
        }
        Long id = Long.valueOf(String.valueOf(obj));
        map.remove("id");

        Query query = new Query();
        query.addCriteria(Criteria.where(primary).is(id));

        Update update = new Update();
        for (String key : map.keySet()) {
            update.set(key, map.get(key));
        }

        UpdateResult result = mongoTemplate.updateFirst(query, update, getGenericClass());
        return result.getModifiedCount();
    }

    /**
     * 对满足query查询的进行update的更新
     *
     * @param query
     * @param update
     * @author zyw
     */
    @Override
    public Long update(Map<String, Object> query, Map<String, Object> update) {

        Update udt = new Update();
        for (String key : update.keySet()) {
            udt.set(key, update.get(key));
        }

        Query qr = generateQuery(query);
        UpdateResult result = mongoTemplate.updateMulti(qr, udt, getGenericClass());
        return result.getModifiedCount();
    }

    /**
     * 分页条件查询
     *
     * @param para  过滤条件
     * @param index 页码,起始1
     * @param size  分页大小
     * @param order 排序
     * @return
     * @throws Exception
     * @author zyw
     */
    @Override
    public List<T> list(Map<String, Object> para, Integer index, Integer size, String order) {

        //过滤
        Query query = generateQuery(para);
        //分页
        query.skip((index - 1) * size);
        query.limit(size);
        //排序
        if (order != null && order.trim().length() > 1) {

            query.with(generateSort(order));
        }
        List<T> dataList = mongoTemplate.find(query, getGenericClass());

        if (para != null) {
            Long count = count(para);
            para.clear();
            para.put("total", count);
        }
        return dataList;

    }

    /**
     * 查询符合条件的数量
     * zyw
     *
     * @param para
     * @return
     */
    @Override
    public Long count(Map<String, Object> para) {
        Query query = generateQuery(para);

        return mongoTemplate.count(query, getGenericClass());
    }

    /**
     * 查询满足条件数据
     * zyw
     *
     * @return
     */
    @Override
    public List<T> list(Map<String, Object> para, String sort) {

        Query query = generateQuery(para);

        if (sort != null && sort.trim().length() != 0) {
            query.with(generateSort(sort));
        }
        return mongoTemplate.find(query, getGenericClass());
    }

    /**
     * 执行原生查询
     *
     * @param queryTemplate
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> excuteQuery(String queryTemplate, Object... args) {
        if (args != null && args.length > 0) {
            queryTemplate = String.format(queryTemplate, args);
        }
        List<Map> ret = null;
        BasicDBObject query = new BasicDBObject();
        query.put("$eval", queryTemplate);
        Document result = mongoTemplate.getDb().runCommand(query);
        Object obj = result.get("retval");
        if (obj instanceof BasicDBObject) {
            obj = ((BasicDBObject) obj).get("_batch");
            if (obj instanceof BasicDBList) {
                ret = (List<Map>) obj;
            }
        } else {
            Map map = new HashMap<>(1);

            map.put("ret", obj);
            ret = new ArrayList<>(1);
            ret.add(map);

        }
        return ret;
    }

    /**
     * 删除满足条件的实体
     *
     * @param para
     * @throws Exception
     */
    @Override
    public Long delete(Map<String, Object> para) {
        Query query = generateQuery(para);

        DeleteResult result = mongoTemplate.remove(query, getGenericClass());
        return result.getDeletedCount();
    }


    /**
     * 删除实体[数组]
     *
     * @param ids 实体ID或数组
     */
    @Override
    public Long delete(Long... ids) {
        Map para = new HashMap(1);
        para.put("id$in", ids);

        return this.delete(para);
    }

    /**
     * 根据ID查询
     *
     * @param id 实体的主键ID
     */
    @Override
    public T find(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        List<T> dataList = mongoTemplate.find(query, getGenericClass());
        if (dataList == null && dataList.size() != 0) {
            throw new RuntimeException(String.format("no unique record found with id=%s !", id));
        }
        return dataList.get(0);
    }

    /**
     * 根据id查找
     *
     * @param ids
     * @return
     */
    @Override
    public List<T> find(Long... ids) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        List<T> dataList = mongoTemplate.find(query, getGenericClass());

        return dataList;
    }

    /**
     * 根据参数生成query
     *
     * @param para
     * @return
     * @throws Exception
     * @author zyw
     */
    private Query generateQuery(Map<String, Object> para) {
        Query query = new Query();

        if (para == null) {
            return query;
        }
        if (para != null) {
            for (String key : para.keySet()) {
                query.addCriteria(generateCriteria(key, para.get(key)));
            }
        }
        return query;
    }

    /**
     * 生成Criteria
     *
     * @param key   字段名+$+操作符,如 name$regex, age$lte ,其中默认为is,如 id$is id 等效
     * @param value
     * @return
     * @throws Exception
     * @author zyw
     */
    private Criteria generateCriteria(String key, Object value) {
        String seperator = "$";
        String methodName = "is";

        if (key.indexOf(seperator) > -1) {
            String[] arr = key.split(":");
            methodName = arr[1];
            key = arr[0];
        }
        Criteria criteria = Criteria.where(key);


        if (methodName.equals("regex")) {
            criteria.regex(String.valueOf(value));
        } else {

            try {
                Method method = Criteria.class.getDeclaredMethod(methodName, Object.class);
                method.invoke(criteria, value);
            } catch (Exception e) {
                throw new RuntimeException(String.format("generate criteria:where %s %s failed !", key, value.toString()));
            }
        }

        return criteria;
    }

    private Sort generateSort(String order) {
        order = order.trim();
        String[] array = order.split(",");

        List<Sort.Order> orderList = new ArrayList<>(array.length);

        for (String str : array) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                String property = matcher.group(0);
                Sort.Direction direction = str.indexOf("desc") > -1 ? Sort.Direction.DESC : Sort.Direction.ASC;
                Sort.Order so = new Sort.Order(direction, property);
                str = str.toLowerCase();
                if (str.indexOf("nulls_first") > -1) {
                    so = so.nullsFirst();
                } else if (str.indexOf("nulls_last") > -1) {
                    so = so.nullsLast();
                }

                orderList.add(so);
            }

        }
        return new Sort(orderList);
    }


    private Map<String, Object> parse(T entity) {

        Class clazz = entity.getClass();
        Set<Field> fieldSet = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        fieldSet.addAll(Arrays.asList(fields));
        Map<String, Object> ret = new HashMap(fieldSet.size());
        while (!clazz.getSuperclass().getSimpleName().toLowerCase().equals("object")) {
            clazz = clazz.getSuperclass();
            fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        for (Field field : fieldSet) {
            field.setAccessible(true);
            try {
                ret.put(field.getName(), field.get(entity));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(String.format("entity field:%s reflect failed !", field.getName()));
            }
        }
        return ret;
    }

    private Class getGenericClass() {
        Type type = this.getClass().getGenericSuperclass();
        type = ((ParameterizedType) type).getActualTypeArguments()[0];
        return (Class<T>) type;
    }

}
