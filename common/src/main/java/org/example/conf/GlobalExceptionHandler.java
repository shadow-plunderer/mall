package org.example.conf;

import org.example.vo.R;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        return R.error(500, "Internal server error");
    }
}
