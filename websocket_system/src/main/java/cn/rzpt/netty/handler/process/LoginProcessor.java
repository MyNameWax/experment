package cn.rzpt.netty.handler.process;

import cn.rzpt.netty.ws.request.WsLoginInfoReq;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class LoginProcessor extends AbstractMessageProcessor<WsLoginInfoReq> {

    @Override
    public void process(ChannelHandlerContext ctx, WsLoginInfoReq data) {
        log.info("登录请求");
        ctx.channel().writeAndFlush("登录");
    }
}
