package cn.rzpt.netty.user.infrastructure.repository;

import cn.rzpt.netty.user.application.req.UserLoginReq;
import cn.rzpt.netty.user.application.resp.OnlineUserResp;
import cn.rzpt.netty.user.domain.repository.IUserRepository;
import cn.rzpt.netty.user.infrastructure.dao.entity.User;
import cn.rzpt.netty.user.infrastructure.dao.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserRepository implements IUserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final RedisTemplate<String, String> redisTemplate;
    private final UserMapper userMapper;

    @Override
    public User login(UserLoginReq userLoginReq) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername, userLoginReq.getUsername())
                .eq(User::getPassword, userLoginReq.getPassword());
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<OnlineUserResp> onlineUser() {
        String key = "im:*";
        Set<String> keys = redisTemplate.keys(key);
        if (CollectionUtils.isEmpty(keys) && keys.isEmpty()) {
            return List.of();
        }
        Set<String> realKeys = keys.stream().map(item -> item.replace("im:", "")).collect(Collectors.toSet());
        List<User> users = userMapper.selectList(Wrappers.lambdaQuery(User.class).in(User::getId, realKeys));
        return users.stream().map(item -> OnlineUserResp.builder().id(item.getId()).username(item.getUsername()).build()).collect(Collectors.toList());
    }
}
