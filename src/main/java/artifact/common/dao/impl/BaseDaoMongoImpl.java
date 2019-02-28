package artifact.common.dao.impl;

import artifact.common.dao.BaseDao;

import java.util.List;
import java.util.Map;

public class BaseDaoMongoImpl<T> implements BaseDao<T> {
    public void save(T entity) {

    }

    public void update(T entity) {

    }

    public void delete(Long ids) {

    }

    public T find(Long id) {
        return null;
    }

    public List<T> list() {
        return null;
    }

    public List<T> section(Map<String, Object> para) {
        return null;
    }

    public int count(Map<String, Object> para) {
        return 0;
    }
}
