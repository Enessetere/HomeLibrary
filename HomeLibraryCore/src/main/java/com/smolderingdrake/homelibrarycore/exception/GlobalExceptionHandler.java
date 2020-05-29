package com.smolderingdrake.homelibrarycore.exception;

import com.smolderingdrake.homelibrarycore.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleGlobalException(final Exception exception) {
        return ErrorMessage.builder()
                .message("Global Exception")
                .details(List.of(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorMessage handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        return ErrorMessage.builder()
                .message("Method type not supported. Supported methods:")
                .details(List.of(Objects.requireNonNull(exception.getSupportedMethods())))
                .build();
    }
}
