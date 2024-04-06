package ra.project_md03.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.entity.Compare;
import ra.project_md03.model.entity.Wishlist;
import ra.project_md03.model.service.compare.CompareServiceImpl;
import ra.project_md03.model.service.product.ProductServiceImpl;
import ra.project_md03.model.service.wishlist.WishlistService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CompareController {
    @Autowired
    private CompareServiceImpl compareService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private HttpSession session;

    @RequestMapping("/compare")
    public String wishlist(Model model) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            List<Compare> compareList = compareService.findByUserId(userLoginDTO.getUserId());
            model.addAttribute("compareList", compareList);
        } else {
            return "redirect:/login?action=compare";
        }
        return "userview/compare";
    }

    @RequestMapping("/compare-delete/{id}")
    public String deleteWishlist(@PathVariable("id") Integer productId) {
        compareService.delete(productId);
        return "redirect:/compare";
    }

    @RequestMapping(value = "/add-compare")
    public String addWishlist(@RequestParam("productId") Integer productId) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            Compare compare = new Compare();
            compare.setUserId(userLoginDTO.getUserId());
            compare.setProduct(productService.findById(productId));
            Compare compare1 = compareService.findByProductId(productId, userLoginDTO.getUserId());
            if (compare1 == null || compare1.getCompareId() == 0) {
                compareService.save(compare);
            }
        }
        return "redirect:/compare";
    }
}
