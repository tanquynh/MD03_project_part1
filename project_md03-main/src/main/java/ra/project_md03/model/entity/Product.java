package ra.project_md03.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project_md03.model.validation.admin.product.ProductNameUnique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    private Integer productId;
    @NotEmpty(message = "ProductName must not be empty!")
    @ProductNameUnique
    private String productName;
    @Positive(message = "Price must be greater than 0!")
    private BigDecimal price;
    @NotEmpty(message = "Description must not be empty!")
    private String description;
    private Brand brand;
    @PositiveOrZero(message = "Stock must be greater than or is 0!")
    private int stock;
    private boolean productStatus = true;
    @NotNull(message = "Image must not be null!")
    private String image;
    private List<Image> imageList;
    private Category category;
}
