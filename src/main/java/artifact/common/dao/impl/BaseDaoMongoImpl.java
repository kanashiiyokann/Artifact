package artifact.common.dao.impl;

import artifact.common.dao.BaseDao;
import com.mongodb.BasicDBObject;
import org.bson.Document;
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
     * 反射获取实现子类的实际泛型T的class
     *
     * @return
     */
    private Class<T> getGenericClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        update(entity, "id", null);
    }

    private void update(T entity, String primaryKey, Object[] ignores) throws Exception {
        Map<String, Object> map = parse(entity);
        Object primaryValue = map.get(primaryKey);
        map.remove(primaryKey);
        Update update = new Update();

        List ignoreList = Arrays.asList(ignores);
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (!ignoreList.contains(value)) {
                update.set(key, value);
            }
        }
        mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where(primaryKey).is(primaryValue)), update, getGenericClass());
    }


    @Override
    public void delete(Long ids) {

    }

    @Override
    public T find(Long id) throws Exception {

        List<T> retList = mongoTemplate.find(new Query().addCriteria(Criteria.where("_id").is(id)), getGenericClass());
        // mongoTemplate.findById()
        if (retList == null || retList.size() != 1) {
            throw new Exception("no unque record found!");
        }
        return retList.get(0);
    }

    @Override
    public List<T> list(Map<String, Object> para) throws Exception {
        Query query = new Query();
        Criteria criteria = null;
        for (String key : para.keySet()) {
            criteria = generateFilter(key, para.get(key), criteria);
        }
        query.addCriteria(criteria);
        return mongoTemplate.find(query, getGenericClass());
    }


    @Override
    public int count(Map<String, Object> para) {
        return 0;
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
            fieldSet.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        }
        for (Field field : fieldSet) {
            ret.put(field.getName(), field.get(entity));
        }
        return ret;
    }

    private Criteria generateFilter(String keyStr, Object value, Criteria criteria) throws Exception {
        String seperator = ":";
        String methodName = null, key = null;

        if (keyStr.indexOf(seperator) > -1) {
            String[] arr = keyStr.split(":");
            methodName = arr[0];
            key = arr[1];
        } else {
            key = keyStr;
        }
        Map<String, String> nickNames = new HashMap(2) {
            {
                put("lk", "regex");
                put(null, "is");
            }
        };

        //别名
        if (nickNames.containsKey(methodName)) {
            methodName = nickNames.get(methodName);
        }
        if (criteria == null) {
            criteria = Criteria.where(key);
        } else {
            criteria.and(key);
        }
        if (methodName.equals("regex")) {
            criteria.regex(value.toString());
        } else {
            Method method = Criteria.class.getDeclaredMethod(methodName, Object.class);
            method.invoke(criteria, value);
        }

        return criteria;
    }


    private void separateMap(Map<String, Object> map, List<String> keys, List<Object> values) {
        if (keys == null) {
            keys = new ArrayList<>(map.size());
        }
        if (values == null) {
            values = new ArrayList<>(map.size());
        }

        for (String key : map.keySet()) {
            keys.add(key);
            values.add(map.get(key));

        }

    }
}
