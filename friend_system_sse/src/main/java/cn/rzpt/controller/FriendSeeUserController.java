package cn.rzpt.controller;

import cn.rzpt.model.po.FriendSseUser;
import cn.rzpt.model.req.AddFriendReq;
import cn.rzpt.model.result.DataResult;
import cn.rzpt.service.MessageNotificationService;
import cn.rzpt.service.SseService;
import cn.rzpt.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@AllArgsConstructor
@RestController
@RequestMapping("/friend")
public class FriendSeeUserController {

    private static final Logger log = LoggerFactory.getLogger(FriendSeeUserController.class);
    private final UserService userService;
    private final MessageNotificationService messageNotificationService;

    @GetMapping("connection")
    public SseEmitter connection(String token) {
        log.info("创建SSE连接请求,token={}", token);
        FriendSseUser user = userService.getLoginUser(token);
        return SseService.createConnection(user.getId());
    }

    @PostMapping("add/friend")
    public DataResult<Boolean> addFriend(@RequestHeader String Authorization, @RequestBody AddFriendReq addFriendReq) {
        log.info("添加好友,添加人:{},被添加人:{}", addFriendReq.getUserId(), addFriendReq.getTargetId());
        messageNotificationService.addFriend(Authorization, addFriendReq);
        return DataResult.success(true);
    }

    @GetMapping("query")
    public DataResult<Integer> queryAuditFriend(String token) {
        log.info("查询好友数量,token={}", token);
        return DataResult.success(userService.queryAuditFriend(token));
    }

}
