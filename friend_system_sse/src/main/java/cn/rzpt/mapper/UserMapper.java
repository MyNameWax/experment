package cn.rzpt.mapper;

import cn.rzpt.model.po.FriendSseUser;
import cn.rzpt.model.req.LoginUserReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // 登录
    FriendSseUser login(LoginUserReq loginUserReq);

    FriendSseUser queryUserInfo(Long userId);

    Integer queryAuditFriend(Long userId);
}
