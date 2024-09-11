package cn.rzpt.netty.ws.model.request;

import cn.rzpt.netty.ws.enums.WSReqTypeEnum;
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
     * @see WSReqTypeEnum
     */
    private Integer type;

    private T data;


}
