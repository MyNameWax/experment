package cn.rzpt.netty.ws.handler.process;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import cn.rzpt.netty.ws.UserChannelCtxMap;
import cn.rzpt.netty.ws.constant.ChannelAttrKey;
import cn.rzpt.netty.ws.enums.WSReqTypeEnum;
import cn.rzpt.netty.ws.model.request.WsLoginInfoReq;
import cn.rzpt.netty.ws.model.response.WSBaseResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class LoginProcessor extends AbstractMessageProcessor<WsLoginInfoReq> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void process(ChannelHandlerContext ctx, WsLoginInfoReq wsLoginInfoReq) {
        log.info("用户登录,userId={}", wsLoginInfoReq);
        ChannelHandlerContext context = UserChannelCtxMap.getChannelCtx(wsLoginInfoReq.getUserId());
        if (Objects.nonNull(context)) {
            // 说明这个用户已经存在了,不允许再次登录,强制下线
            WSBaseResponse baseResponse = WSBaseResponse.builder()
                    .type(1)
                    .message("账号已在其他地方登录")
                    .build();
            ctx.channel().writeAndFlush(baseResponse);
            return;
        }
        String token = redisTemplate.opsForValue().get(wsLoginInfoReq.getUserId().toString());
        if (StrUtil.isEmpty(token) || !JWTUtil.verify(token, "1234".getBytes())) {
            log.info("用户登录失败,token={}", token);
            WSBaseResponse baseResponse = WSBaseResponse.builder()
                    .type(1)
                    .message("连接失败,请重新登陆")
                    .build();
            ctx.channel().writeAndFlush(baseResponse);
            return;
        }
        // 绑定用户和Channel
        UserChannelCtxMap.addChannelCtx(wsLoginInfoReq.getUserId(), ctx);
        // 设置用户ID属性
        AttributeKey<Long> attr = AttributeKey.valueOf(ChannelAttrKey.USER_ID);
        ctx.channel().attr(attr).set(wsLoginInfoReq.getUserId());
        // 心跳次数
        attr = AttributeKey.valueOf(ChannelAttrKey.HEARTBEAT_TIMES);
        ctx.channel().attr(attr).set(0L);
        // 在redis上记录每个user的channelId，15秒没有心跳，则自动过期
        String key = "im:" + wsLoginInfoReq.getUserId();
        redisTemplate.opsForValue().set(key, String.valueOf(0), 15, TimeUnit.SECONDS);
        // 响应ws
        WSBaseResponse baseResponse = WSBaseResponse.builder()
                .type(WSReqTypeEnum.LOGIN.getType())
                .message("成功与通讯服务建立连接")
                .build();
        ctx.channel().writeAndFlush(baseResponse).addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                log.error("写入数据失败", future.cause());
            }
        });
    }

    @Override
    public WsLoginInfoReq transForm(Object o) {
        HashMap map = (HashMap) o;
        return BeanUtil.fillBeanWithMap(map, new WsLoginInfoReq(), false);
    }
}
