package ra.project_md03.model.service.cart;


import ra.project_md03.model.entity.CartItemDB;

import java.util.List;

public interface CartItemDBService {
    List<CartItemDB> findItemByUserId(Integer userId);
    void save(CartItemDB item);
    void delete(Integer productId,Integer cartId);
    boolean increase(Integer productId,Integer cartId);
    void decrease(Integer productId,Integer cartId);
    CartItemDB findByProductId(Integer productId,Integer cartId);
    List<CartItemDB>getCartUserLogin(Integer userId);
     boolean addToCart(CartItemDB cartItemDB,Integer cartId);
     Integer getCartID(Integer userId);
     Integer getCartTotal(List<CartItemDB>list);
    void getTotalProduct(List<CartItemDB> list);
    void deleteCartItem(Integer cartId);
}
