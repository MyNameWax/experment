package cn.rzpt.netty.ws.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WSChatReq {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 接收用户ID
     */
    private Long toUserId;

    /**
     * 消息内容
     */
    private String message;

}
