package com.smolderingdrake.homelibrarycore.exception;

import com.smolderingdrake.homelibrarycore.controller.AuthorController;
import com.smolderingdrake.homelibrarycore.model.ErrorMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice(assignableTypes = AuthorController.class)
public class AuthorEndpointExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthorException.class)
    public ErrorMessage handleAuthorException(final AuthorException exception) {
        return ErrorMessage.builder()
                .message("Change your request")
                .details(List.of(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorMessage handleNoSuchElementException(final NoSuchElementException exception) {
        return ErrorMessage.builder()
                .message("Index out of range.")
                .details(List.of(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return ErrorMessage.builder()
                .message("Change your request to meet following condition")
                .details(getValidationMessages(exception))
                .build();
    }

    private List<String> getValidationMessages(final MethodArgumentNotValidException exception) {
        return getValidationErrors(exception).stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    private List<ObjectError> getValidationErrors(final MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors();
    }
}
