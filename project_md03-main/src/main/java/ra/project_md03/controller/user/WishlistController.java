//package ra.project_md03.controller.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import ra.project_md03.model.dto.user.UserLoginDTO;
//import ra.project_md03.model.entity.Wishlist;
//import ra.project_md03.model.service.product.ProductServiceImpl;
//import ra.project_md03.model.service.wishlist.WishlistService;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//
//@Controller
//public class WishlistController {
//    @Autowired
//    private WishlistService wishlistService;
//    @Autowired
//    private ProductServiceImpl productService;
//    @Autowired
//    private HttpSession session;
//    @RequestMapping("/wishlist")
//    public String wishlist(Model model) {
//        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
//        if(userLoginDTO != null) {
//            List<Wishlist> wishlistList = wishlistService.findByUserId(userLoginDTO.getUserId());
//            model.addAttribute("wishlist", wishlistList);
//        } else {
//            return "redirect:/login?action=wishlist";
//        }
//        return "userview/wishlist";
//    }
//    @RequestMapping("/wishlist-delete/{id}")
//       public String deleteWishlist(@PathVariable("id") Integer productId) {
//        wishlistService.delete(productId);
//        return "redirect:/wishlist";
//    }
//    @RequestMapping(value = "/add-wishlist", method = RequestMethod.POST)
//    public String addWishlist(@RequestParam("productId") Integer productId) {
//        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
//        if(userLoginDTO != null) {
//            Wishlist wishlist = new Wishlist();
//            wishlist.setUserId(userLoginDTO.getUserId());
//            wishlist.setProduct(productService.findById(productId));
//            Wishlist wList = wishlistService.findByProductId(productId,userLoginDTO.getUserId());
//            if (wList == null|| wList.getWishlistId() == 0){
//                wishlistService.save(wishlist);
//            }
//        }
//        return "redirect:/wishlist";
//    }
//}
