package artifact;

import artifact.modules.common.controller.BaseController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest extends BaseController {

    @Test
    public void doTest() throws Exception {
        String str = "2019/3/28";
        Date date = getDate(str, "yyyy/MM/dd");
        date = getDate(null, "yyyy/MM/dd");
    }

}
