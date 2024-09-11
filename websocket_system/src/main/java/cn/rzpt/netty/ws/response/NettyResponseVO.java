package cn.rzpt.netty.ws.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NettyResponseVO<T> implements Serializable {
    /**
     * 请求类型
     *
     * @see cn.rzpt.netty.enums.WSReqTypeEnum
     */
    private Integer type;

    /**
     * 响应数据
     */
    private T data;
}
