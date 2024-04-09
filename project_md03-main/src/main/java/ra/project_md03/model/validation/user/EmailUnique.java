package ra.project_md03.model.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailConstraint.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnique {
    String message() default "Email is already existed!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
