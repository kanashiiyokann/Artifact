package artifact.controller;

import artifact.modules.user.entity.User;
import artifact.modules.user.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserServiceImpl userService;

    @RequestMapping("/test")
    public String test() {
        return "hello spring boot!";
    }

    @RequestMapping("/save")
    public String save() {

        User user = new User();
        user.setName("admin");
        user.setPwd("fuckyou");
        user.setAge(18);
        user.setId(1L);
        user.setCreateTime(new Date());

        userService.save(user);
        return "success";
    }

}
