package cn.rzpt.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public class SseService {

    // 当前连接总数
    private static final AtomicInteger CURRENT_CONNECTION_TOTAL = new AtomicInteger(0);

    // 对象映射集
    private static final Map<Long, SseEmitter> SSE_EMITTER_MAP = new ConcurrentHashMap<>();

    /**
     * 创建连接
     */
    public static SseEmitter createConnection(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        try {
            sseEmitter.send(
                    SseEmitter.event()
                            .reconnectTime(1000L)
                            .data("连接成功")
            );
        } catch (IOException e) {
            log.error("前端重连异常 ===> 用户ID={},异常信息:{}", userId, e.getMessage());
        }
        // 注册回调
        sseEmitter.onCompletion(completionCallBack(userId));
        sseEmitter.onTimeout(timeOutCallBack(userId));
        sseEmitter.onError(errorCallBack(userId));
        SSE_EMITTER_MAP.put(userId, sseEmitter);
        int count = CURRENT_CONNECTION_TOTAL.incrementAndGet();
        log.info("创建SSE连接成功=>,当前连接总数:{},用户ID={}", count, userId);
        return sseEmitter;
    }

    /**
     * 错误回调
     * 连接报错时回调触发。
     *
     * @param messageId 消息 ID
     * @return {@link Consumer}<{@link Throwable}>
     */
    private static Consumer<Throwable> errorCallBack(Long messageId) {
        return throwable -> {
            log.error("连接异常 ==> messageId={}", messageId);
            removeMessageId(messageId);
        };
    }

    /**
     * 超时回拨
     * 连接超时时回调触发
     *
     * @param messageId 消息 ID
     * @return {@link Runnable}
     */
    private static Runnable timeOutCallBack(Long messageId) {
        return () -> {
            log.info("连接超时 ==> messageId={}", messageId);
            removeMessageId(messageId);
        };
    }

    /**
     * 删除邮件 ID
     * 移除 MessageId
     *
     * @param userId 用户 ID
     */
    public static void removeMessageId(Long userId) {
        SSE_EMITTER_MAP.remove(userId);
        //数量-1
        CURRENT_CONNECTION_TOTAL.getAndDecrement();
        log.info("remove messageId={}", userId);
    }

    /**
     * 完成回拨
     * 断开SSE连接时的回调
     *
     * @param userId 消息 ID
     * @return {@link Runnable}
     */
    private static Runnable completionCallBack(Long userId) {
        return () -> {
            log.info("结束连接 ==> userId={}", userId);
            removeMessageId(userId);
        };
    }

    public static boolean containUser(Long targetId) {
        return SSE_EMITTER_MAP.containsKey(targetId);
    }

    @SneakyThrows
    public static void sendMessage(Long targetId, String jsonStr) {
        if (SSE_EMITTER_MAP.containsKey(targetId)) {
            SSE_EMITTER_MAP.get(targetId).send(jsonStr);
        }
    }
}
