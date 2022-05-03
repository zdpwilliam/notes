package com.william;

import com.william.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by william on 17-3-30.
 */
public class HibernateBeanValidator {

    public static void main(String[] args) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        User user = User.builder().age(10).build();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        for (ConstraintViolation<User> violation : constraintViolations) {
            System.out.println(violation.getMessage());
        }
    }
}
