package artifact;

import artifact.modules.user.entity.User;
import artifact.modules.user.service.impl.UserServiceImpl;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    public void doTest() {

        User user = new User();
        user.setName("admin");
        user.setPwd("fuckyou");
        user.setAge(18);
        user.setId(1L);
        user.setCreateTime(new Date());

        userService.save(user);
    }

    @Test
    public void mongoAggregateTest() {
        Long companyId = 123L;
        List<Integer> list = new ArrayList(3) {
            {
                add(2);
                add(3);
                add(4);
            }
        };
        Aggregation aggregation = Aggregation.newAggregation(
               // Aggregation.match(Criteria.where("companyId").is(companyId)),
                Aggregation.group()
                        .sum(ConditionalOperators.when(ArrayOperators.In.arrayOf("state").containsValue(new int[]{2,3})).then(1).otherwise(0)).as("abnormal")
                        .count().as("total")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "User", Map.class);
        List<Map> data=result.getMappedResults();

    }

    @Test
    public void mongoRawQueryTest(){
        String query="db.getCollection(\"User\").aggregate([ { \"$group\" : { \"_id\" :  null  , \"abnormal\" : { \"$sum\" : { \"$cond\" : { \"if\" : { \"$in\" : [ \"$state\",[ 2 , 3] ]} , \"then\" : 1 , \"else\" : 0}}} , \"total\" : { \"$sum\" : 1}}}]);";

        BasicDBObject command=new BasicDBObject();
        command.put("$eval",query);
         Document data= mongoTemplate.getDb().runCommand(command);
         Map result=new HashMap();
      for(String key :  data.keySet()){
          result.put(key,data.get(key));
      }

    }

}
