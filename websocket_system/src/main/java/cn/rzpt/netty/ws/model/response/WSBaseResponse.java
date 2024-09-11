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
public class WSBaseResponse implements Serializable {

    private Integer type;

    private String message;

}
