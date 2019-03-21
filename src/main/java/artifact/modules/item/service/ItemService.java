package artifact.modules.item.service;

import artifact.modules.item.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ItemService extends ElasticsearchRepository<Item,Long> {
}
