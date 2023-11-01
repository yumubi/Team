package io.goji.team.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * api的json解析异常,通常为com.fasterxml.jackson.core.JsonProcessingException捕获后wrapper后抛出
 */
@Data
public class ApiJsonProcessingException extends ApiException{
    public ApiJsonProcessingException(String msg) {
        super(msg);
    }

    public ApiJsonProcessingException(Throwable cause) {
        super(cause);
    }
}
