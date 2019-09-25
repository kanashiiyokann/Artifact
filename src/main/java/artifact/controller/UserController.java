package artifact.controller;

import artifact.common.RestResponse;
import artifact.modules.user.entity.User;
import artifact.modules.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findById.do", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查找用户", notes = "根据id查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, dataTypeClass = Long.class),
    })
    public RestResponse<User> findById(Long id) {

        User user = null;
        try {
            user = userService.findById(id);
            return RestResponse.success(user);
        } catch (Exception e) {
            return RestResponse.failed(e.getMessage());
        }

    }

}
