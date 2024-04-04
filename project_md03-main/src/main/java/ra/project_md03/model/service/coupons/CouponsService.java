package ra.project_md03.model.service.coupons;


import ra.project_md03.model.dto.category.CategoryDTO;
import ra.project_md03.model.dto.coupons.CouponsDTO;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.entity.Coupons;

import java.util.List;

public interface CouponsService {
    void saveOrUpdate(CouponsDTO coupons);

    void changeStatus(Integer id);

    Integer getTotalPagePagination(Integer limit, Integer currentPage);

    Integer getTotalPageSearch(String searchName);

    Integer countCoupons(Boolean b);

    Boolean findByName(String name);

    Boolean checkCouponsNameExist(String categoryName);

}
