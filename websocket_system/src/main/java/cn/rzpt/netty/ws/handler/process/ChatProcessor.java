package cn.rzpt.netty.ws.handler.process;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import cn.rzpt.netty.ws.UserChannelCtxMap;
import cn.rzpt.netty.ws.enums.WSReqTypeEnum;
import cn.rzpt.netty.ws.model.request.WSChatReq;
import cn.rzpt.netty.ws.model.response.WSBaseResponse;
import cn.rzpt.netty.ws.model.response.WSChatFriendResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class ChatProcessor extends AbstractMessageProcessor<WSChatReq> {

    @Override
    public void process(ChannelHandlerContext ctx, WSChatReq data) {
        Long userId = data.getUserId();
        Long toUserId = data.getToUserId();
        String message = data.getMessage();
        ChannelHandlerContext context = UserChannelCtxMap.getChannelCtx(toUserId);
        //TODO 先存入数据库
        if (Objects.isNull(context) || !context.channel().isActive()) {
            log.info("用户不在线");
            return;
        }
        //TODO 如果用户在线, 则发送消息
        WSChatFriendResponse wsChatFriendResponse = WSChatFriendResponse.builder()
                .userId(userId)
                .content(message)
                .build();
        WSBaseResponse baseResponse = WSBaseResponse.builder()
                .type(WSReqTypeEnum.CHAT.getType())
                .message(JSONUtil.toJsonStr(wsChatFriendResponse))
                .build();
        context.channel().writeAndFlush(baseResponse);
    }

    @Override
    public WSChatReq transForm(Object o) {
        HashMap map = (HashMap) o;
        return BeanUtil.fillBeanWithMap(map, new WSChatReq(), false);
    }
}
