package cn.rzpt.netty.user.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.jwt.JWTUtil;
import cn.rzpt.netty.user.application.req.UserLoginReq;
import cn.rzpt.netty.user.application.service.UserService;
import cn.rzpt.netty.user.domain.repository.IUserRepository;
import cn.rzpt.netty.user.infrastructure.dao.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final RedisTemplate<String,String> redisTemplate;
    private final IUserRepository userRepository;

    @Override
    public String login(UserLoginReq userLoginReq) {
        checkUserLoginReqParams(userLoginReq);
        User user = userRepository.login(userLoginReq);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("userId", user.getId());
        String token = JWTUtil.createToken(map, "1234".getBytes());
        redisTemplate.opsForValue().set(user.getId().toString(), token, 60 * 60L, TimeUnit.MINUTES);
        return token;
    }

    private void checkUserLoginReqParams(UserLoginReq userLoginReq) {
        Assert.notNull(userLoginReq.getUsername(), "用户名不允许为空");
        Assert.notNull(userLoginReq.getPassword(), "密码不允许为空");
    }
}
