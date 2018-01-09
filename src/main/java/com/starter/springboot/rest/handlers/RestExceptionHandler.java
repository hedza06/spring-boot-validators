package com.starter.springboot.rest.handlers;

import com.starter.springboot.exceptions.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex)
    {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST);
        error.setMessage("Validation errors");

        error.addValidationErrors(ex.getErrors().getFieldErrors());

        return buildErrorResponseEntity(error);
    }

    private ResponseEntity<Object> buildErrorResponseEntity(ApiError error) {
        return new ResponseEntity<>(error, error.getStatus());
    }
}
