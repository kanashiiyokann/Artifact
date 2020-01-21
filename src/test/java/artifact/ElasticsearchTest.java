package artifact;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {
@Autowired
private ElasticsearchTemplate esTemplate;

    @Test
    public void deleteByFeature() {

        DeleteQuery deleteQuery=new DeleteQuery();
        deleteQuery.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("tags","qiangxiao")));
        deleteQuery.setIndex("index_test");
        deleteQuery.setType("product");
        esTemplate.delete(deleteQuery);
        if(!esTemplate.indexExists("index_test1")){
            esTemplate.createIndex("index_test1");
        }

    }


}
