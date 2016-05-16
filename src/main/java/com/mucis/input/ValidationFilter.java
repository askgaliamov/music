package com.mucis.input;

import java.util.Set;
import java.util.function.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidationFilter<T> implements Predicate<T> {

    private static Logger LOG = LoggerFactory.getLogger(ValidationFilter.class);

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public boolean test(final T record) {
        Set<ConstraintViolation<T>> validate = validator.validate(record);
        if (!validate.isEmpty()) {
            LOG.info("Invalid record: {}", record);
            return false;
        }
        return true;
    }
}
