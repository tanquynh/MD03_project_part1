package ra.project_md03.model.validation.admin.category;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = CategoryNameConstraint.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryNameUnique {
    String message() default "Category Name is existed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
