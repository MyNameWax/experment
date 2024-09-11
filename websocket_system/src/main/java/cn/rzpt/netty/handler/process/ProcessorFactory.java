package cn.rzpt.netty.handler.process;

import cn.hutool.extra.spring.SpringUtil;
import cn.rzpt.netty.enums.WSReqTypeEnum;

public class ProcessorFactory {

    public static AbstractMessageProcessor createProcessor(WSReqTypeEnum wsReqTypeEnum) {
        AbstractMessageProcessor processor = null;
        switch (wsReqTypeEnum) {
            case LOGIN:
                processor = SpringUtil.getBean(LoginProcessor.class);
                break;
            case CHAT:
            case AUTHORIZE:
            case HEARTBEAT:
            default:
                break;
        }
        return processor;
    }

}
