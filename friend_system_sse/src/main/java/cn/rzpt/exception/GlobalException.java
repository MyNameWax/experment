package cn.rzpt.exception;

import cn.rzpt.model.result.DataResult;
import cn.rzpt.model.result.DataResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalException {

    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(RuntimeException.class)
    public DataResult handlerException(RuntimeException e) {
        log.error("全局异常捕获:{}", e.getMessage());
        return DataResult.fail(DataResultCodeEnum.FAIL.getCode(),e.getMessage());
    }

}
