package cn.rzpt.netty.user.application.service;

import cn.rzpt.netty.user.application.req.UserLoginReq;

public interface UserService {

    /**
     * 用户登录
     */
    String login(UserLoginReq userLoginReq);

}
