package top.flapypan.blog.common;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.flapypan.blog.vo.response.RestResult;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * security 异常处理
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResult<Void> handleSaTokenException(AuthenticationException e) {
        return RestResult.err(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * Spring Validation 异常处理
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResult<String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String message = objectError.getDefaultMessage();
        log.warn("字段错误 {}", message);
        return RestResult.err(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 业务异常处理
     */
    @ExceptionHandler(value = RestException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResult<String> handleRestException(RestException e) {
        log.error("业务错误", e);
        return RestResult.err(e.code, e.getMessage());
    }

    @ExceptionHandler(value = ServletException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResult<String> handleServletException(ServletException e) {
        return RestResult.err(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 其他异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResult<Void> handleException(Exception e) {
        log.error("服务器内部错误", e);
        return RestResult.err(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
