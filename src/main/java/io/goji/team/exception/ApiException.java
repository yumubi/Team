package io.goji.team.exception;


import cn.hutool.core.util.StrUtil;
import io.goji.team.common.result.ResultCode;
import java.util.HashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.text.MessageFormat;

/**
 * Api异常基类
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class ApiException extends RuntimeException {

    private ResultCode errorCode;
    private String msg;

    protected HashMap<String, Object> payload;


    public ApiException(String msg) {
        super(MessageFormat.format("traceId: {0}\n{1}", MDC.get("traceId"), msg));
    }

    public ApiException(Throwable cause) {
        super(MessageFormat.format("traceId: {0}\n{1}", MDC.get("traceId")), cause);
    }

    public ApiException(ResultCode errorCode, String msg) {

        this.errorCode = errorCode;
        this.msg = StrUtil.format(errorCode.getMsg());
    }

    public ApiException(Throwable e, ResultCode errorCode, Object... args) {
        super(e);
        this.errorCode = errorCode;
        this.msg = StrUtil.format(errorCode.getMsg(), args);
    }

}
