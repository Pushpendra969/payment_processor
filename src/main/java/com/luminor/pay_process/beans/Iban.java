package com.luminor.pay_process.beans;

import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = IbanConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Iban {

    public String message() default "Invalid IBAN";

    public Class<?>[] groups()default {};

    public Class<? extends Payload>[]payload() default {};
}
