package artifact.modules.common.dao.impl;


import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param <T>
 * @author DGG-S27-D-20
 */
public abstract class ElasticSearchDaoImpl<T> {
    private static List<String> rangeMethodList = new ArrayList<String>(4) {{
        add("lt");
        add("lte");
        add("gt");
        add("gte");
    }};
    private Pattern pattern_letter = Pattern.compile("[a-zA-Z]+");
    @Resource
    private Client client;


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

    public List<T> search(Map features, String order, String... indices) {

        SearchRequestBuilder searchRequest = client.prepareSearch(indices).setSearchType(SearchType.QUERY_THEN_FETCH);
        QueryBuilder query = generateQuery(features);
        searchRequest.setQuery(query);
        searchRequest.setFrom(0).setSize(10000);

        searchRequest.addSort(SortBuilders.fieldSort("name").order(SortOrder.DESC));

        SearchResponse response = searchRequest.execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        List<T> retList = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            retList.add(JSONObject.parseObject(hit.getSourceAsString(), getGenericClass()));
        }
        return retList;
    }


    /**
     * 获取index
     *
     * @return
     */
    protected String getIndex(T entity) {
        return String.format("index_%s", getType(entity));

    }

    /**
     * 获取type
     *
     * @return
     */
    protected String getType(T entity) {
        String name = entity.getClass().getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
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
    private Class<T> getGenericClass() {
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
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        Map<String, RangeQueryBuilder> rangeQueries = new HashMap<>(para.size());

        if (para != null && para.size() > 0) {
            Matcher matcher;
            boolean flag;
            for (Object obj : para.keySet()) {
                String key = String.valueOf(obj).trim();
                matcher = pattern_letter.matcher(key);
                flag = matcher.find();
                if (!flag) {
                    throw new RuntimeException("illegal feature name found !");
                }
                Object value = para.get(obj);
                String name = matcher.group(0), type = "term";
                if (matcher.find()) {
                    type = matcher.group(0);
                }
                if (type.equals("term")) {
                    name = value instanceof String ? name.concat(".keyword") : name;
                    query.must(QueryBuilders.termQuery(name, value));
                } else if (rangeMethodList.contains(type)) {
                    RangeQueryBuilder rangeQuery = rangeQueries.getOrDefault(name, QueryBuilders.rangeQuery(name));
                    Method method;
                    try {
                        method = RangeQueryBuilder.class.getMethod(type, Object.class);
                        method.invoke(rangeQuery, value);

                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage());
                    }
                    rangeQueries.put(name, rangeQuery);
                }
            }
        }

        for (RangeQueryBuilder rangeQuery : rangeQueries.values()) {
            query.must(rangeQuery);
        }

        return query;
    }

    private IndexRequestBuilder generateSaveRequest(T entity) {
        Map map = parse(entity);
        Object id = map.get(getIdentityKey());
        if (id == null) {
            throw new RuntimeException("the entity should has id field without null ！");
        }
        return client.prepareIndex(getIndex(entity), getType(entity), String.valueOf(id)).setSource(map);
    }

    private DeleteRequestBuilder generateDeleteRequest(T entity) {

        Map map = parse(entity);
        Object id = map.get(getIdentityKey());
        if (id == null) {
            throw new RuntimeException("the entity should has id field without null ！");
        }
        return client.prepareDelete(getIndex(entity), getType(entity), String.valueOf(id));
    }

}
