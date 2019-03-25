package artifact;

import artifact.modules.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AopTest {

    @Resource
    private UserServiceImpl userserviceImpl;

    @Test
    public void test() {
        userserviceImpl.test("", "admin", 18);
    }

}
