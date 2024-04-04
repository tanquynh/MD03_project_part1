package ra.project_md03.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.dto.category.CategoryDTO;
import ra.project_md03.model.dto.coupons.CouponsDTO;
import ra.project_md03.model.dto.coupons.CouponsEditDTO;
import ra.project_md03.model.entity.Coupons;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.coupons.CouponsServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CouponsController {
    @Autowired
    private CouponsServiceImpl couponsService;

    @RequestMapping({"/coupons", "/coupons/{i}"})
    public String pagination(Model model, @PathVariable(value = "i", required = false) Integer page, @RequestParam(name = "searchName", required = false) String searchName) {
        int currentPage = (page != null && page > 1) ? page : 1;
        List<Coupons> couponsList;
        int totalPage;
        int totalCouponsSearch = 0;
        if (searchName != null && !searchName.isEmpty()) {
            couponsList = couponsService.search(searchName);
            totalPage = couponsService.getTotalPageSearch("searchName");
            model.addAttribute("totalPage", totalPage);
            totalCouponsSearch = couponsList.size();
        } else {
            couponsList = couponsService.pagination(3, currentPage);
            totalPage = couponsService.getTotalPagePagination(3, currentPage);
            model.addAttribute("totalPage", totalPage);

        }
        int activeCoupons = couponsService.countCoupons(true);
        int inactiveCoupons = couponsService.countCoupons(false);
        model.addAttribute("totalCouponsSearch", totalCouponsSearch);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("couponsList", couponsList);
        model.addAttribute("activeCoupons", activeCoupons);
        model.addAttribute("inactiveCoupons", inactiveCoupons);
        model.addAttribute("searchName", searchName);
        return "/admin/coupons/coupons";
    }

    @GetMapping({"/add-coupons"})
    public String addCoupons(Model model) {
        model.addAttribute("coupons", new CouponsDTO());
        return "/admin/coupons/add-coupons";
    }

    @RequestMapping(value = "/add-coupons", method = RequestMethod.POST)
    public String addCoupons(@Valid @ModelAttribute("coupons") CouponsDTO couponsDTO
    ) {

        couponsService.saveOrUpdate(couponsDTO);
        return "redirect:/admin/coupons";
    }

    @RequestMapping(value = "/coupons-edit/{id}")
    public String editCoupons(@PathVariable() Integer id, Model model) {
        CouponsEditDTO couponsDTO = new CouponsEditDTO();
        couponsDTO.setId(id);
        Coupons coupons = couponsService.findById(id);
        couponsDTO.setTitle(coupons.getTitle());
        couponsDTO.setCode(coupons.getCode());
        couponsDTO.setDiscount(coupons.getDiscount());
        couponsDTO.setQuantity(coupons.getQuantity());
        couponsDTO.setEndDate(coupons.getEndDate());
        couponsDTO.setStartDate(coupons.getStartDate());
        model.addAttribute("coupons", couponsDTO);
        return "/admin/coupons/edit-coupons";
    }

    @RequestMapping(value = "/coupons-edit", method = RequestMethod.POST)
    public String editCoupons(@ModelAttribute("coupons") CouponsEditDTO couponsDTO) {
        couponsService.update(couponsDTO);
        return "redirect:/admin/coupons";
    }

    @RequestMapping("/coupons-block/{id}")
    public String changeStatus(@PathVariable("id") Integer id) {
        couponsService.changeStatus(id);
        return "redirect:/admin/coupons";
    }

    @RequestMapping("/coupons-show/{id}")
    public String show(Model model, @PathVariable("id") Integer id) {
        Coupons coupons = couponsService.findById(id);
        model.addAttribute("coupons", coupons);
        return "admin/coupons/coupons-show";
    }
}
