package cn.rzpt.netty.ws.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public enum WSReqTypeEnum {
    LOGIN(1, "请求登录"),
    CHAT(2, "发送消息"),
    AUTHORIZE(3, "登录认证"),
    HEARTBEAT(4, "心跳包");

    private final Integer type;
    private final String desc;

    private static final Map<Integer, WSReqTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(WSReqTypeEnum.values()).collect(Collectors.toMap(WSReqTypeEnum::getType, Function.identity()));
    }

    public static WSReqTypeEnum of(Integer type) {
        return CACHE.get(type);
    }
}
