package artifact;

import artifact.modules.user.entity.User;
import artifact.modules.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {

    @Resource
    private UserServiceImpl userService;

    @Test
    public void doTest() {

        List<User> users=userService.list(null,"age nulls_last");
        System.out.println(users.toString());
    }






}
