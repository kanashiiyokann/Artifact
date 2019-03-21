package artifact.modules.user.service;

import artifact.modules.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserService extends MongoRepository<User,Long> {
}
