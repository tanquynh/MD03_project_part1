package ra.project_md03.model.service.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.cart.CartDao;

@Service
public class CartDBServiceImpl implements CartDBService {
    @Autowired
    private CartDao cartDao;

    @Override
    public Integer checkCartUser(Integer userId) {
        return cartDao.checkCartUser(userId);
    }

    @Override
    public Integer save(Integer userId) {
        return cartDao.save(userId);
    }




}
