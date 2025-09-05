package phunla2784.edu.vn.website.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import phunla2784.edu.vn.website.validation.validator.MinAgeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinAgeValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
    String message() default "Invalid age";
    int value();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}