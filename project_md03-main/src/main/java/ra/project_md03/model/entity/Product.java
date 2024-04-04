package ra.project_md03.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String productName;
    private BigDecimal price;
    private String description;
    private Brand brand;
    private int stock;
    private boolean productStatus = true;
    private String image;
    private List<Image> imageList;
    private Category category;
}
