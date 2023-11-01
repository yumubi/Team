package io.goji.team.advice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.goji.team.common.result.Result;
import io.goji.team.common.result.ResultCode;
import io.goji.team.exception.ApiException;
import io.goji.team.exception.ApiJsonProcessingException;
import io.goji.team.exception.ApiParamException;
import io.goji.team.filter.TraceIdFilter;
import io.goji.team.utils.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailMessage;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 统一异常处理
 **/
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {
//    private final ApplicationEventPublisher applicationEventPublisher;
//    @Value("${spring.mail.enable:false}")
//    private boolean mailEnable;
//    private final ObjectMapper om;


    /**
     * 业务异常
     */
    @ExceptionHandler
    public Result<?> handleServiceException(ApiException e) {
        log.error(e.getMessage(), e.getPayload());
        return Result.failed(e.getErrorCode(), e.getMsg())   ;
    }




    @ExceptionHandler
    public Result<String> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(getExceptionErrorLogMessage("请求方法不正确或者没有受支持的方法"), e);
        Result<String> result = Result.failed(ResultCode.USER_ERROR, getExceptionJsonMessage());
        result.setMsg("请求方法不正确或者没有受支持的方法");
        return result;
    }




    @ExceptionHandler
    public Result<String> apiParamException(ApiParamException e) {
        log.error(getExceptionErrorLogMessage("请求参数不正确或者不支持"), e);
        return Result.failed(ResultCode.USER_ERROR, getExceptionJsonMessage());
    }

    @ExceptionHandler
    public Result<String> apiJsonProcessingException(ApiJsonProcessingException e) {
        log.error(getExceptionErrorLogMessage("json转换错误(json格式化不正确)"), e);
        return Result.failed(ResultCode.USER_ERROR, getExceptionJsonMessage());
    }

    @ExceptionHandler
    public Result<String> jsonProcessingException(JsonProcessingException e){
        log.error(getExceptionErrorLogMessage("json转换错误(json格式化不正确)"), e);
        return Result.failed(ResultCode.USER_ERROR, getExceptionJsonMessage());
    }

    @ExceptionHandler
    public Result<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error(getExceptionErrorLogMessage("方法参数类型无法匹配"), e);
        return Result.failed(ResultCode.PARAM_ERROR, getExceptionJsonMessage());
    }

    @ExceptionHandler
    public Result<String> nullPointerException(NullPointerException e) {
        log.error(getExceptionErrorLogMessage("空指针异常"), e);
        Result<String> result = Result.failed(ResultCode.SYSTEM_RESOURCE_ERROR, getExceptionJsonMessage());
        result.setMsg("空指针异常");
        return result;
    }

    @ExceptionHandler
    public Result<String> defaultException(Exception e) {
        log.error(getExceptionErrorLogMessage("未分类异常"), e);
//        if (mailEnable) {
//            MailMessage errMailMessage = null;
//            try {
//                errMailMessage = MailMessage.builder().title("出现未分类异常").body("defaultException: " + e.getLocalizedMessage() + " message: " + e.getMessage() + "\n stackTrace: " + om.writeValueAsString(e.getStackTrace())).build();
//            } catch (JsonProcessingException ex) {
//                log.error("defaultException: JsonProcessingException: ", ex);
//            }
//            applicationEventPublisher.publishEvent(new MailMessageEven(errMailMessage));
//        }
        return Result.failed(ResultCode.SYSTEM_EXECUTION_ERROR, getExceptionJsonMessage());
    }

    @ExceptionHandler
    public Result<String> defaultThrowable(Throwable throwable) {
        log.error(getExceptionErrorLogMessage("系统错误"), throwable);
        return Result.failed(ResultCode.SYSTEM_EXECUTION_ERROR, getExceptionJsonMessage());
    }

    private String getExceptionErrorLogMessage(String exceptionMessage){
        return getExceptionJsonMessage() +
                "; \t" +
                exceptionMessage;
    }

    private String getExceptionJsonMessage(){
        return "{" +
                TraceIdFilter.requestIdKey +
                ": " +
                MDC.get(TraceIdFilter.requestIdKey) +
                "}";
    }
}