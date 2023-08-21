package com.nagiur.onlineshop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

public class PasswordEqualValidator implements
        ConstraintValidator<PasswordEqual, Object> {

    private final static Logger LOGGER
            = LoggerFactory.getLogger(PasswordEqualValidator.class);
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    public void initialize(PasswordEqual constraint) {
        firstFieldName = constraint.first();
        secondFieldName = constraint.second();
        message = constraint.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            final Object firstObj = getValue(value, firstFieldName);
            final Object secondObj = getValue(value, secondFieldName);

            LOGGER.info("-----: {}", firstObj);
            LOGGER.info("-----: {}", secondObj);
            valid = Objects.equals(firstObj, secondObj);
        } catch (final Exception ignore) {
            // ignore
            LOGGER.info("----- Ignore -----");
        }

        if(!valid){
            LOGGER.info("--------------------- invalid password --------");
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        LOGGER.info("--------------------- Valid password --------");
        return valid;
    }

    private Object getValue(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessError, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        LOGGER.info("--- Get Value --- : {}", field.get(object));
        return field.get(object);
    }
}
