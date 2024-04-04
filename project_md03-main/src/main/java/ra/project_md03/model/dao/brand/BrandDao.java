package ra.project_md03.model.dao.brand;

import ra.project_md03.model.dao.IGenericDao;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.entity.Category;

import java.util.List;

public interface BrandDao extends IGenericDao<Brand,Integer, String> {

    Integer getTotalPagePagination(Integer limit, Integer currentPage);
    Integer getTotalPageSearch(String searchName);
    Integer countBrand(Boolean b);
    Boolean findByName(String name);
    Boolean checkBrandNameExist(String categoryName);

}
