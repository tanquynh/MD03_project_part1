package ra.project_md03.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.dao.product.ProductDao;
import ra.project_md03.model.dao.product.ProductDapImpl;
import ra.project_md03.model.dto.product.ProductDTO;
import ra.project_md03.model.dto.product.ProductEditDTO;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.entity.Image;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.UploadService;
import ra.project_md03.model.service.brand.BrandServiceImpl;
import ra.project_md03.model.service.category.CategoryServiceImpl;
import ra.project_md03.model.service.image.ImageService;
import ra.project_md03.model.service.product.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    BrandServiceImpl brandService;
    @Autowired
    UploadService uploadService;
    @Autowired
    ImageService imageService;

    @RequestMapping({"/product", "/product/{id}"})
    public String product(Model model, @PathVariable(value = "id", required = false) Integer page, @RequestParam(name = "searchName", required = false) String searchName) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = new ArrayList<>();
        if (searchName != null && !searchName.isEmpty()) {
            productList = productService.search(searchName, 5, currentPage);
            int totalPage = productService.totalPageSearch(searchName, 5);
            model.addAttribute("totalPage", totalPage);
            int totalProductSearch = productService.totalProductSearch(searchName);
            model.addAttribute("totalProductSearch", totalProductSearch);
        } else {
            productList = productService.pagination(5, currentPage);
            int totalPage = ProductDapImpl.totalPage;
            model.addAttribute("totalPage", totalPage);
        }
        int activeProduct = productService.countProductByStatus(true);
        int inactiveProduct = productService.countProductByStatus(false);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("activeProduct", activeProduct);
        model.addAttribute("inactiveProduct", inactiveProduct);
        model.addAttribute("nameSearch", searchName);
        model.addAttribute("productList", productList);
        return "admin/product/products";
    }

    @RequestMapping({"/product-add"})
    public String addProduct(Model model) {
        List<Category> categoryList = categoryService.findAll().stream().filter(Category::isCategoryStatus).collect(Collectors.toList());
        List<Brand> brandList = brandService.findAll().stream().filter(Brand::isBrandStatus).collect(Collectors.toList());
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("brandList", brandList);
        model.addAttribute("categoryList", categoryList);
        return "admin/product/add-product";
    }

    @RequestMapping(value = {"/product-add"}, method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") ProductDTO productDTO) {
        productService.save(productDTO);
        return "redirect:/admin/product";
    }

    @RequestMapping(value = {"/product-edit/{productId}"})
    public String editProduct(@PathVariable("productId") Integer id, Model model) {
        ProductEditDTO productEditDTO = new ProductEditDTO();
        Product product = productService.findById(id);
        productEditDTO.setProductName(product.getProductName());
        productEditDTO.setProductId(product.getProductId());
        productEditDTO.setProductId(product.getProductId());
        productEditDTO.setDescription(product.getDescription());
        productEditDTO.setPrice(product.getPrice());
        productEditDTO.setStock(product.getStock());
        productEditDTO.setProductName(product.getProductName());
        model.addAttribute("product", productEditDTO);
        model.addAttribute("imageParent", product.getImage());
        model.addAttribute("imageList", imageService.findByProductId(id));
        model.addAttribute("product", productEditDTO);
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("brandList", brandService.findAll());
        return "/admin/product/product-edit";
    }

    @RequestMapping(value = {"/product-edit"}, method = RequestMethod.POST)
    public String editProduct(@ModelAttribute("product") ProductEditDTO product
    ) {
        productService.update(product);
        return "redirect:/admin/product";
    }

    @RequestMapping("/product-change/{productId}")
    public String changeStatus(@PathVariable("productId") Integer id) {
        productService.changeStatus(id);
        return "redirect:/admin/product";
    }

    @RequestMapping("/product-show/{id}")
    public String show(Model model, @PathVariable("id") Integer id) {
        Product product = productService.findById(id);
        List<Image> imageList = imageService.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("imageList", imageList);
        return "admin/product/product-show";
    }
}
