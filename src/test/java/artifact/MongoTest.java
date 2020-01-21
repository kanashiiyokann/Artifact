package artifact;

import artifact.modules.user.entity.Log;
import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {

    @Autowired
    private MongoTemplate template;
    @Autowired
    private MongoClient client;

    @Test
    public void sessionTest() throws Exception {
        ClientSessionOptions sessionOptions= ClientSessionOptions.builder().causallyConsistent(true).build();
        ClientSession session=client.startSession(sessionOptions);
        template.withSession(()->session).execute(options->{
            Query query=new Query(Criteria.where("name").is("tome"));
            options.remove(query,"user");
            Log log=new Log();
            log.setNote("删除名字为tome的用户");
            options.insert(log,"log");
            return options;
        });

        session.close();
    }

}
