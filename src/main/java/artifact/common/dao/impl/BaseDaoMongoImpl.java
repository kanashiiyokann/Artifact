package artifact.common.dao.impl;

import artifact.common.dao.BaseDao;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        mongoTemplate.save(entity, getGenericClass().getSimpleName());
    }

    @Override
    public void update(T entity) {

    }

    @Override
    public void delete(Long ids) {

    }

    @Override
    public T find(Long id) {
        return null;
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
