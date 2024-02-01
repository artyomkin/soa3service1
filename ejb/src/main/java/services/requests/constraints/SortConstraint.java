package services.requests.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortConstraintValidator.class)
public @interface SortConstraint {
    String message() default "sort";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
