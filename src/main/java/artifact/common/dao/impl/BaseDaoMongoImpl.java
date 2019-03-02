package artifact.common.dao.impl;

import artifact.common.dao.BaseDao;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
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
}
