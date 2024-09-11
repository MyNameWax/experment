package cn.rzpt.netty.ws.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WSBaseInfo<T> {

    /**
     * 请求类型
     *
     * @see cn.rzpt.netty.enums.WSReqTypeEnum
     */
    private Integer type;

    private T data;


}
