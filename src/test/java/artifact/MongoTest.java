package artifact;

import artifact.modules.common.controller.BaseController;
import artifact.modules.user.dao.UserDao;
import artifact.modules.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest extends BaseController {

    @Autowired
    private UserDao userDao;

    @Test
    public void doTest() throws Exception {

        List<User> userList = new ArrayList<>(100);

        for (int i = 1; i <= 10000; i++) {
            User user = new User();
            String index = String.valueOf(i);
            user.setId(Long.valueOf(index));
            user.setName("user".concat(index));
            userList.add(user);
        }
        long start = System.currentTimeMillis();
        int count = userDao.insert(userList, "user1");
        long end = System.currentTimeMillis();
        System.out.println(String.format("%s record inserted,and took %s ms.", count, end - start));

        start = System.currentTimeMillis();
        userList.forEach(userDao::save);
        end = System.currentTimeMillis();
        System.out.println(String.format("%s record inserted,and took %s ms.", count, end - start));
    }

}
