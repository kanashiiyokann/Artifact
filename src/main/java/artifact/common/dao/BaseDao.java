package artifact.common.dao;

import java.util.List;
import java.util.Map;

/**
 * @author DGG-S27-D-20
 * @param <T>
 */
public interface BaseDao<T> {
    /**
     * 保存实体
     * @param entity
  * @throws Exception
     */
    void save(T entity) throws Exception;

    /**
     * 更新实体
     * @param entity
     * @throws Exception
     */
    void update(T entity) throws  Exception;
    /**
     * 更新实体
     * @param query
     * @param update
     * @throws Exception
     */
    void update(Map<String, Object> query,Map<String, Object> update) throws Exception;

    /**
     * 删除实体
     * @param para
     * @throws Exception
     */
    void delete(Map<String, Object> para) throws Exception;

    /**
     * 根据id查找实体
     * @param id
     * @return
     * @throws Exception
     */
    T find(Long id) throws Exception;

    /**
     * 条件分页排序查询
     * @param para
     * @param index
     * @param size
     * @param order
     * @return
     * @throws Exception
     */
    List<T> find(Map<String, Object> para, Integer index, Integer size, String order) throws Exception;

    /**
     * 查询数量
     * @param para
     * @return
     * @throws Exception
     */
    Long count(Map<String, Object> para) throws Exception;
}
