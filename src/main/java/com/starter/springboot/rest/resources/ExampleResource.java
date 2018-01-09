package com.starter.springboot.rest.resources;

import com.starter.springboot.exceptions.ValidationException;
import com.starter.springboot.rest.dto.ExampleDTO;
import com.starter.springboot.rest.validators.ExampleValidator;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Description(value = "Rest controller for handling example dto related requests.")
@RestController
@RequestMapping(value = "api")
public class ExampleResource {

    private ExampleValidator exampleValidator;

    /**
     * Constructor / dependency injector.
     * @param exampleValidator - example validator dependency.
     */
    public ExampleResource(ExampleValidator exampleValidator) {
        this.exampleValidator = exampleValidator;
    }

    /**
     * Method for storing example dto object.
     *
     * @param exampleDTO - object to be stored.
     * @return validation errors or success message.
     */
    @PostMapping(value = "store", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> exampleEndpoint(@RequestBody ExampleDTO exampleDTO)
        throws ValidationException
    {
        // setting up validator for example dto object.
        Errors potentialErrors = new BeanPropertyBindingResult(exampleDTO, "exampleDTO");
        ValidationUtils.invokeValidator(exampleValidator, exampleDTO, potentialErrors);

        // check for potential errors.
        if (potentialErrors.hasErrors()) {
            throw new ValidationException(potentialErrors);
        }

        // generate dummy response...
        Map<String, String> dummyResponse = new HashMap<>();
        dummyResponse.put("status", "success");
        dummyResponse.put("message", "Example DTO successfully stored.");

        return new ResponseEntity<>(dummyResponse, HttpStatus.CREATED);
    }

}
