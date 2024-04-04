package ra.project_md03.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.entity.Image;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductEditDTO {
    private Integer productId;
    private String productName;
    private BigDecimal price;
    private String description;
    private int brandId;
    private int stock;
    private MultipartFile image;
    private List<MultipartFile> subImage;
    private int categoryId;
}
