package ra.project_md03.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.validation.FileNotNull;

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
    private String productName;
    private BigDecimal price;
    private String description;
    private int brandId;
    private int stock;
    private MultipartFile image;
    private int categoryId;
    private List<MultipartFile> subImage;
}
