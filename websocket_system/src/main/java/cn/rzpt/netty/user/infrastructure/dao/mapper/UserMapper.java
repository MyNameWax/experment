package cn.rzpt.netty.user.infrastructure.dao.mapper;

import cn.rzpt.netty.user.infrastructure.dao.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
