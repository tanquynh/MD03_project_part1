package ra.project_md03.model.validation.admin.product;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProductNameConstraint.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductNameUnique {
    String message() default "Product Name is existed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
