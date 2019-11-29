package artifact;

import artifact.modules.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void mongoSelfDefineQuery() {


    }

}


