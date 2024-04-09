package ra.project_md03.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.dto.user.UserEdit;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.entity.*;
import ra.project_md03.model.service.order.OrderService;
import ra.project_md03.model.service.product.ProductService;
import ra.project_md03.model.service.user.UserService;
import ra.project_md03.model.service.user.UserServiceImpl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class ProfileController {

    private String path;
    @Autowired
    HttpSession httpSession;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/profile")
    public String profile() {
        UserLoginDTO userLoginDTO = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            return "/userview/my-account";
        } else {
            return "redirect:/login?action=profile";
        }
    }

    @GetMapping("/order-history")
    public String orderHistory(Model model) {
        User userLogin = getProfile();
        if (userLogin != null) {
            model.addAttribute("user", userLogin);
            List<Order> orders = orderService.findByUserId(userLogin.getUserId());
            model.addAttribute("orders", orders);
        } else {
            return "redirect:/signin?action=profile";
        }
        return "userview/order-history";
    }

    @GetMapping("/order-show/{id}")
    public String showOrder(@PathVariable("id") Integer orderId, Model model) {
        User userLogin = getProfile();
        if (userLogin != null) {
            model.addAttribute("user", userLogin);
        } else {
            return "redirect:/signin?action=profile";
        }
        Order order = orderService.findByOrderId(orderId);
        model.addAttribute("order", order);

        List<OrderDetail> orderDetailList = orderService.findOrderDetailByOrderId(orderId);
        model.addAttribute("orderDetailList", orderDetailList);

        int cartTotal = (int) orderDetailList.stream().mapToDouble(orderDetail -> orderDetail.getOrderPrice() * orderDetail.getQuantity()).sum();
        model.addAttribute("cartTotal", cartTotal);
        return "userview/order-detail";
    }

    @GetMapping("/order-cancel/{id}")
    public String cancel(@PathVariable("id") Integer orderId, RedirectAttributes redirectAttributes) {
        orderService.changeStatus(2, orderId);
        List<OrderDetail> orderDetails = orderService.findOrderDetailByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            for (Product product : productService.findAll()) {
                if (Objects.equals(orderDetail.getProduct().getProductId(), product.getProductId())) {
                    product.setStock(product.getStock() + orderDetail.getQuantity());
                    productService.save(product);
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "Cancelled order successfully!");
        return "redirect:/profile/edit-profile";
    }

    @RequestMapping("/profile/edit-profile")
    public String editProfile(Model model) {
        UserEdit userEdit = new UserEdit();
        User userLogin = getProfile();
        userEdit.setId(userLogin.getUserId());
        userEdit.setEmail(userLogin.getEmail());
        userEdit.setPhone(userLogin.getPhone());
        userEdit.setAddress(userLogin.getAddress());
        userEdit.setUsername(userLogin.getUsername());
        userEdit.setPassword(userLogin.getPassword());
        model.addAttribute("user", userEdit);
        return "userview/my-accountsua";
    }

    @PostMapping("/profile/edit-profile")
    public String postEditProfile(@Valid @ModelAttribute("user") UserEdit user, BindingResult result,
                                  @RequestParam("img") MultipartFile file,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "userview/my-accountsua";
        }
        userService.update(user);
        redirectAttributes.addFlashAttribute("message", "Edit profile successfully!");

        return "redirect:/profile";
    }

    @RequestMapping("/profile/change-password")
    public String changePassword(Model model) {
        User userLogin = userService.findById(((UserLoginDTO) httpSession.getAttribute("userLoginUser")).getUserId());
        if (userLogin != null) {
            model.addAttribute("user", userLogin);
        } else {
            return "redirect:/login?action=profile-change-password";
        }
        return "userview/profile-change-password";
    }

    @RequestMapping(value = "/profile-change-password", method = RequestMethod.POST)
    public String postChangePassword(@RequestParam("old-password") String oldPassword,
                                     @RequestParam("new-password") String newPassword,
                                     @RequestParam("confirm-password") String confirmPassword,
                                     RedirectAttributes redirectAttributes) {
        User userLogin = getProfile();

        if (oldPassword != null && newPassword != null && confirmPassword != null) {
            if (!userService.checkPassword(oldPassword, userLogin)) {
                redirectAttributes.addFlashAttribute("errOldPassword", "Incorrect old password!");
                return "redirect:/profile-change-password";
            }
            if (newPassword.length() < 4 || newPassword.length() > 12) {
                redirectAttributes.addFlashAttribute("errNewPassword", "New password's length is from 4 to 12");
                return "redirect:/profile-change-password";
            }
            if (newPassword.equals(oldPassword)) {
                redirectAttributes.addFlashAttribute("errNewPassword", "New password must be different from old password!");
                return "redirect:/profile-change-password";
            }
            if (!userService.checkConfirmedPassword(newPassword, confirmPassword)) {
                redirectAttributes.addFlashAttribute("errConfirmPassword", "Incorrect password!");
                return "redirect:/profile-change-password";
            }
        } else {
            redirectAttributes.addFlashAttribute("errConfirmPassword", "Please fill password!");
            return "redirect:/profile-change-password";
        }
        userService.changePassword(newPassword, userLogin.getUserId());
        redirectAttributes.addFlashAttribute("message", "Change password successfully!");

        return "redirect:/order-history";
    }

    public User getProfile() {
        UserLoginDTO userLoginDTO = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            return userService.findById(userLoginDTO.getUserId());
        }
        return null;
    }
}
