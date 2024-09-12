package cn.rzpt.netty.ws.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WSChatGroupResponse implements Serializable {

    /**
     * 发送人ID
     */
    private Long sendId;

    /**
     * 群组ID
     */
    private Long groupId;

    /**
     * 消息内容
     */
    private String message;

}
