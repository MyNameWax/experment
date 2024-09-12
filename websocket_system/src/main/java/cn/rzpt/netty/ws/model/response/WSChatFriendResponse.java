package cn.rzpt.netty.ws.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 好友消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WSChatFriendResponse implements Serializable {

    /**
     * 发消息的人
     */
    private Long userId;


    /**
     * 消息内容
     */
    private String content;

}
