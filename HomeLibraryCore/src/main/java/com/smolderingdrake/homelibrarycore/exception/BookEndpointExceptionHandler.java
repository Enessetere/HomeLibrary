package com.smolderingdrake.homelibrarycore.exception;

import com.smolderingdrake.homelibrarycore.controller.BookController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BookController.class)
public class BookEndpointExceptionHandler {
}
