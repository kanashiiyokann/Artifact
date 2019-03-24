package artifact;

import artifact.modules.item.entity.Item;
import artifact.modules.item.service.impl.ItemServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Resource
    private ItemServiceImpl itemService;

    @Test
    public void saveTest() {

        Item item = new Item();
        item.setId(4L);
        item.setName("admin");
        item.setNote("es save test again");
        item.setCreateTime(new Date());

        itemService.save(item);

    }

    @Test
    public void deleteTest() {

        Item item = new Item();
        item.setId(4L);
        itemService.delete(item);

    }
}