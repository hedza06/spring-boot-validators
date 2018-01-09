package com.starter.springboot.exceptions;

import org.springframework.context.annotation.Description;
import org.springframework.validation.Errors;

@Description(value = "Custom exception class that handles validation errors.")
public class ValidationException extends Exception {

    private Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

}
