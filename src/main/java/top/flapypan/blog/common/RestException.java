package top.flapypan.blog.common;

/**
 * 自定义业务异常
 */
public class RestException extends RuntimeException {
    final int code;

    public RestException(int code, String message) {
        super(message);
        this.code = code;
    }
}
