package artifact.modules.common.dao;

public interface ElasticsearchDao<T> {
    /**
     * 保存一个实体
     *
     * @param entity
     */
    void save(T entity);
}
