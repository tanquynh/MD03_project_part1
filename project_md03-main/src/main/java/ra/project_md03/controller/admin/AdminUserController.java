package ra.project_md03.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.entity.User;
import ra.project_md03.model.service.user.UserService;

import java.util.List;

import static ra.project_md03.model.dao.user.UserDaoIMPL.totalPagePagination;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping({"/user", "/user{id}"})
    public String index(Model model, @PathVariable(value = "id", required = false) Integer page, @RequestParam(value = "search", required = false) String searchName) {
        int currentPage = (page != null && page > 1) ? page : 1;
        List<User> userList;
        int totalUserSearch = 0;
        if (searchName != null && !searchName.isEmpty()) {
            totalUserSearch = userService.findAll().size();
            userList = userService.search(searchName);
            model.addAttribute("totalPage", totalUserSearch);

        } else {
            userList = userService.pagination(5, currentPage);
            model.addAttribute("totalPage", totalPagePagination);

        }
        int activeUser = userService.countUserByStatus(true);
        int blockedUser = userService.countUserByStatus(false);
        model.addAttribute("totalUserSearch", totalUserSearch);
        model.addAttribute("userList", userList);
        model.addAttribute("activeUser", activeUser);
        model.addAttribute("blockedUser", blockedUser);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("nameSearch", searchName);
        if (searchName != null && !searchName.isEmpty()) {
            userList = userService.search(searchName);
        } else {
            userList = userService.findAll();
        }
        model.addAttribute("userList", userList);
        return "admin/user/users";
    }
    @GetMapping("/user-change/{userId}")
    public String changeStatus(@PathVariable("userId") Integer id, RedirectAttributes redirectAttributes) {
        userService.changeStatus(id);
        User user = userService.findById(id);
        if (!user.isRole()) {
            redirectAttributes.addFlashAttribute("message", "Can't change ADMIN's status!");
        } else {
            if (user.isUserStatus()) {
                redirectAttributes.addFlashAttribute("message", "Blocked user!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Unblocked user!");
            }
        }
        return "redirect:/admin/user";
    }

}
