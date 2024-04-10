package ra.project_md03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.dto.user.UserLoginCheck;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.dto.user.UserRegisterDTO;
import ra.project_md03.model.service.user.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


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
    public String signupPost(@Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO, BindingResult result, Model model,
                             @RequestParam("confirm-password") String confirmedPassword, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "userview/signup";
        }

        if (userService.findByMail(userRegisterDTO.getEmail()) != null) {
            model.addAttribute("errEmail", "Email has already been registered!");
            return "redirect:/signup";
        }

        if (!userService.checkConfirmedPassword(userRegisterDTO.getPassword(), confirmedPassword)) {
            model.addAttribute("errRePassword", "Incorrect password confirmation!");
            return "redirect:/signup";
        }

        if (userService.register(userRegisterDTO)) {
            model.addAttribute("message", "Account created successfully!");
        }

        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        UserLoginCheck user = new UserLoginCheck();
        model.addAttribute("user", user);
        return "userview/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@Valid @ModelAttribute("user") UserLoginCheck user, BindingResult bindingResult,
                            @RequestParam(value = "action", required = false) String action, HttpSession session) {
        UserLoginDTO userLogin = userService.login(user.getEmail(), user.getPassword());
        if (userLogin != null) {
            if (!userLogin.isUserStatus()) {
                return "redirect:/sign-up";
            } else {
                if (userLogin.isRole()) {
                    session.setAttribute("userLoginUser", userLogin);
                    System.out.println(session.getAttribute("userLoginUser"));
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
                            return "redirect:/profile";
                        default:
                            return "redirect:/";
                    }
                } else {
                    session.setAttribute("userLoginAdmin", userLogin);
                    return "redirect:/admin";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/logout-user")
    public String logoutUser() {
        session.removeAttribute("userLoginUser");
        session.removeAttribute("cartTotalProduct");
        return "redirect:/?error=true";
    }

    @GetMapping("/logout-admin")
    public String logoutAdmin() {
        session.removeAttribute("userLoginAdmin");
        return "redirect:/?error=true";
    }
}
