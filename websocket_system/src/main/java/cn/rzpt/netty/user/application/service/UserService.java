package cn.rzpt.netty.user.application.service;

import cn.rzpt.netty.user.application.req.UserLoginReq;
import cn.rzpt.netty.user.application.resp.OnlineUserResp;

import java.util.List;

public interface UserService {

    /**
     * 用户登录
     */
    String login(UserLoginReq userLoginReq);

    /**
     * 获取所有在线用户
     */
    List<OnlineUserResp> onlineUser();

}
