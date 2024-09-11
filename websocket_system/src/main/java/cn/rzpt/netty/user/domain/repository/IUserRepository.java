package cn.rzpt.netty.user.domain.repository;

import cn.rzpt.netty.user.application.req.UserLoginReq;
import cn.rzpt.netty.user.infrastructure.dao.entity.User;

public interface IUserRepository {

    /**
     * 用户登录
     */
    User login(UserLoginReq userLoginReq);

}
