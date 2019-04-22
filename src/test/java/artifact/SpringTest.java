package artifact;

import artifact.modules.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {

    @Resource
    private UserService userService;

    @Test
    public void mongoTest() {
        userService.test();

    }


    @Test
    public void doTest() {

//        Item item = new Item();
//        item.setName("打印中间件");
//        item.setNote("测试数据");
//        item.setCreateTime(new Date());
//
//        itemService.save(item);

//        User user = new User();
//        user.setState(User.STATE_NORMAL);
//        user.setName("admin");
//        user.setPwd("admin");
//        user.setAge(18);
//        user.setId(5L);
//        user.setCreateTime(new Date());
//
//        userService.save(user);
        Query query;
        Criteria criteria;
        query = new Query();


        query = new Query();
        criteria = Criteria.where("C").is("value").andOperator(new Criteria().orOperator(Criteria.where("A").is("value"), Criteria.where("B").is("value")));
        query.addCriteria(criteria);
        System.out.println(query.toString());

        query = new Query();
        query.addCriteria(Criteria.where("C").is("value"));
        criteria = new Criteria().orOperator(Criteria.where("A").is("value"), Criteria.where("B").is("value"));
        query.addCriteria(criteria);

        System.out.println(query.toString());

        //     mongoTemplate.aggregate(Aggregation.match(),User.class);


    }


}
