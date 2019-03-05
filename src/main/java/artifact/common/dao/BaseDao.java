package artifact.common.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    void save(T entity);

    void update(T entity) throws Exception;

    void delete(Long ids);

    T find(Long id) throws Exception;

    List<T> list(Map<String, Object> para)throws Exception;


    int count(Map<String, Object> para);
}
