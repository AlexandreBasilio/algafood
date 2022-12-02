package com.algaworks.algafood.core.validation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMin;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {}
)
@DecimalMin("1")
public @interface TaxaFrete {

    @OverridesAttribute(constraint = DecimalMin.class, name="message")
    String message() default "{TaxaFrete.invalida}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
