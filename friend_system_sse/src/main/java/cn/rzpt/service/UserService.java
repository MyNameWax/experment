package cn.rzpt.service;

import cn.rzpt.mapper.UserMapper;
import cn.rzpt.model.po.FriendSseUser;
import cn.rzpt.model.req.LoginUserReq;
import cn.rzpt.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final StringRedisTemplate redisTemplate;


    public String login(LoginUserReq loginUserReq) {
        FriendSseUser friendSseUser = userMapper.login(loginUserReq);
        if (Objects.isNull(friendSseUser)) {
            throw new RuntimeException("用户名或密码错误");
        }
        Map<String, Long> claims = new HashMap<>();
        claims.put("id", friendSseUser.getId());
        String token = JwtUtil.createJWT("cereshuzhitingnizhenbangcereshuzhitingnizhenbang", 1000 * 60 * 60 * 24, claims);
        redisTemplate.opsForValue().set(token, friendSseUser.getId().toString());
        return token;
    }

    public Boolean isLogin(String token) {
        return JwtUtil.parseJWT("cereshuzhitingnizhenbangcereshuzhitingnizhenbang", token) != null;
    }

    public FriendSseUser getLoginUser(String token) {
        if (!isLogin(token)) {
            throw new RuntimeException("用户未登录");
        }
        String userId = redisTemplate.opsForValue().get(token);
        return userMapper.queryUserInfo(Long.parseLong(userId));
    }

    public Integer queryAuditFriend(String token) {
        String userId = redisTemplate.opsForValue().get(token);
        return userMapper.queryAuditFriend(Long.parseLong(userId));
    }
}
