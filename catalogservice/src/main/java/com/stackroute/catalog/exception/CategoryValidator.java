package com.stackroute.catalog.exception;

import com.stackroute.catalog.model.Category;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<CategoryConstraint, String> {
    @Override
    public boolean isValid(String categoryField, ConstraintValidatorContext cxt) {
        return Category.isInEnum(categoryField);
    }
}
