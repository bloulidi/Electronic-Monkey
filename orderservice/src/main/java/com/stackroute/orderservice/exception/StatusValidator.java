package com.stackroute.orderservice.exception;

import com.stackroute.orderservice.model.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<StatusConstraint, String> {
    @Override
    public boolean isValid(String categoryField, ConstraintValidatorContext cxt) {
        return Status.isInEnum(categoryField);
    }
}
