package cn.rzpt.netty.user.infrastructure.repository;

import cn.rzpt.netty.user.application.req.UserLoginReq;
import cn.rzpt.netty.user.domain.repository.IUserRepository;
import cn.rzpt.netty.user.infrastructure.dao.entity.User;
import cn.rzpt.netty.user.infrastructure.dao.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserRepository implements IUserRepository {

    private final UserMapper userMapper;

    @Override
    public User login(UserLoginReq userLoginReq) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername, userLoginReq.getUsername())
                .eq(User::getPassword, userLoginReq.getPassword());
        return userMapper.selectOne(queryWrapper);
    }
}
