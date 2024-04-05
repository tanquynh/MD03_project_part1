package ra.project_md03.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.dto.category.CategoryDTO;
import ra.project_md03.model.dto.category.CategoryEditDTO;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.service.UploadService;
import ra.project_md03.model.service.category.CategoryServiceImpl;
import ra.project_md03.model.service.image.ImageService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ImageService imageService;

    @RequestMapping({"/category", "/category/{i}"})
    public String pagination(Model model, @PathVariable(value = "i", required = false) Integer page, @RequestParam(name = "searchName", required = false) String searchName) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Category> categoryList;
        int totalPage;
        int totalCategorySearch = 0;
        if (searchName != null && !searchName.isEmpty()) {
            categoryList = categoryService.search(searchName);
            totalPage = categoryService.getTotalPageSearch("search-name");
            model.addAttribute("totalPage", totalPage);
            totalCategorySearch = categoryList.size();
        } else {
            categoryList = categoryService.pagination(5, currentPage);
            totalPage = categoryService.getTotalPagePagination(5, currentPage);
            model.addAttribute("totalPage", totalPage);
        }
        int activeCategory = categoryService.countCategory(true);
        int inactiveCategory = categoryService.countCategory(false);
        List<Category> parentCategories = categoryService.findParent();
        model.addAttribute("totalCategorySearch", totalCategorySearch);
        model.addAttribute("parentCategories", parentCategories);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("inactiveCategory", inactiveCategory);
        model.addAttribute("nameSearch", searchName);
        return "/admin/category/category-list";
    }

    @RequestMapping({"/add-new-category"})
    public String addCategory(Model model) {
        model.addAttribute("category", new CategoryDTO());
        model.addAttribute("categoryList", categoryService.findParent());
        return "/admin/category/add-new-category";
    }

    @RequestMapping(value = "/add-new-category", method = RequestMethod.POST)
    public String addCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO, BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.findParent());
            return "/admin/category/add-new-category";
        }
        categoryService.saveOrUpdate(categoryDTO);
        return "redirect:/admin/category";
    }

    @RequestMapping("/category-edit/{categoryId}")
    public String editCategory(@PathVariable("categoryId") Integer id, Model model) {
        Category category = categoryService.findById(id);
        CategoryEditDTO categoryEditDTO = new CategoryEditDTO();
        categoryEditDTO.setCategoryId(id);
        categoryEditDTO.setCategoryName(category.getCategoryName());
        categoryEditDTO.setParentId(category.getParentId());
        model.addAttribute("image", category.getImage());
        model.addAttribute("category", categoryEditDTO);
        model.addAttribute("categoryList", categoryService.findParent());
        return "/admin/category/category-edit";
    }

    @RequestMapping(value = "/category-edit", method = RequestMethod.POST)
    public String editCategory(@Valid @ModelAttribute("category") CategoryEditDTO category, BindingResult bindingResult, Model model
    ) {
        Category updateCategory = categoryService.findById(category.getCategoryId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("image", updateCategory.getImage());
            model.addAttribute("categoryList", categoryService.findParent());
            return "admin/category/category-edit";
        }

        categoryService.Update(category);
        return "redirect:/admin/category";
    }

    @RequestMapping("/category-block/{categoryId}")
    public String changeStatus(@PathVariable("categoryId") Integer id) {
        categoryService.changeStatus(id);
        return "redirect:/admin/category";
    }

}
