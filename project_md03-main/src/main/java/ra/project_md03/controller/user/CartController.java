//package ra.project_md03.controller.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import ra.project_md03.model.entity.CartItem;
//import ra.project_md03.model.entity.Product;
//import ra.project_md03.model.service.cart.CartService;
//import ra.project_md03.model.service.product.ProductServiceImpl;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Controller
//public class CartController {
//    @Autowired
//    private ProductServiceImpl productService;
//    @Autowired
//    private CartService cartService;
//
//    @RequestMapping("/cart")
//    public String cart(Model model) {
//        List<CartItem> cartItems = cartService.getCartItems();
//        model.addAttribute("carts", cartItems);
//        BigDecimal cartTotal = cartService.getCartTotal(cartItems);
//        model.addAttribute("cartTotal", cartTotal);
//        return "userview/cart";
//    }
//
//    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
//    public String addCart(@RequestParam("quantity") Integer quantity, @RequestParam("productId") Integer id) {
//        CartItem cartItem = new CartItem();
//        Product product = productService.findById(id);
//        cartItem.setProduct(product);
//        cartItem.setQuantity(quantity);
//        cartService.addToCart(cartItem);
//        return "redirect:/cart";
//    }
//
//    @RequestMapping(value = "/add-cart-quick", method = RequestMethod.POST)
//    public String addCartQuick(@RequestParam("product") Integer id) {
//        CartItem cartItem = new CartItem();
//        Product product = productService.findById(id);
//        cartItem.setQuantity(1);
//        cartItem.setProduct(product);
//        cartService.addToCartQuick(cartItem);
//        return "redirect:/shop-left-sidebar";
//    }
//
//    @RequestMapping("/cart-delete/{id}")
//    public String deleteCartItem(@PathVariable("id") Integer id) {
//        cartService.delete(id);
//        return "redirect:/cart";
//    }
//
//    @RequestMapping("/cart-derease/{id}")
//    public String decrease(@PathVariable("id") Integer id) {
//        cartService.decrease(id);
//        return "redirect:/cart";
//    }
//
//    @RequestMapping("/checkout")
//    public String checkout(Model model) {
//        List<CartItem> cartItems = cartService.getCartItems();
//        model.addAttribute("carts", cartItems);
//        BigDecimal cartTotal = cartService.getCartTotal(cartItems);
//        model.addAttribute("cartTotal", cartTotal);
//        return "userview/checkout";
//
//    }
//
//}
