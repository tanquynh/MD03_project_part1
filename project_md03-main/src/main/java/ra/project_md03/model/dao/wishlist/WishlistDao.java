package ra.project_md03.model.dao.wishlist;

import ra.project_md03.model.entity.Wishlist;

import java.util.List;

public interface WishlistDao {
    List<Wishlist> findByUserId (Integer id);
    void save(Wishlist wishlist);
    void delete(Integer productId);
    Wishlist findByProductID(Integer product,Integer userId);
}
