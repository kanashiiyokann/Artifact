package artifact;

import artifact.modules.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    public void mongoTest() {


        User user = mongoTemplate.findById(1L, User.class);
        System.out.println(user);
    }
}


