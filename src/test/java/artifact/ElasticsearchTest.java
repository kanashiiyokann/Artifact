package artifact;

import artifact.modules.item.entity.Item;
import artifact.modules.item.service.impl.ItemServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Resource
    private ItemServiceImpl itemService;

    @Test
    public void saveTest() {

        Item item = new Item();
        item.setId(5L);
        item.setName("head-cha-la");
        item.setNote("this is a lyric");
        item.setCreateTime(new Date());

        itemService.save(item);

    }

    @Test
    public void deleteTest() {

        Item item = new Item();
        item.setId(4L);
        itemService.delete(item);

    }

    @Test
    public void searchTest() {

        Map features = new HashMap<String, Object>(1) {{
            put("id$gte", 2L);
        }};

        List<Item> retList = itemService.search(features, "index_item");
        System.out.println(retList);

    }
}
