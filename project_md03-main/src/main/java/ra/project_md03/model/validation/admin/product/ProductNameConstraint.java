package ra.project_md03.model.validation.admin.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.project_md03.model.dao.category.CategoryDao;
import ra.project_md03.model.dao.product.ProductDao;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.product.ProductService;
import ra.project_md03.model.validation.admin.category.CategoryNameUnique;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ProductNameConstraint implements ConstraintValidator<ProductNameUnique, String> {
    @Autowired
    private ProductService productService;

    @Override
    public void initialize(ProductNameUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !productService.checkProductNameExist(name);
    }
}
