package cn.rzpt.netty.user.domain.repository;

import cn.rzpt.netty.user.application.req.UserLoginReq;
import cn.rzpt.netty.user.application.resp.OnlineUserResp;
import cn.rzpt.netty.user.infrastructure.dao.entity.User;

import java.util.List;

public interface IUserRepository {

    /**
     * 用户登录
     */
    User login(UserLoginReq userLoginReq);

    /**
     * 获取所有在线用户
     */
    List<OnlineUserResp> onlineUser();

}
