package ra.project_md03.model.dao.like;

import org.springframework.stereotype.Component;
import ra.project_md03.model.entity.Like;

import java.util.List;
import java.util.Map;

public interface LikeDao {
    Map<Integer, Integer> findAll();
    void save(Integer userId, Integer productId);
    Integer countLikeInProduct(Integer productId);
}
