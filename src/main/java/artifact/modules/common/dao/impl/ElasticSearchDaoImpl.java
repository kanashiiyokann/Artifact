package artifact.modules.common.dao.impl;


import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @param <T>
 * @author DGG-S27-D-20
 */
public abstract class ElasticSearchDaoImpl<T> {
    @Resource
    protected Client client;


    public boolean save(T... entities) {

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (T entity : entities) {
            bulkRequest.add(generateSaveRequest(entity));
        }
        BulkResponse responses = bulkRequest.execute().actionGet();
        if (responses.hasFailures()) {
            throw new RuntimeException(responses.buildFailureMessage());
        }
        return true;

    }

    public boolean delete(T... entities) {

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for (T entity : entities) {
            bulkRequest.add(generateDeleteRequest(entity));
        }
        BulkResponse response = bulkRequest.get();
        if (!response.hasFailures()) {
            throw new RuntimeException(String.format("elasticsearch delete failed response :%s", response.buildFailureMessage()));
        }
        return true;
    }

    public List<T> search(Map features, String type, String... indices) {

        SearchRequestBuilder searchRequest = client.prepareSearch(type).setSearchType(type);
        QueryBuilder query = null;
        searchRequest.setQuery(generateQuery(features));


        return null;
    }

    /**
     * 获取index
     *
     * @return
     */
    protected String getIndex(T entity) {
        String name = entity.getClass().getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }

    /**
     * 获取type
     *
     * @return
     */
    protected String getType(T entity) {
        return String.format("index_%s", getIndex(entity));
    }

    /**
     * 获取主键field
     *
     * @return
     */
    protected String getIdentityKey() {
        return "id";
    }

    /**
     * 获取子类实际泛型参数
     *
     * @return
     */
    private Class getGenericClass() {
        Type type = this.getClass().getGenericSuperclass();
        type = ((ParameterizedType) type).getActualTypeArguments()[0];
        return (Class<T>) type;
    }

    private Map<String, Object> parse(T entity) {

        Class clazz = entity.getClass();
        Set<Field> fieldSet = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        fieldSet.addAll(Arrays.asList(fields));
        Map<String, Object> ret = new HashMap(fieldSet.size());
        String obj = "object";
        while (!obj.equals(clazz.getSuperclass().getSimpleName().toLowerCase())) {
            clazz = clazz.getSuperclass();
            fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        for (Field field : fieldSet) {
            field.setAccessible(true);
            try {
                ret.put(field.getName(), field.get(entity));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(String.format("entity field:%s reflect failed !", field.getName()));
            }
        }
        return ret;
    }

    private QueryBuilder generateQuery(Map para) {

        return null;
    }

    private IndexRequestBuilder generateSaveRequest(T entity) {
        Map map = parse(entity);
        Object id = map.get(getIdentityKey());
        if (id == null) {
            throw new RuntimeException("the entity should has id field without null ！");
        }
        IndexRequestBuilder ret = client.prepareIndex(getIndex(entity), getType(entity), String.valueOf(id)).setSource(map);

        return ret;
    }

    private DeleteRequestBuilder generateDeleteRequest(T entity) {

        Map map = parse(entity);
        Object id = map.get(getIdentityKey());
        if (id == null) {
            throw new RuntimeException("the entity should has id field without null ！");
        }
        DeleteRequestBuilder ret = client.prepareDelete(getIndex(entity), getType(entity), String.valueOf(id));
        return ret;
    }

}
