package cn.rzpt.netty.ws.handler.process;

import cn.hutool.extra.spring.SpringUtil;
import cn.rzpt.netty.ws.enums.WSReqTypeEnum;

public class ProcessorFactory {

    public static AbstractMessageProcessor createProcessor(WSReqTypeEnum wsReqTypeEnum) {
        AbstractMessageProcessor processor = null;
        switch (wsReqTypeEnum) {
            case LOGIN:
                processor = SpringUtil.getBean(LoginProcessor.class);
                break;
            case CHAT:
                processor = SpringUtil.getBean(ChatProcessor.class);
                break;
            case HEARTBEAT:
                processor = SpringUtil.getBean(HeartBeatProcessor.class);
                break;
            default:
                break;
        }
        return processor;
    }

}
