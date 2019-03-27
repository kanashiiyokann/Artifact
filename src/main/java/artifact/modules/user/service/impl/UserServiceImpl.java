package artifact.modules.user.service.impl;

import artifact.modules.common.dao.MongoRepository;
import artifact.modules.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl extends MongoRepository<User> {


    public UserServiceImpl() {
        this.strategy = Strategy.IGNORE_NULL_AND_EMPTY;
    }

    public void test(String name, String pwd, Integer age) {

        System.out.println(name);
        System.out.println(pwd);
        System.out.println(age);
    }
}
