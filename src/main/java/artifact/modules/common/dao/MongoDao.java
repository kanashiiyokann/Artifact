package artifact.modules.common.dao;

import java.util.List;
import java.util.Map;

/**
 * @param <T>
 * @author DGG-S27-D-20
 */
public interface MongoDao<T> {


    /**
     * 保存实体<br>
     * 备注：执行完成本方法后，所引用实体的主键id会自动赋上值
     *
     * @param entity
     */
    void save(T entity);

    /**
     * 根据ID修改实体
     *
     * @param entity
     */
    Long update(T entity);

    /**
     * 删除实体[数组]
     *
     * @param ids 实体ID或数组
     */
    Long delete(Long... ids);

    /**
     * 删除满足条件的实体
     *
     * @param para
     * @throws Exception
     */
    Long delete(Map<String, Object> para);

    /**
     * 对满足query查询的进行update的更新
     *
     * @param query
     * @param update
     */
    Long update(Map<String, Object> query, Map<String, Object> update);

    /**
     * 根据ID查询
     *
     * @param id 实体的主键ID
     */
    T find(Long id);

    /**
     * 根据id查找
     *
     * @param ids
     * @return
     */
    List<T> find(Long... ids) throws Exception;


    /**
     * 条件排序查询(无分页)
     *
     * @param para
     * @param sort
     * @return
     * @throws Exception
     */
    List<T> list(Map<String, Object> para, String sort);

    /**
     * 查询符合条件的数据数目
     *
     * @param para
     * @return
     * @throws Exception
     */
    Long count(Map<String, Object> para);

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
    List<T> list(Map<String, Object> para, Integer index, Integer size, String order);


    /**
     * 执行原生查询
     *
     * @param queryTemplate
     * @param args
     * @return
     * @throws Exception
     */
    List<Map> excuteQuery(String queryTemplate, Object... args);


    List<Map> aggregate(Map match, Map group, Map aggregate);

}
