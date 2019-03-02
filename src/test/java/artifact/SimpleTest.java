package artifact;

import artifact.modules.user.entity.User;
import artifact.modules.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
                Aggregation.match(Criteria.where("companyId").is(companyId)),
                Aggregation.group("companyId")
                        .sum(ConditionalOperators.when(ArrayOperators.In.arrayOf("state").containsValue(new int[]{2, 3, 4})).then(1).otherwise(0)).as("backed")
                        .count().as("total")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "bill", Map.class);
        List<Map> data=result.getMappedResults();

    }

}
