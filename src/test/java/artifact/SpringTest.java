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

import java.text.SimpleDateFormat;
import java.util.HashMap;
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

    @Test
    public void dateTest() throws Exception{
        Map map=new HashMap(4);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("2019-07-01",sdf.parse("2019-07-01 00:00:00"));
        map.put("2019-07-25",sdf.parse("2019-07-25 23:59:59"));
        map.put("2019-07-26",sdf.parse("2019-07-26 00:00:00"));
        map.put("2019-07-31",sdf.parse("2019-07-31 23:59:59"));

        mongoTemplate.insert(map,"sample");
    }
}


