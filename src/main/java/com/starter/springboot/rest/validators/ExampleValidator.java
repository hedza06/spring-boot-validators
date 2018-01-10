package com.starter.springboot.rest.validators;

import com.starter.springboot.enumeration.ExampleProfession;
import com.starter.springboot.rest.dto.ExampleDTO;
import com.starter.springboot.services.ExampleService;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Description(value = "Example Validator Class.")
@Component
public class ExampleValidator implements Validator {

    private ExampleService exampleService;

    /**
     * Constructor / dependency injector.
     * @param exampleService - example service dependency.
     */
    public ExampleValidator(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ExampleDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        ExampleDTO exampleDTO = (ExampleDTO) target;

        // validate username field
        if (exampleDTO.getUsername() == null || StringUtils.isEmpty(exampleDTO.getUsername()))
        {
            errors.rejectValue("username",
                    "username.required",
                    "Field `username` is required.");
            return;
        }

        if (!(exampleDTO.getUsername() instanceof String))
        {
            errors.rejectValue("username",
                    "username.invalid",
                    "Invalid type of `username` parameter");

            return;
        }

        if (isUsernameInUse(exampleDTO.getUsername()))
        {
            errors.rejectValue("username",
                    "username.already_taken",
                    "Field `username` is already taken.");

            return;
        }

        // validate employed
        if (exampleDTO.getEmployed() == null)
        {
            errors.rejectValue("employed",
                    "employed.required",
                    "Field `employed` is required");
            return;
        }

        // validate profession property
        if (exampleDTO.getEmployed() && exampleDTO.getProfession() == null)
        {
            errors.rejectValue("profession",
                    "profession.required",
                    "Field `profession` is required");
            return;
        }

        if (exampleDTO.getEmployed()
                && !EnumUtils.isValidEnum(ExampleProfession.class, exampleDTO.getProfession()))
        {
            errors.rejectValue("profession",
                    "profession.invalid",
                    "Invalid type of `profession` parameter.");
            return;
        }

        if (!exampleDTO.getEmployed() && exampleDTO.getProfession() != null)
        {
            errors.rejectValue("profession",
                    "profession.provided",
                    "Do not specify profession if `employed` field is false or 0.");

            return;
        }

        // validate age property
        if (exampleDTO.getAge() == null)
        {
            errors.rejectValue("age", "age.required", "Field `age` is required.");
            return;
        }

        if (exampleDTO.getAge() != null && exampleDTO.getAge() == 0)
        {
            errors.rejectValue("age", "age.invalid", "Field `age` is invalid.");
            return;
        }

        if (exampleDTO.getAge() != null && exampleDTO.getAge() < 18)
        {
            errors.rejectValue("age",
                    "age.invalid",
                    "Field `age` must be greater than 18.");
        }
    }

    private Boolean isUsernameInUse(String username) {
        Boolean result = this.exampleService.isUsernameInUse(username);
        return result;
    }
}
