package ra.project_md03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.category.CategoryServiceImpl;
import ra.project_md03.model.service.product.ProductServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private HttpSession session;
    @RequestMapping("/")
    public String index(Model model) {
        List<Product> productList = productService.findAllActiveStatus(true)
                .stream()
                .sorted(Comparator.comparing(Product::getPrice, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        model.addAttribute("productList", productList);
        return "userview/index";
    }
}
