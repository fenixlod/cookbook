package com.lunix.cookbook.utility;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuplicatedProductsValidator.class)
@Documented
public @interface UniqueProducts {
	String message() default "Contains dublicated elements";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
