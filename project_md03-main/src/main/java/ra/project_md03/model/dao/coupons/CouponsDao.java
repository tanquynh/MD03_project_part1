package ra.project_md03.model.dao.coupons;

import ra.project_md03.model.dao.IGenericDao;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.entity.Coupons;

import java.util.List;

public interface CouponsDao extends IGenericDao<Coupons,Integer, String> {
    void changeStatus(Integer id);
    Integer getTotalPagePagination(Integer limit, Integer currentPage);
    Integer getTotalPageSearch(String searchName);
    Integer countCoupons(Boolean b);
    Boolean findByName(String name);
    Boolean checkCouponsNameExist(String categoryName);
}
