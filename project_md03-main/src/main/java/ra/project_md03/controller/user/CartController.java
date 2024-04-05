package ra.project_md03.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.project_md03.model.entity.CartItem;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.cart.CartService;
import ra.project_md03.model.service.product.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CartService cartService;

//    @RequestMapping("/cart")
//    public String cart(Model model) {
//        List<CartItem> cartItems = cartService.getCartItems();
//        model.addAttribute("carts", cartItems);
//        BigDecimal cartTotal = cartService.getCartTotal(cartItems);
//        model.addAttribute("cartTotal", cartTotal);
//        return "userview/shop-cart";
//    }

    @RequestMapping(value = "/addToCart")
    @ResponseBody
    public String addToCart(@RequestParam("productId") Integer id) {
        CartItem cartItem = new CartItem();
        Product product = productService.findById(id);
        if (product == null) {
            return "error";
        }
        cartItem.setQuantity(1);
        cartItem.setProduct(product);
        cartService.addToCartQuick(cartItem);
        return "Product added to cart successfully!";
    }

    @RequestMapping(value = "/addToCartQty")
    @ResponseBody
    public String addToCart2(@RequestParam("productId") Integer id, @RequestParam("quantity") Integer qty) {
        CartItem cartItem = new CartItem();
        Product product = productService.findById(id);
        if (product == null) {
            return "error";
        }
        cartItem.setQuantity(qty);
        cartItem.setProduct(product);
        cartService.addToCartQuick(cartItem);
        return "Product added to cart successfully!";
    }
}
