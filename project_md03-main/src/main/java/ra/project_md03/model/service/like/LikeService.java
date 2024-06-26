package ra.project_md03.model.service.like;


import ra.project_md03.model.entity.Like;
import ra.project_md03.model.entity.Wishlist;

import java.util.List;
import java.util.Map;

public interface LikeService {
    Map<Integer, Integer> findAll();
    void save(Integer userId, Integer productId);
    Integer countLikeInProduct(Integer productId);
}
