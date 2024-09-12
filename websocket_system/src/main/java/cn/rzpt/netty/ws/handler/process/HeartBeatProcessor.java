package cn.rzpt.netty.ws.handler.process;

import cn.hutool.core.bean.BeanUtil;
import cn.rzpt.netty.ws.enums.WSReqTypeEnum;
import cn.rzpt.netty.ws.model.request.WsHeartBeatReq;
import cn.rzpt.netty.ws.model.request.WsLoginInfoReq;
import cn.rzpt.netty.ws.model.response.WSBaseResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class HeartBeatProcessor extends AbstractMessageProcessor<WsHeartBeatReq> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void process(ChannelHandlerContext ctx, WsHeartBeatReq data) {
        log.info("心跳检测:{}", "正常接受到心跳包");
        String key = "im:" + data.getUserId();
        redisTemplate.opsForValue().set(key, String.valueOf(0), 15, TimeUnit.SECONDS);
        WSBaseResponse baseResponse = WSBaseResponse.builder()
                .type(WSReqTypeEnum.HEARTBEAT.getType())
                .message("心跳检测成功")
                .build();
        ctx.channel().writeAndFlush(baseResponse).addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                log.error("写入数据失败", future.cause());
            }
        });
    }

    @Override
    public WsHeartBeatReq transForm(Object o) {
        HashMap map = (HashMap) o;
        return BeanUtil.fillBeanWithMap(map, new WsHeartBeatReq(), false);
    }
}
