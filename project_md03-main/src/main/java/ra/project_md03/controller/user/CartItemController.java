package ra.project_md03.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.entity.CartItem;
import ra.project_md03.model.entity.CartItemDB;
import ra.project_md03.model.entity.Coupons;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.cart.CartDBServiceImpl;
import ra.project_md03.model.service.cart.CartItemDBService;
import ra.project_md03.model.service.coupons.CouponsService;
import ra.project_md03.model.service.coupons.CouponsServiceImpl;
import ra.project_md03.model.service.product.ProductServiceImpl;
import ra.project_md03.model.service.wishlist.WishlistService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartItemController {
    @Autowired
    private CartItemDBService cartItemDBService;
    @Autowired
    private CartDBServiceImpl cartDBService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private HttpSession session;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private CouponsServiceImpl couponsService;

    @RequestMapping("/cart")
    public String cart(Model model) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            List<CartItemDB> cartItemList = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(userLoginDTO.getUserId()));
            model.addAttribute("carts", cartItemList);
            session.setAttribute("cartItem", cartItemList);
            int cartTotal = cartItemDBService.getCartTotal(cartItemList);
            model.addAttribute("cartTotal", cartTotal);
            model.addAttribute("totalCart", cartTotal);
        } else {
            return "redirect:/login?action=cart";
        }
        return "userview/shop-cart";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public String cart(@RequestParam(name = "couponCode", required = false) String couponCode, Model model) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            List<CartItemDB> cartItemList = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(userLoginDTO.getUserId()));
            model.addAttribute("carts", cartItemList);
            int cartTotal = cartItemDBService.getCartTotal(cartItemList);
            model.addAttribute("cartTotal", cartTotal);
            session.setAttribute("cartTotal",cartTotal);
            List<Coupons> couponsList = couponsService.findAll();
            model.addAttribute("couponsList", couponsList);
            if (couponCode != null && !couponCode.isEmpty()) {
                Coupons appliedCoupon = couponsList.stream()
                        .filter(coupon -> coupon.getCode().equals(couponCode))
                        .findFirst()
                        .orElse(null);
                if (appliedCoupon != null) {
                    model.addAttribute("appliedCoupon", (cartTotal * Integer.parseInt(appliedCoupon.getDiscount().replace("%", ""))) / 100);
                    model.addAttribute("totalCart", (cartTotal - (cartTotal * Integer.parseInt(appliedCoupon.getDiscount().replace("%", ""))) / 100));
                    session.setAttribute("totalCart", (cartTotal - (cartTotal * Integer.parseInt(appliedCoupon.getDiscount().replace("%", ""))) / 100));
                    session.setAttribute("coupons", (cartTotal * Integer.parseInt(appliedCoupon.getDiscount().replace("%", ""))) / 100);
                    ;
                }
                return "userview/shop-cart";
            }
            model.addAttribute("appliedCoupon", 0);
            model.addAttribute("totalCart", cartTotal);
            session.setAttribute("coupons", 0);
            session.setAttribute("totalCart", cartTotal);
        } else {
            return "redirect:/login?action=cart";
        }
        return "userview/shop-cart";
    }

    @RequestMapping("/addToCartQty")
    public String addCart(@RequestParam("quantity") Integer quantity,
                          @RequestParam("productId") Integer id) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
        if (userLoginDTO == null) {
            return "redirect:/login?action=product";
        } else {
            CartItemDB cartItemDB = new CartItemDB();
            Product product = productService.findById(id);
            cartItemDB.setCartId(cartItemDBService.getCartID(userLoginDTO.getUserId()));
            cartItemDB.setProduct(product);
            cartItemDB.setQuantity(quantity);
            if (!cartItemDBService.addToCart(cartItemDB, cartItemDB.getCartId())) {
                System.out.println("1234");
            }
            return "redirect:/cart";
        }
    }

    @RequestMapping(value = "/addToCart")
    @ResponseBody
    public String addToCart(@RequestParam("productId") Integer productId) {
        UserLoginDTO userLogin = (UserLoginDTO) session.getAttribute("userLoginUser");
        if (userLogin == null) {
            return "redirect:/login?action=product";
        } else {
            CartItemDB cartItemDB = new CartItemDB();
            Product product = productService.findById(productId);
            cartItemDB.setCartId(cartItemDBService.getCartID(userLogin.getUserId()));
            cartItemDB.setProduct(product);
            cartItemDB.setQuantity(1);
            cartItemDBService.save(cartItemDB);
        }
        return "redirect:/product";
    }

    @PostMapping("/add-cart")
    public String addCartFromWishlist(@RequestParam("productId") Integer productId) {
        UserLoginDTO userLogin = (UserLoginDTO) session.getAttribute("userLoginUser");
        if (userLogin == null) {
            return "redirect:/login?action=product";
        } else {
            CartItemDB cartItemDB = new CartItemDB();
            Product product = productService.findById(productId);

            cartItemDB.setCartId(cartItemDBService.getCartID(userLogin.getUserId()));
            cartItemDB.setProduct(product);
            cartItemDB.setQuantity(1);
            cartItemDBService.save(cartItemDB);
            wishlistService.delete(productId);
        }
        return "redirect:/wishlist";
    }

    @GetMapping("/cart-delete/{id}")
    public String deleteCartItem(@PathVariable("id") Integer productId) {
        UserLoginDTO userLogin = (UserLoginDTO) session.getAttribute("userLoginUser");
        cartItemDBService.delete(productId, cartItemDBService.getCartID(userLogin.getUserId()));
        return "redirect:/cart";
    }

    @GetMapping("/cart-delete")
    public String deleteAllCartItem() {
        UserLoginDTO userLogin = (UserLoginDTO) session.getAttribute("userLoginUser");
        int cartId = cartItemDBService.getCartID(userLogin.getUserId());
        cartItemDBService.deleteCartItem(cartId);
        return "redirect:/cart";
    }

    @GetMapping("/cart-increase/{id}")
    public String increase(@PathVariable("id") Integer productId, RedirectAttributes redirectAttributes) {
        UserLoginDTO userLogin = (UserLoginDTO) session.getAttribute("userLoginUser");
        int stock = productService.findById(productId).getStock();
        if (cartItemDBService.increase(productId, cartItemDBService.getCartID(userLogin.getUserId()))) {
            redirectAttributes.addFlashAttribute("errQuantity", "Quantity must not be greater than stock!");
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart-decrease/{id}")
    public String decrease(@PathVariable("id") Integer productId) {
        UserLoginDTO userLogin = (UserLoginDTO) session.getAttribute("userLoginUser");
        cartItemDBService.decrease(productId, cartItemDBService.getCartID(userLogin.getUserId()));
        return "redirect:/cart";
    }

}
