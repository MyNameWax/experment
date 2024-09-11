package cn.rzpt.service;

import cn.hutool.json.JSONUtil;
import cn.rzpt.mapper.MessageNotificationMapper;
import cn.rzpt.model.po.MessageNotification;
import cn.rzpt.model.req.AddFriendReq;
import cn.rzpt.model.vo.NoticeMessageVo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageNotificationService {

    private static final Logger log = LoggerFactory.getLogger(MessageNotificationService.class);
    private final StringRedisTemplate redisTemplate;
    private final MessageNotificationMapper messageNotificationMapper;

    public void addFriend(String token, AddFriendReq addFriendReq) {
        Long targetId = addFriendReq.getTargetId();
        String remark = addFriendReq.getRemark();
        String userId = redisTemplate.opsForValue().get(token);
        // 判断是否已经添加过了,防止重新添加（TODO 是否已经是好友了,防止是好友也添加 ）
        log.info("添加好友请求,userId={},targetId={}", userId, targetId);
        Long flag = messageNotificationMapper.queryAlwaysAdd(Long.parseLong(userId), targetId);
        if (flag > 0) {
            throw new RuntimeException("您已经添加过该好友了");
        }
        addFriendReq.setUserId(Long.parseLong(userId));
        // 创建通知消息对象,并设置相关信息
        MessageNotification messageNotification = MessageNotification.builder()
                .userId(addFriendReq.getUserId())
                .toUserId(targetId)
                .noticeType(3)
                .noticeContent(remark)
                .readTarget(0)
                .processResult(null)
                .build();
        messageNotificationMapper.insert(messageNotification);
        if (SseService.containUser(targetId)) {
            NoticeMessageVo.builder()
                    .noticeType(3)
                    .noticeContent(remark)
                    .userId(Long.parseLong(userId))
                    .title("添加好友")
                    .build();
            SseService.sendMessage(targetId, JSONUtil.toJsonStr(messageNotification));
        }
    }
}
