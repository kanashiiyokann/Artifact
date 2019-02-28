package artifact.common.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    void save(T entity);

    void update(T entity);

    void delete(Long ids);

    T find(Long id);

    List<T> list();

    List<T> section(Map<String, Object> para);

    int count(Map<String, Object> para);
}
