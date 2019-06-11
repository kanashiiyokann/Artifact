package artifact;

import artifact.modules.common.service.BaseService;
import artifact.modules.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {
    @Autowired
    BaseService baseService;

    @Test
    public void mongoTest() {

        UserService userService = baseService.getBean(UserService.class);
        userService.test();
    }
}


