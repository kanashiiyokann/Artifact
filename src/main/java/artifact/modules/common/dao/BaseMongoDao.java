package artifact.modules.common.dao;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseMongoDao<T> {

    protected int strategy = Strategy.NO_IGNORE;
    private Pattern pattern = Pattern.compile("\\S+");
    @Autowired
    protected MongoTemplate mongoTemplate;


    /**
     * 保存多个实体
     *
     * @param entities
     */
    public void save(T... entities) {
        for (T entity : entities) {
            mongoTemplate.save(entity);
        }
    }

    /**
     * 更新多个实体
     *
     * @param entities
     */
    public void update(T... entities) {
        for (T entity : entities) {
            mongoTemplate.save(entity);
        }
    }

    /**
     * 对满足query查询的进行update的更新
     *
     * @param query
     * @param update
     * @author zyw
     */
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
     * 查询满足条件数据
     * zyw
     *
     * @return
     */
    public List<T> list(Map<String, Object> para, String sort) {

        Query query = generateQuery(para);

        if (sort != null && sort.trim().length() != 0) {
            query.with(generateSort(sort));
        }
        return mongoTemplate.find(query, getGenericClass());
    }

    /**
     * 查询符合条件的数量
     * zyw
     *
     * @param para
     * @return
     */
    public Long count(Map<String, Object> para) {
        Query query = generateQuery(para);

        return mongoTemplate.count(query, getGenericClass());
    }


    /**
     * 执行原生查询
     *
     * @param queryTemplate
     * @param args
     * @return
     * @throws Exception
     */
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
     * 删除实体[数组]
     *
     * @param ids 实体ID或数组
     */
    public Long delete(Long... ids) {
        Map para = new HashMap(1);
        para.put("id$in", ids);

        return this.delete(para);
    }

    /**
     * 删除满足条件的实体
     *
     * @param para
     * @throws Exception
     */
    public Long delete(Map<String, Object> para) {
        Query query = generateQuery(para);

        DeleteResult result = mongoTemplate.remove(query, getGenericClass());
        return result.getDeletedCount();
    }


    /**
     * 根据ID查询
     *
     * @param id 实体的主键ID
     */
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
    public List<T> find(Long... ids) {
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
                Object val = para.get(key);
                //检查策略
                if (val == null && strategy % 10 == 1) {
                    continue;
                } else if (String.valueOf(val).trim().length() == 1 && strategy > 9) {
                    continue;
                }
                query.addCriteria(generateCriteria(key, val));
            }
        }
        return query;
    }


    public Criteria generateCriteria(Map para) {
        Criteria ret = new Criteria();

        if (para != null && para.size() > 0) {

            List<Filter> filterList = new ArrayList<>();
            para.forEach((k, v) -> filterList.add(new Filter(k.toString(), v)));
            filterList.sort(Comparator.comparing(Filter::getKey));
            String prekey = null;
            for (Filter filter : filterList) {
                String key = filter.getKey();
                if (!key.equals(prekey)) {
                    prekey = key;
                    ret = ret.and(key);
                }
                try {
                    filter.getMethod().invoke(ret, filter.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    private class Filter {
        private Pattern pattern = Pattern.compile("[a-zA-Z_]+");
        private String key;
        private Object value;
        private String method;
        private String seperator = "$";
        public List<Method> methods = new ArrayList<>();

        public Filter(String key, Object value) {
            String method = "is";

            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                key = matcher.group(0);
            }
            if (matcher.find()) {
                method = matcher.group(0);
            }
            this.key = key;
            this.value = value;
            this.method = method;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public Method getMethod() {

            if (method == null || method.trim().length() == 0) {
                new Exception("错误的方法名称！").printStackTrace();
            }
            Method ret = null;
            Class clazz = Criteria.class;
            try {

                if (method.equals("regex")) {
                    ret = clazz.getDeclaredMethod(method, value.getClass());
                } else {
                    ret = clazz.getDeclaredMethod(method, Object.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ret;
        }

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

    /**
     * 生产排序条件
     *
     * @param order
     * @return
     */
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

    /**
     * 获取子类实际泛型参数类型
     *
     * @return
     */
    private Class getGenericClass() {
        Type type = this.getClass().getGenericSuperclass();
        type = ((ParameterizedType) type).getActualTypeArguments()[0];
        return (Class<T>) type;
    }

    /**
     * 设置map生成query的对value的策略
     *
     * @param strategy
     */
    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    /**
     * 执行原生聚合查询语句
     *
     * @param aggregation
     * @return
     */

    public <T> List<T> excuteAggregate(Aggregation aggregation, Class<T> clazz) {

        AggregationResults<T> ret = mongoTemplate.aggregate(aggregation, getGenericClass(), clazz);
        return ret.getMappedResults();
    }


    public interface Strategy {
        int IGNORE_NULL_AND_EMPTY = 11;
        int IGNORE_NULL = 1;
        int IGNORE_EMPTY = 10;
        int NO_IGNORE = 0;


    }

}
