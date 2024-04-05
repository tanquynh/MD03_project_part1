package ra.project_md03.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ra.project_md03.model.validation.admin.category.CategoryNameUnique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    @NotEmpty(message = "CategoryName must not be empty!")
    @CategoryNameUnique
    private String categoryName;
    @NotNull(message = "ParentId must not be empty!")
    private Integer parentId;
    private MultipartFile img;


}
