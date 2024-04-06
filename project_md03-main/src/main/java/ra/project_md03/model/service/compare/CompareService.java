package ra.project_md03.model.service.compare;

import ra.project_md03.model.entity.Compare;
import ra.project_md03.model.entity.Wishlist;

import java.util.List;

public interface CompareService {
    List<Compare> findByUserId(Integer userId);
    void save(Compare compare);
    void delete(Integer productId);
    Compare findByProductId(Integer productId,Integer userId);
}
