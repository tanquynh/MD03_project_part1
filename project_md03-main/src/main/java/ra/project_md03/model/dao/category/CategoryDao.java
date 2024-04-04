package ra.project_md03.model.dao.category;

import ra.project_md03.model.dao.IGenericDao;
import ra.project_md03.model.entity.Category;

import java.util.List;

public interface CategoryDao extends IGenericDao<Category,Integer, String> {
    List<Category> findParent();
    Integer getTotalPagePagination(Integer limit, Integer currentPage);
    Integer getTotalPageSearch(String searchName);
    Integer countCategory(Boolean b);
    Boolean findByName(String name);
    Boolean checkCategoryNameExist(String categoryName);
    List<Category> findByParentId(Integer patentId);
}
