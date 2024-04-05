package ra.project_md03.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project_md03.model.validation.admin.category.CategoryNameUnique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Category {
    private int categoryId;
    @NotEmpty(message = "CategoryName must not be empty!")
    @CategoryNameUnique
    private String categoryName;
    private boolean categoryStatus;
    @NotNull(message = "ParentId must not be empty!")
    private int parentId;
    private String image;
}
