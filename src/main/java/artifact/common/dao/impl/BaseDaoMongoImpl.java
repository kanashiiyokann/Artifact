package artifact.common.dao.impl;

import artifact.common.dao.BaseDao;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Repository
public abstract class BaseDaoMongoImpl<T> implements BaseDao<T> {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 删除实体
     *
     * @param para
     * @throws Exception
     */
    @Override
    public void delete(Map<String, Object> para) throws Exception {

        Query query = generateQuery(para);
        mongoTemplate.remove(query, getGenericClass());

    }

    /**
     * 条件分页排序查询
     *
     * @param para
     * @param index
     * @param size
     * @param order
     * @return
     * @throws Exception
     */
    @Override
    public List<T> find(Map<String, Object> para, Integer index, Integer size, String order) throws Exception {
        //条件
        Query query = generateQuery(para);
        //排序
        if (order != null && order.trim().length() != 0) {
            query.with(generateSort(order));
        }
        //分页
        if (index != null && size != null) {
            query.skip((index - 1) * size);
            query.limit(size);
        }
        return mongoTemplate.find(query, getGenericClass());
    }

    @Override
    public void save(T entity) {
        mongoTemplate.save(entity);
    }

    /**
     * 更新实体
     *
     * @param entity
     * @throws Exception
     */
    @Override
    public void update(T entity) throws Exception {
        String primary = "id";
        Map<String, Object> map = parse(entity);
        Object obj = map.get(primary);
        if (obj == null) {
            throw new Exception("lack of value for primary key:id !");
        }
        Long id = Long.valueOf(String.valueOf(obj));
        Query query = new Query(Criteria.where(primary).is(id));
        map.remove("id");

        Update update = new Update();
        for (String key : map.keySet()) {
            update.set(key, map.get(key));
        }
        mongoTemplate.updateMulti(query, update, getGenericClass());
    }

    @Override
    public void update(Map<String, Object> query, Map<String, Object> update) throws Exception {


        Update udt = new Update();
        for (String key : update.keySet()) {
            udt.set(key, update.get(key));
        }
        Query qr = generateQuery(query);
        mongoTemplate.updateMulti(qr, udt, getGenericClass());
    }


    @Override
    public T find(Long id) throws Exception {

        List<T> retList = mongoTemplate.find(new Query(Criteria.where("id").is(id)), getGenericClass());

        if (retList == null || retList.size() != 1) {
            throw new Exception("no unique record found!");
        }
        return retList.get(0);
    }


    @Override
    public Long count(Map<String, Object> para) throws Exception {

        Query query = generateQuery(para);
        return mongoTemplate.count(query, getGenericClass());
    }

    /**
     * 执行mongodb原生语句
     *
     * @param query
     * @return
     */
    public List<Map> rawQuery(String query) {
        BasicDBObject command = new BasicDBObject();
        command.put("$eval", query);
        Document data = mongoTemplate.getDb().runCommand(command);

        List<Map> ret = new ArrayList<>();

        Object retval = data.get("retval");

        if (retval instanceof Document) {
            data = (Document) retval;
            List dataList = (ArrayList) data.get("_batch");

            for (Object obj : dataList) {
                Document doc = ((Document) obj);
                Map map = new HashMap(doc.size());
                for (String key : doc.keySet()) {
                    map.put(key, doc.get(key));
                }
                ret.add(map);
            }
        } else {
            Map map = new HashMap(1) {{
                put("retval", retval.toString());
            }};
            ret.add(map);
        }


        return ret;
    }

    private Map<String, Object> parse(T entity) throws Exception {
        Map<String, Object> ret = new HashMap();
        Class clazz = entity.getClass();
        Set<Field> fieldSet = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        fieldSet.addAll(Arrays.asList(fields));
        while (!clazz.getSuperclass().getSimpleName().toLowerCase().equals("object")) {
            clazz = clazz.getSuperclass();
            fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        for (Field field : fieldSet) {
            field.setAccessible(true);
            ret.put(field.getName(), field.get(entity));
        }
        return ret;
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
    private Criteria generateCriteria(String key, Object value) throws Exception {
        String separator = "$";
        String methodName = "is";

        if (key.indexOf(separator) > -1) {
            String[] arr = key.split(":");
            methodName = arr[1];
            key = arr[0];
        }
        Criteria criteria = Criteria.where(key);
        Class parameterType = value.getClass();

        Method method = Criteria.class.getDeclaredMethod(methodName, parameterType);
        method.invoke(criteria, parameterType.cast(value));

        return criteria;
    }


    /**
     * 根据参数生成query
     *
     * @param para
     * @return
     * @throws Exception
     * @author zyw
     */
    private Query generateQuery(Map<String, Object> para) throws Exception {
        Query query = new Query();

        for (String key : para.keySet()) {
            query.addCriteria(generateCriteria(key, para.get(key)));
        }
        return query;
    }

    /**
     * 反射获取实现子类的实际泛型T的class
     *
     * @return
     */
    private Class<T> getGenericClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    private Sort generateSort(String order) {
        order = order.trim().toLowerCase();
        String[] array = order.split(",");

        List<Sort.Order> orderList = new ArrayList<>(array.length);
        for (String str : array) {
            Sort.Direction direction = str.indexOf("desc") > -1 ? Sort.Direction.DESC : Sort.Direction.ASC;
            orderList.add(new Sort.Order(direction, str.split(" ")[0]));

        }
        return Sort.by(orderList);
    }
}
