package cn.rzpt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class FriendSystemSseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendSystemSseApplication.class, args);
        log.info("基于SSE添加好友项目启动成功");
    }

}
