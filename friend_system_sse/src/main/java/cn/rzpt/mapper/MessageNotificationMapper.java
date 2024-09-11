package cn.rzpt.mapper;

import cn.rzpt.model.po.MessageNotification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageNotificationMapper {
    void insert(MessageNotification messageNotification);

    Long queryAlwaysAdd(Long userId, Long targetId);
}
