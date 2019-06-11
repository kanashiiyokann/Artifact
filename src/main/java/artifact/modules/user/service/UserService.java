package artifact.modules.user.service;

import artifact.modules.common.dao.BaseMongoDao.Strategy;
import artifact.modules.user.dao.UserDao;
import artifact.modules.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void test() {
        Map para = new HashMap<>(3);
        para.put("age$lt", 30);
        para.put("age$gte", 18);
        para.put("state", 2);

        Criteria criteria = userDao.generateCriteria(para, Strategy.IGNORE_NULL_AND_EMPTY);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria)
        );
        List<User> users = userDao.aggregate(aggregation, User.class);

        System.out.println(users);

    }
}
