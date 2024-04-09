package ra.project_md03.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.project_md03.model.service.category.CategoryService;
import ra.project_md03.model.service.category.CategoryServiceImpl;
import ra.project_md03.model.service.order.OrderService;
import ra.project_md03.model.service.product.ProductService;
import ra.project_md03.model.service.product.ProductServiceImpl;
import ra.project_md03.model.service.user.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryServiceImpl categoryService;
//    @Autowired
//    private OrderService orderService;

    @RequestMapping("")
    public String index(Model model) {
        int categoryCount = categoryService.findAll().size();
        int productCount = productService.findAll().size();
        int userCount = userService.findAll().size();
//        int orderCount = orderService.findAll().size();
        model.addAttribute("categoryCount",categoryCount);
        model.addAttribute("productCount",productCount);
        model.addAttribute("userCount",userCount);
//        model.addAttribute("orderCount",orderCount);
        return "/admin/index";
    }

}
