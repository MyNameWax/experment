package cn.rzpt.netty.user.interfaces.user;


import cn.rzpt.netty.user.application.req.UserLoginReq;
import cn.rzpt.netty.user.application.resp.OnlineUserResp;
import cn.rzpt.netty.user.application.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    public String login(@RequestBody UserLoginReq userLoginReq) {
        return userService.login(userLoginReq);
    }

    @GetMapping("online")
    public List<OnlineUserResp> onlineUser() {
        return userService.onlineUser();
    }


}
