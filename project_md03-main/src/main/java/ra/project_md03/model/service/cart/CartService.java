package ra.project_md03.model.service.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.entity.CartItem;
import ra.project_md03.model.entity.Product;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CartService {
    List<CartItem> cartItems = new ArrayList<>();
    @Autowired
    CartItemDBService cartItemDBService;
    @Autowired
    HttpSession httpSession;
    public List<CartItem> getCartItems() {
        cartItems = ((httpSession.getAttribute("carts") != null) ? (List<CartItem>) httpSession.getAttribute("carts") : new ArrayList<>());
        return cartItems;
    }


    public void addToCart(CartItem cartItem) {
        CartItem oldCartItem = findCartItemByProduct(cartItem.getProduct());
        if (oldCartItem != null) {
            oldCartItem.setQuantity(oldCartItem.getQuantity() + cartItem.getQuantity());
        } else {
//        day item vao cart
            cartItems.add(cartItem);
        }
        httpSession.setAttribute("carts", cartItems);
    }

    public void addToCartQuick(CartItem cartItem) {
        CartItem oldCartItem = findCartItemByProduct(cartItem.getProduct());
        if (oldCartItem != null) {
            oldCartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
//        day item vao cart
            cartItems.add(cartItem);
        }
//luu vao session
        httpSession.setAttribute("carts", cartItems);
    }


    public void increase(Integer productId) {
//        CartItem cartItem = (CartItem) cartItems.stream().filter(cart -> cart.getProduct().getProductId() == productId);
        CartItem cartItem = findCartItemByProductId(productId);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        //luu vao session
        httpSession.setAttribute("carts", cartItems);
    }

    public void decrease(Integer productId) {
//        CartItem cartItem = (CartItem) cartItems.stream().filter(cart -> cart.getProduct().getProductId() == productId);
        CartItem cartItem = findCartItemByProductId(productId);
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        if (cartItem.getQuantity() == 0) {
            cartItems.remove(cartItem);
        }
        //luu vao session
        httpSession.setAttribute("carts", cartItems);
    }

    public void delete(Integer productId) {
        cartItems.removeIf(cartItem -> Objects.equals(cartItem.getProduct().getProductId(), productId));
        //luu vao session
        httpSession.setAttribute("carts", cartItems);
    }

    public CartItem findCartItemByProduct(Product product) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == product.getProductId()) {
                return cartItem;
            }
        }
        return null;
    }

    public BigDecimal getCartTotal(List<CartItem> cartItems) {
        BigDecimal cartTotal = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemTotal = BigDecimal.valueOf(cartItem.getQuantity());
            cartTotal = cartTotal.add(itemTotal);
        }
        return cartTotal;
    }

    public CartItem findCartItemByProductId(Integer id) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == id) {
                return cartItem;
            }
        }
        return null;
    }

}
