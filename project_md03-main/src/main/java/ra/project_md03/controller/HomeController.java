package ra.project_md03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.entity.CartItemDB;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.entity.Like;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.cart.CartItemDBService;
import ra.project_md03.model.service.category.CategoryServiceImpl;
import ra.project_md03.model.service.like.LikeService;
import ra.project_md03.model.service.product.ProductServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private HttpSession session;
    @Autowired
    private LikeService likeService;

    @RequestMapping("/")
    public String index(Model model) {
        Map<Integer, Integer> listLike = likeService.findAll();
        List<Category> categoryList = categoryService.findAll().stream().filter(Category::isCategoryStatus).limit(6).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);
        List<Product> productList = productService.findAllActiveStatus(true)
                .stream()
                .sorted(Comparator.comparing(Product::getPrice, Comparator.reverseOrder())).limit(8)
                .collect(Collectors.toList());
        model.addAttribute("productList", productList);
        model.addAttribute("listLike",listLike);
        return "userview/index";
    }

    @RequestMapping("/about")
    public String about() {
        return "userview/about";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "userview/contact";
    }

}
