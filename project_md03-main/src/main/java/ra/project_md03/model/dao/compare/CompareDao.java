package ra.project_md03.model.dao.compare;

import ra.project_md03.model.entity.Compare;
import ra.project_md03.model.entity.Wishlist;

import java.util.List;

public interface CompareDao {
    List<Compare> findByUserId (Integer id);
    void save(Compare compare);
    void delete(Integer productId);
    Compare findByProductID(Integer product,Integer userId);
}
