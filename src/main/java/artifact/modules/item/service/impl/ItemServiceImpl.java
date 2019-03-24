package artifact.modules.item.service.impl;

import artifact.modules.common.dao.impl.ElasticSearchDaoImpl;
import artifact.modules.item.entity.Item;
import org.springframework.stereotype.Service;

/**
 *
 * @author DGG-S27-D-20
 */
@Service
public class ItemServiceImpl extends ElasticSearchDaoImpl<Item> {

}
