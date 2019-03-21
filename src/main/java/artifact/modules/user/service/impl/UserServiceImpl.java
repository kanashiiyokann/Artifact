package artifact.modules.user.service.impl;

import artifact.modules.common.dao.impl.MongoDaoImpl;
import artifact.modules.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserServiceImpl extends MongoDaoImpl<User> {
    @Override
    public List<Map> aggregate(Map match, Map group, Map aggregate) {

        return null;
    }
}
