package artifact.modules.fortest;

import artifact.modules.user.entity.User;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface TestService {

    void test(List<Map> dataList, Function<Map, User> handler);

    User testHandler(Map data);
}
