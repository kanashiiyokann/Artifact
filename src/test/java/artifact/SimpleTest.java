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
    public void mongoQueryTest() throws Exception {

        User user = userService.find(1L);


    }


    @Test
    public void mongoUpdateTest() throws Exception {

        User user = new User();
        user.setId(2L);
        user.setAge(29);
        user.setPwd("mg317412");

        userService.update(user, new Object[]{null});


    }


}
