package ra.project_md03.model.service.like;

import ra.project_md03.model.entity.Wishlist;

import java.util.List;

public class LikeServiceImpl {
    List<Wishlist> findByUserId(Integer userId);
    void save(Wishlist wishlist);
    void delete(Integer productId);
    Wishlist findByProductId(Integer productId,Integer userId);

}
