package cn.rzpt.netty.ws.handler.encoder;

import cn.rzpt.netty.ws.model.request.WSBaseInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class MessageProtocolDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame, List<Object> list) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        WSBaseInfo sendInfo = objectMapper.readValue(textWebSocketFrame.text(), WSBaseInfo.class);
        list.add(sendInfo);
    }
}
