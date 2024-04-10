package ra.project_md03.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.dto.user.UserCheckoutDTO;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.entity.CartItemDB;
import ra.project_md03.model.entity.Order;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.entity.User;
import ra.project_md03.model.service.cart.CartItemDBService;
import ra.project_md03.model.service.order.OrderService;
import ra.project_md03.model.service.product.ProductService;
import ra.project_md03.model.service.user.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
public class CheckoutController {
    @Autowired
    HttpSession httpSession;
    @Autowired
    private CartItemDBService cartItemDBService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/checkout")
    public String checkout(Model model) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLoginDTO == null) {
            return "redirect:/signin?action=checkout";
        }
        //tim user dua tren userLoginDTO
        User userLogin = userService.findById(userLoginDTO.getUserId());

        //tao 1 userCheckoutDTO va cast tu gia tri user tim duoc phia tren
        UserCheckoutDTO userCheckoutDTO = new UserCheckoutDTO();
        userCheckoutDTO.setUserId(userLogin.getUserId());
        userCheckoutDTO.setUsername(userLogin.getUsername());
        userCheckoutDTO.setAddress(userLogin.getAddress());
        userCheckoutDTO.setPhone(userLogin.getPhone());
        userCheckoutDTO.setEmail(userLogin.getEmail());
        userCheckoutDTO.setNote("");

        //lay cac gia tri listCart, cartTotal, userCheckoutDTO va chuyen sang view
        List<CartItemDB> list = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(userLogin.getUserId()));


        model.addAttribute("totalCart", httpSession.getAttribute("totalCart"));
        model.addAttribute("coupons", httpSession.getAttribute("coupons"));
        model.addAttribute("user", userCheckoutDTO);
        model.addAttribute("carts", list);
        return "userview/checkout";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String postCheckout(@Valid @ModelAttribute("user") UserCheckoutDTO userCheckoutDTO,
                               BindingResult result, @RequestParam("payment") String payment,
                               RedirectAttributes redirectAttributes) {
        User user = userService.findByMail(userCheckoutDTO.getEmail());
        List<CartItemDB> list = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(user.getUserId()));
        Order order = new Order();
        Object totalObj = httpSession.getAttribute("totalCart");
        if (totalObj != null) {
            BigDecimal total = new BigDecimal(totalObj.toString());
            // Set the order total
            order.setOrderTotal(total);
        } else {
            System.out.println("Error: Attribute 'totalCart' not found or is not a BigDecimal");
        }

        order.setNote(userCheckoutDTO.getNote());
        order.setUser(user);
        order.setAddress(userCheckoutDTO.getAddress());
        order.setOrder_at(Date.valueOf(LocalDate.now()));
        order.setPayment(payment);

        for (CartItemDB cartItemDB : list) {
            for (Product product : productService.findAll()) {
                if (Objects.equals(cartItemDB.getProduct().getProductId(), product.getProductId())) {
                    if (cartItemDB.getQuantity() > product.getStock()) {
                        redirectAttributes.addFlashAttribute("errQuantity", "Quantity must not be greater than stock!");
                        return "redirect:/cart";
                    } else {
                        product.setStock(product.getStock() - cartItemDB.getQuantity());
                        productService.update(product);
                    }
                }
            }
        }
        //luu so luong san pham cuar userLogin len session
        orderService.save(order, list);
        cartItemDBService.deleteCartItem(cartItemDBService.getCartID(user.getUserId()));
        List<CartItemDB> cartItemDBList = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(user.getUserId()));
        int totalProduct = cartItemDBList.stream().mapToInt(CartItemDB::getQuantity).sum();
        httpSession.setAttribute("cartTotalProduct", totalProduct);
        return "userview/order-completed";
    }

}
