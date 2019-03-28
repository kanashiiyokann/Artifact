package artifact;

import artifact.modules.common.annotation.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class NotNullTest {


    @Test
    public void doTest() {
        printSomething(null);

    }


    public void printSomething(@NotNull String str) {

        System.out.println(str);
    }
}
