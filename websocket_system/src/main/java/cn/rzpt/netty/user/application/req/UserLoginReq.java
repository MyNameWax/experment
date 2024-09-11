package cn.rzpt.netty.user.application.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginReq {

    private String username;

    private String password;

}
