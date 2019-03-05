package artifact;

import artifact.modules.user.entity.User;
import artifact.modules.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
    public void mongoQueryTest() throws Exception{

        Map para=new HashMap(1){{
            put("age",18);
            put("state",2);
        }};

        Query query=new Query();
        Criteria criteria=new Criteria();
        criteria.and("age").is(18).and("state").is(2);
        query.addCriteria(criteria);
        query.limit(10);
     List<User> users=   mongoTemplate.find(query,User.class);



    }

    @Test
    public void updateTest() throws Exception {

        User user = userService.find(1L);
        user.setState(User.STATE_FORBIDDEN);
        userService.update(user);

    }

}
