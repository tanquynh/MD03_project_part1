package ra.project_md03.controller.user;

import jdk.internal.icu.text.NormalizerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.entity.*;
import ra.project_md03.model.service.brand.BrandService;
import ra.project_md03.model.service.cart.CartItemDBService;
import ra.project_md03.model.service.category.CategoryServiceImpl;
import ra.project_md03.model.service.image.ImageService;
import ra.project_md03.model.service.product.ProductServiceImpl;
import ra.project_md03.model.service.user.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductUserController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CartItemDBService cartItemDBService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private HttpSession session;

    @RequestMapping({"/product", "/product/{id}"})
    public String pagination(Model model, @PathVariable(value = "id", required = false) Integer page,
                             @RequestParam(value = "searchName", required = false) String searchName) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = new ArrayList<>();
        System.out.println(searchName);
        if( searchName != null && !searchName.isEmpty()) {
            productList = productService.searchActive(searchName, 9, currentPage);
            int totalPageSearch = productService.totalPageSearch(searchName, 9);
            model.addAttribute("totalPageSearch", totalPageSearch);
            int totalProductSearch = productService.totalProductSearchActive(searchName);
            model.addAttribute("totalProductSearch", totalProductSearch);
        } else {
            productList = productService.pagination(9, currentPage);
            int totalPage = productService.totalPageActive(9, currentPage);
            model.addAttribute("totalPage", totalPage);
        }
        List<Category> categoryList = categoryService.findAll().stream().filter(Category::isCategoryStatus).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("productList", productList);

        // Luu so luong san pham cua userLogin len session
        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            List<CartItemDB> list = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(userLoginDTO.getUserId()));
            int totalProduct = list.stream().mapToInt(CartItemDB::getQuantity).sum();
            session.setAttribute("cartTotalProduct", totalProduct);
        }
        return "userview/shop-left-sidebar";
    }

    @RequestMapping({"/product/product-sort/asc", "/product/product-sort/asc/{i}"})
    public String paginationSort(Model model, @PathVariable(value = "i", required = false) Integer page) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = productService.paginationAndSort(9, page, "asc");
        int totalPageSortASC = productService.totalPageActiveSort(9, currentPage, "asc");
        List<Category> categoryList = categoryService.findAll().stream().filter(Category::isCategoryStatus).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("totalPageSortASC", totalPageSortASC);
        model.addAttribute("productList", productList);
        return "userview/shop-left-sidebar";
    }


    @RequestMapping({"/product/product-sort/desc", "/product/product-sort/desc/{i}"})
    public String paginationAndSortDESC(Model model, @PathVariable(value = "i", required = false) Integer page
    ) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = productService.paginationAndSortActive(9, currentPage, "desc");
        int totalPage = productService.totalPageActiveSort(9, currentPage, "desc");
        List<Category> categoryList = categoryService.findAll().stream().filter(Category::isCategoryStatus).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("totalPageSort", totalPage);
        model.addAttribute("productList", productList);
        return "userview/shop-left-sidebar";
    }

    @RequestMapping("/product-detail/{id}")
    public String showProductdetail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.findAll().stream().filter(Category::isCategoryStatus).collect(Collectors.toList());
        List<Image> images = imageService.findByProductId(id);
        model.addAttribute("images", images);
        List<Product> productList = productService.findAllActiveStatus(true);
        model.addAttribute("productList", productList);
        return "userview/shop-product-detail-left-sidebar";
    }

    @RequestMapping("/product-find-by-category/{id}")
    public String showProduct(@PathVariable("id") Integer id, Model model) {
        List<Product> productList = productService.findByCategoryId(id).stream().filter(Product::isProductStatus).collect(Collectors.toList());
        model.addAttribute("productList", productList);
        List<Category> categoryList = categoryService.findAll().stream().filter(Category::isCategoryStatus).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);
        return "userview/shop-left-sidebar";
    }


}
