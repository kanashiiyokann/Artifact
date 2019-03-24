package artifact.modules.common.dao.impl;


import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

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

    public boolean save(T entity) {
        Map map = parse(entity);

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        Object id = map.get(getIdentityKey());
        if (id == null) {
            throw new RuntimeException("the entity should has id field without null ！");
        }
        IndexRequestBuilder indexRequest = client.prepareIndex(getIndex(), getType(), String.valueOf(id)).setSource(map);
        bulkRequest.add(indexRequest);
        BulkResponse responses = bulkRequest.execute().actionGet();
        if (responses.hasFailures()) {
            throw new RuntimeException(responses.buildFailureMessage());
        }
        return true;
    }

    public boolean delete(T entity) {
        Map map = parse(entity);
        Object id = map.get(getIdentityKey());
        if (id == null) {
            throw new RuntimeException("the entity should has id field without null ！");
        }
        DeleteResponse response = client.prepareDelete(getIndex(), getType(), String.valueOf(id)).get();
        if (!Result.DELETED.equals(response.getResult())) {
            throw new RuntimeException(String.format("elasticsearch delete failed response :%s", response.getResult()));
        }
        return true;
    }

    /**
     * 获取index
     *
     * @return
     */
    protected String getIndex() {
        String name = getGenericClass().getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }

    /**
     * 获取type
     *
     * @return
     */
    protected String getType() {
        return "default";
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

}
