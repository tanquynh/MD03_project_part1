package ra.project_md03.model.dao.cart;

import ra.project_md03.model.entity.CartItemDB;

import java.util.List;

public interface CartItemDBDao {
    List<CartItemDB> findItemByUserId(Integer userId);
    void save(CartItemDB item);
    void delete(Integer productId);
    void increase(Integer productId);
    void decrease(Integer productId);
    CartItemDB findByProductId(Integer productId,Integer cartId);
    List<CartItemDB> findItemByCartId(Integer cartId);
    void deleteCartItem(Integer cartId);
}
