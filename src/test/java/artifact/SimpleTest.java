package artifact;

import artifact.modules.user.entity.User;
import artifact.modules.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
        user.setState(User.STATE_NORMAL);
        user.setCreateTime(new Date());

        userService.save(user);
    }


    @Test
    public void mongoRawQueryTest(){

        String query="db.getCollection(\"User\").aggregate([ { \"$group\" : { \"_id\" :  null  , \"abnormal\" : { \"$sum\" : { \"$cond\" : { \"if\" : { \"$in\" : [ \"$state\",[ 2 , 3] ]} , \"then\" : 1 , \"else\" : 0}}} , \"total\" : { \"$sum\" : 1}}}]);";
        List<Map> result = userService.rawQuery(query);
        System.out.println(result.toString());

    }

}
