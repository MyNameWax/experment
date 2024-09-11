package cn.rzpt.netty.ws.handler.process;

import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractMessageProcessor<T> {

    public void process(ChannelHandlerContext ctx, T data) {
    }

    public void process(T data) {
    }

    public T transForm(Object o) {
        return (T) o;
    }

}
