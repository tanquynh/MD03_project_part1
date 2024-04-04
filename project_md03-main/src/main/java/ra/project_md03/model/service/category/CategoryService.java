package ra.project_md03.model.service.category;


import ra.project_md03.model.dto.category.CategoryDTO;
import ra.project_md03.model.dto.category.CategoryEditDTO;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.service.IGenericService;

import java.util.List;

public interface CategoryService  {
     void saveOrUpdate(CategoryDTO categoryDTO);
     void changeStatus(Integer id);
     List<Category> findParent();
     Integer getTotalPagePagination(Integer limit, Integer currentPage);
     Integer getTotalPageSearch(String searchName);
     Integer countCategory(Boolean b);
     Boolean findByName(String name);
     Boolean checkCategoryNameExist(String categoryName);
     Boolean checkCategoryNameExistEdit(String categoryName,String name);
     Boolean checkParentId(Integer parentId);
     List<Category>findByParentId(Integer parentId);

     void Update(Category category);
     void Update(CategoryEditDTO categoryEditDTO);
}
