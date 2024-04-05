package ra.project_md03.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.validation.FileNotNull;
import ra.project_md03.model.validation.admin.product.ProductNameUnique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    @NotEmpty(message = "Product Name must not be empty!")
    @ProductNameUnique
    private String productName;
    @Positive(message = "Price must be greater than 0!")
    private BigDecimal price;
    @NotEmpty(message = "Description must not be empty!")
    private String description;
    private int brandId;
    @PositiveOrZero(message = "Stock must be greater than or is 0!")
    private int stock;
    @FileNotNull(message = "Image must not be null!")
    private MultipartFile image;
    private int categoryId;
    @FileNotNull(message = "Image must not be null!")
    private List<MultipartFile> subImage;
}
