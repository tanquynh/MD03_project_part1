package ra.project_md03.model.service.brand;


import ra.project_md03.model.dto.brand.BrandDTO;
import ra.project_md03.model.dto.category.CategoryDTO;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.entity.Category;

import java.util.List;

public interface BrandService {
    void saveOrUpdate(BrandDTO brand);

    void changeStatus(Integer id);

    Integer getTotalPagePagination(Integer limit, Integer currentPage);

    Integer getTotalPageSearch(String searchName);

    Integer countBrand(Boolean b);

    Boolean findByName(String name);

    Boolean checkBrandNameExist(String brandName);

    Boolean checkBrandNameExistEdit(String brandName, String name);
}
