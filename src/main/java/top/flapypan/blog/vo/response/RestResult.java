package top.flapypan.blog.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 统一返回结构
 */
@Getter
@AllArgsConstructor
public class RestResult<T> {
    @JsonIgnore
    public static final int OK_STATUS = HttpStatus.OK.value();

    private int code;

    private T data;

    public boolean isSuccess() {
        return code == OK_STATUS;
    }

    public static <T> RestResult<T> ok() {
        return new RestResult<>(OK_STATUS, null);
    }

    public static <T> RestResult<T> ok(T data) {
        return new RestResult<>(OK_STATUS, data);
    }

    public static <T> RestResult<T> err(int code) {
        return new RestResult<>(code, null);
    }

    public static <T> RestResult<T> err(int code, T data) {
        return new RestResult<>(code, data);
    }
}
