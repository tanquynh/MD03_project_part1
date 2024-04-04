package ra.project_md03.model.service.wishlist;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.wishlist.WishlistDao;
import ra.project_md03.model.entity.Wishlist;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishlistDao wishlistDao;
    @Override
    public List<Wishlist> findByUserId(Integer userId) {
        return wishlistDao.findByUserId(userId);
    }

    @Override
    public void save(Wishlist wishlist) {
        wishlistDao.save(wishlist);
    }

    @Override
    public void delete(Integer productId) {
        wishlistDao.delete(productId);
    }

    @Override
    public Wishlist findByProductId(Integer productId, Integer userId) {
        return wishlistDao.findByProductID(productId, userId);
    }
}
