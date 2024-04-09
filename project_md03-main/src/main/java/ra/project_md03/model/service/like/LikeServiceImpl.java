package ra.project_md03.model.service.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.project_md03.model.dao.like.LikeDaoImpl;
import ra.project_md03.model.entity.Like;

import java.util.List;
import java.util.Map;

@Component
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeDaoImpl likeDao;

    @Override
    public Map<Integer, Integer> findAll(){
        return likeDao.findAll();
    }

    @Override
    public void save(Integer userId, Integer productId) {
        likeDao.save(userId, productId);
    }


    @Override
    public Integer countLikeInProduct(Integer productId) {
        return likeDao.countLikeInProduct(productId);
    }
}
