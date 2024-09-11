package cn.rzpt.netty.ws.handler;

import cn.rzpt.netty.ws.UserChannelCtxMap;
import cn.rzpt.netty.ws.constant.ChannelAttrKey;
import cn.rzpt.netty.ws.enums.WSReqTypeEnum;
import cn.rzpt.netty.ws.handler.process.AbstractMessageProcessor;
import cn.rzpt.netty.ws.handler.process.ProcessorFactory;
import cn.rzpt.netty.ws.model.request.WSBaseInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<WSBaseInfo> {

    private final RedisTemplate redisTemplate;

    /**
     * 读取到消息后进行处理
     *
     * @param channelHandlerContext channel上下文
     * @param wsBaseReq             请求消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WSBaseInfo wsBaseReq) {
        log.info("收到消息:{}", wsBaseReq);
        AbstractMessageProcessor processor = ProcessorFactory.createProcessor(WSReqTypeEnum.of(wsBaseReq.getType()));
        processor.process(channelHandlerContext, processor.transForm(wsBaseReq.getData()));
    }

    /**
     * 出现异常的处理 打印错误日志
     *
     * @param ctx   channel上下文
     * @param cause 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
    }

    /**
     * 监控浏览器上线
     *
     * @param ctx channel上下文
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("{}{}", ctx.channel().id().asLongText(), "加入");
    }


    /**
     * 监控浏览器下线
     *
     * @param ctx channel上下文
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        AttributeKey<Long> userIdAttr = AttributeKey.valueOf(ChannelAttrKey.USER_ID);
        Long userId = ctx.channel().attr(userIdAttr).get();
        ChannelHandlerContext context = UserChannelCtxMap.getChannelCtx(userId);
        // 判断一下 避免异地登录导致的误删
        if (Objects.nonNull(context) && ctx.channel().id().equals(context.channel().id())) {
            // 移除channel
            UserChannelCtxMap.removeChannelCtx(userId);
            // TODO 用户下线（就是把当前在线用户下线（踢下去的是系统用户,而不是ws连接用户））
            // TODO 这里通过Redis, 把当前用户数据删除下线。在用户连接时,重新添加数据
            // TODO 这里是Login处理器的登录数据,并不是系统用户
            String key = String.format("im:%s", userId);
            redisTemplate.delete(key);
            log.info("用户下线,userId:{}", userId);

        }
    }

    /**
     * 心跳检测
     *
     * @param ctx channel上下文
     * @param evt 事件
     */
    @Override
    @SneakyThrows
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                // 在规定时间内没有收到客户端的上行数据,主动断开连接
                AttributeKey<Long> attr = AttributeKey.valueOf("USER_ID");
                Long userId = ctx.channel().attr(attr).get();
                log.info("心跳超时,即将断开连接,用户id:{}", userId);
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
