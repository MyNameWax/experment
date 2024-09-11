package cn.rzpt.controller;

import cn.rzpt.model.req.LoginUserReq;
import cn.rzpt.model.result.DataResult;
import cn.rzpt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    public DataResult<String> login(@RequestBody LoginUserReq loginUserReq) {
        return DataResult.success(userService.login(loginUserReq));
    }

}
