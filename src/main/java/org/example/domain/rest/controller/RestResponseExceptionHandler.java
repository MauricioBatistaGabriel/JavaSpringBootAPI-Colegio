package org.example.domain.rest.controller;

import org.example.domain.rest.ApiError;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError =
                new ApiError(NOT_FOUND, ex.getLocalizedMessage(),
                        "Erro 001");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
