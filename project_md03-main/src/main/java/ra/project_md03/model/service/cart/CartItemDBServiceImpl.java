package ra.project_md03.model.service.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.cart.CartItemDBDao;
import ra.project_md03.model.entity.CartItemDB;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.product.ProductService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemDBServiceImpl implements CartItemDBService {

    @Autowired
    private CartItemDBDao cartItemDBDao;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private CartDBService cartDBService;
    @Autowired
    private ProductService productService;

    @Override
    public List<CartItemDB> findItemByUserId(Integer userId) {
        return cartItemDBDao.findItemByUserId(userId);
    }

    @Override
    public void save(CartItemDB item) {
        cartItemDBDao.save(item);
    }

    @Override
    public void delete(Integer productId, Integer cartId) {
        cartItemDBDao.delete(productId);
        getTotalProduct(cartItemDBDao.findItemByCartId(cartId));
    }

    @Override
    public boolean increase(Integer productId, Integer cartId) {
        CartItemDB cartItemDB = cartItemDBDao.findByProductId(productId, cartId);
        Product product = productService.findById(productId);
        if (cartItemDB.getQuantity() >= product.getStock()) {
            return true;
        }
        cartItemDBDao.increase(productId);
        getTotalProduct(cartItemDBDao.findItemByCartId(cartId));
        return false;
    }

    @Override
    public void decrease(Integer productId, Integer cartId) {
        CartItemDB cartItemDB = cartItemDBDao.findByProductId(productId, cartId);
        if (cartItemDB.getQuantity() > 1) {
            cartItemDBDao.decrease(productId);
        } else {
            cartItemDBDao.delete(productId);
        }

        getTotalProduct(cartItemDBDao.findItemByCartId(cartId));
    }

    @Override
    public CartItemDB findByProductId(Integer productId, Integer cartId) {
        return cartItemDBDao.findByProductId(productId, cartId);
    }

    @Override
    public List<CartItemDB> getCartUserLogin(Integer cartId) {
        List<CartItemDB> cartItemDBList = cartItemDBDao.findItemByCartId(cartId);
        getTotalProduct(cartItemDBList);
        return cartItemDBList;
    }

    public void getTotalProduct(List<CartItemDB> list) {
        int totalProduct = list.stream().mapToInt(CartItemDB::getQuantity).sum();
        httpSession.setAttribute("cartTotalProduct", totalProduct);
    }
    @Override
    public void deleteCartItem(Integer cartId) {
        cartItemDBDao.deleteCartItem(cartId);
    }
    @Override
    public boolean addToCart(CartItemDB cartItemDB, Integer cartId) {
        CartItemDB itemDB = cartItemDBDao.findByProductId(cartItemDB.getProduct().getProductId(), cartId);
        if (itemDB.getCartId() != 0) {
            if (itemDB.getQuantity() + cartItemDB.getQuantity() <= productService.findById(cartItemDB.getProduct().getProductId()).getStock())
                ;
            {
                itemDB.setQuantity(itemDB.getQuantity() + cartItemDB.getQuantity());
                return true;
            }
        } else {
            if (cartItemDB.getQuantity() <= productService.findById(cartItemDB.getProduct().getProductId()).getStock()) {
                cartItemDBDao.save(cartItemDB);
                return true;
            }
        }
        getTotalProduct(cartItemDBDao.findItemByCartId(cartId));
        return false;
    }

    @Override
    public Integer getCartID(Integer userId) {
        int cartUserId = cartDBService.checkCartUser(userId);
        if (cartUserId == 0) {
            cartUserId = cartDBService.save(userId);
        }
        return cartUserId;
    }

    @Override
    public Integer getCartTotal(List<CartItemDB> list) {
        BigDecimal total = BigDecimal.ZERO; // Khởi tạo tổng giá trị là 0

        for (CartItemDB item : list) {
            BigDecimal price = item.getProduct().getPrice();
            int quantity = item.getQuantity();

            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));
            total = total.add(itemTotal);
        }
        return total.intValue();
    }
}
