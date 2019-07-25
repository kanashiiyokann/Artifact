package artifact;

import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void mongoSelfDefineQuery() {

        Query query = new Query();
        query.addCriteria(Criteria.where("$where").is("this.limit > this.age"));

        List<Map> dataList = mongoTemplate.find(query, Map.class, "sample");
        System.out.println(dataList);

        query = new Query();
        query.addCriteria(new Criteria() {
            @Override
            public Document getCriteriaObject() {
                return new Document() {{
                    put("$where", "this.limit > this.age");
                }};
            }
        });

        dataList = mongoTemplate.find(query, Map.class, "sample");
        System.out.println(dataList);


    }
}


