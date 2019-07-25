package artifact.modules.fortest.impl;

import artifact.modules.fortest.TestService;
import artifact.modules.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public void test(List<Map> dataList, Function<Map, User> handler) {

        Assert.notNull(dataList, "处理数据集合不能为空！");

        dataList.forEach(map -> handler.apply(map));
    }


    @Override
    public User testHandler(Map data) {
        //
        System.out.println("test handler run！");

        return null;
    }
}
