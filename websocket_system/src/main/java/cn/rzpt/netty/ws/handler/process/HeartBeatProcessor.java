package cn.rzpt.netty.ws.handler.process;

import cn.hutool.core.bean.BeanUtil;
import cn.rzpt.netty.ws.enums.WSReqTypeEnum;
import cn.rzpt.netty.ws.model.request.WSHeartBeatReq;
import cn.rzpt.netty.ws.model.response.WSBaseResponse;
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
public class HeartBeatProcessor extends AbstractMessageProcessor<WSHeartBeatReq> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void process(ChannelHandlerContext ctx, WSHeartBeatReq data) {
        log.info("心跳检测:{}", "正常接受到心跳包");
        String key = "im:" + data.getUserId();
        redisTemplate.opsForValue().set(key, String.valueOf(0), 15, TimeUnit.SECONDS);
        WSBaseResponse baseResponse = WSBaseResponse.builder()
                .type(WSReqTypeEnum.HEARTBEAT.getType())
                .message("心跳检测成功")
                .build();
        ctx.channel().writeAndFlush(baseResponse);
    }

    @Override
    public WSHeartBeatReq transForm(Object o) {
        HashMap map = (HashMap) o;
        return BeanUtil.fillBeanWithMap(map, new WSHeartBeatReq(), false);
    }
}
