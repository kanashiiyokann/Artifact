package artifact.modules.user.dao;

import artifact.modules.common.dao.BaseMongoDao;
import artifact.modules.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends BaseMongoDao<User> {
}
