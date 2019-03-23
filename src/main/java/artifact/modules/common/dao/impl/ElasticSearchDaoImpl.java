package artifact.modules.common.dao.impl;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
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
    private String index;
    @Resource
    protected Client client;

    public boolean save(T entity) {
        Map map = parse(entity);

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        IndexRequestBuilder indexRequest = client.prepareIndex(getIndex(), getType(), String.valueOf(entity.getId())).setSource(map);
        bulkRequest.add(indexRequest);
        BulkResponse responses = bulkRequest.execute().actionGet();

        return !responses.hasFailures();
    }

    protected String getIndex() {
        if (index == null || index.length() == 0) {
            index = "default";
        }
        return index;
    }

    protected String getType() {
        return getGenericClass().getSimpleName();
    }
    protected String getIdentityKey(){

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
