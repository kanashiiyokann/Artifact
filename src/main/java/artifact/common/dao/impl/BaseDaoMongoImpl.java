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
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Repository
public abstract class BaseDaoMongoImpl<T> implements BaseDao<T> {

    private String primaryKey = "id";
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
        update(entity, null);
    }

    private void update(T entity, Object[] ignores) throws Exception {
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
    public List<T> list() {
        return null;
    }

    @Override
    public List<T> section(Map<String, Object> para) {
        return null;
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

        data = (Document) data.get("retval");
        List<Document> list = (List<Document>) data.get("_batch");
        List<Map> resultList = new ArrayList<>();

        for (Document doc : list) {
            Map map = new HashMap();
            for (String key : doc.keySet()) {
                map.put(key, doc.get(key));
            }
            resultList.add(map);
        }

        return resultList;
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


//      低版本使用下面方法
//    public Map rawQuery(String query){
//        CommandResult data= mongoTemplate.getDb().doEval(query);
//        Map result=new HashMap();
//        for(String key :  data.keySet()){
//            result.put(key,data.get(key));
//        }
//        return data;
//    }
}
