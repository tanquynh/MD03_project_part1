package ra.project_md03.model.service.compare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.dao.compare.CompareDao;
import ra.project_md03.model.dao.compare.CompareDaoImpl;
import ra.project_md03.model.entity.Compare;
import ra.project_md03.model.entity.Wishlist;

import java.util.List;

@Repository
public class CompareServiceImpl implements CompareService {
    @Autowired
    private CompareDaoImpl compareDao;

    @Override
    public List<Compare> findByUserId(Integer userId) {
        return compareDao.findByUserId(userId);
    }

    @Override
    public void save(Compare compare) {
        compareDao.save(compare);
    }

    @Override
    public void delete(Integer productId) {
        compareDao.delete(productId);
    }

    @Override
    public Compare findByProductId(Integer productId, Integer userId) {
        return compareDao.findByProductId(productId,userId);
    }
}
