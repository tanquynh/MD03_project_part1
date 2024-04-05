package ra.project_md03.model.validation.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.project_md03.model.dao.category.CategoryDao;
import ra.project_md03.model.service.category.CategoryServiceImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CategoryNameConstraint implements ConstraintValidator<CategoryNameUnique, String> {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void initialize(CategoryNameUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !categoryDao.checkCategoryNameExist(value);
    }
}
