package ra.project_md03.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.project_md03.model.dto.brand.BrandDTO;
import ra.project_md03.model.dto.brand.BrandDTOEdit;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.service.brand.BrandServiceImpl;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BrandController {
    @Autowired
    private BrandServiceImpl brandService;

    @GetMapping({"/brand", "/brand/{i}"})
    public String pagination(Model model, @PathVariable(value = "i", required = false) Integer page, @RequestParam(name = "searchName", required = false) String searchName) {
        int currentPage = (page != null && page > 1) ? page : 1;
        List<Brand> brandList;
        int totalPage;
        int totalCategorySearch = 0;
        if (searchName != null && !searchName.isEmpty()) {
            brandList = brandService.search(searchName);
            totalPage = brandService.getTotalPageSearch("searchName");
            model.addAttribute("totalPage", totalPage);
            totalCategorySearch = brandList.size();
        } else {
            brandList = brandService.pagination(5, currentPage);
            totalPage = brandService.getTotalPagePagination(5, currentPage);
            model.addAttribute("totalPage", totalPage);

        }

        int activeBrand = brandService.countBrand(true);
        int inactiveBrand = brandService.countBrand(false);
        model.addAttribute("totalCategorySearch", totalCategorySearch);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("brandList", brandList);
        model.addAttribute("activeBrand", activeBrand);
        model.addAttribute("inactiveBrand", inactiveBrand);
        model.addAttribute("searchName", searchName);
        return "/admin/brand/brand";
    }

    @GetMapping({"/add-brand"})
    public String addCategory(Model model) {
        model.addAttribute("brand", new BrandDTO());
        return "/admin/brand/add-brand";
    }

    @PostMapping("/add-brand")
    public String addCategory(@Valid @ModelAttribute("brand") BrandDTO brand
    ) {
//        List<Category> brandList = brandService.findParent();
//        if (result.hasErrors()) {
//            model.addAttribute("brandList", brandList);
//            return "redirect:/admin/add-new-category";
//        }
//        if (brandService.checkCategoryNameExist(categoryDTO.getCategoryName())) {
//            model.addAttribute("brandList", brandList);
//            redirectAttributes.addFlashAttribute("errName", "Category Name is existed!");
//            return "redirect:/admin/add-new-category";
//        } else if (brandService.checkParentId(categoryDTO.getParentId())) {
//            model.addAttribute("brandList", brandList);
//            redirectAttributes.addFlashAttribute("errParentId", "Please fill parentID!");
//            return "redirect:/admin/add-new-category";
//
//        }
        brandService.saveOrUpdate(brand);
        return "redirect:/admin/brand";
    }

    @RequestMapping("/brand-edit/{brandId}")
    public String editCategory(@PathVariable("brandId") Integer id, Model model) {
        BrandDTOEdit brandDTOEdit = new BrandDTOEdit();
        brandDTOEdit.setId(id);
        brandDTOEdit.setBrandName(brandService.findById(id).getBrandName());
        model.addAttribute("brand", brandDTOEdit);
        return "/admin/brand/brand-edit";
    }

    @RequestMapping(value = "/brand-edit", method = RequestMethod.POST)
    public String editCategory(@Valid @ModelAttribute("brand") BrandDTOEdit brand) {
        brandService.update(brand);
        return "redirect:/admin/brand";
    }

    @RequestMapping("/brand-block/{brandId}")
    public String changeStatus(@PathVariable("brandId") Integer id) {
        brandService.changeStatus(id);
        return "redirect:/admin/brand";
    }
}
