package ra.project_md03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.project_md03.model.dto.user.UserLoginCheck;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.dto.user.UserRegisterDTO;
import ra.project_md03.model.entity.User;
import ra.project_md03.model.service.user.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

    @RequestMapping("/index")
    public String index() {
        return "userview/index";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("user", userRegisterDTO);
        return "userview/signup";
    }

    @RequestMapping(value = "/signup-post", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        UserLoginCheck user = new UserLoginCheck();
        model.addAttribute("user", user);
        return "userview/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute("user") UserLoginCheck user,
                            @RequestParam(value = "action", required = false) String action) {
        UserLoginDTO userLogin = userService.login(user.getEmail(), user.getPassword());
        if (userLogin != null) {
            if (!userLogin.isUserStatus()) {
                return "redirect:/sign-up";
            } else {
                if (userLogin.isRole()) {
                    session.setAttribute("userLoginUser", userLogin);

                    if (action == null) {
                        action = "";
                    }
                    switch (action) {
                        case "product":
                            return "redirect:/shop-left-sidebar";
                        case "cart":
                            return "redirect:/cart";
                        case "checkout":
                            return "redirect:/checkout";
                        case "wishlist":
                            return "redirect:/wishlist";
                        case "profile":
                            return "redirect:/my-account";
                        default:
                            return "redirect:/index";
                    }
                } else {
                    session.setAttribute("userLoginAdmin", userLogin);
                    return "redirect:/admin";

                }
            }
        }
        return "redirect:/login";
    }

}
