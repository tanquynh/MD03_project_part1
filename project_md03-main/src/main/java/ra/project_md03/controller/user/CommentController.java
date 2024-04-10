package ra.project_md03.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.entity.Comment;
import ra.project_md03.model.service.comment.CommentService;
import ra.project_md03.model.service.product.ProductServiceImpl;
import ra.project_md03.model.service.user.UserServiceImpl;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private HttpSession session;

    @RequestMapping("/comment-delete/{id}")
    public String deleteComment(@PathVariable("id") Integer productId) {
        commentService.delete(productId);
        return "redirect:/admin/comment";

    }
//@RequestMapping(value ="/add-comment-ajax" )
//public String addComment(Model model) {
//        Comment comment = new Comment();
//        commentService.findByProductId()
//}
    @RequestMapping(value = "/add-comment-ajax", method = RequestMethod.POST)
    public String addComment(@RequestParam("productId") Integer productId, @RequestParam("comment") String ctm, @RequestParam("rating") Integer rating) {
        ;
        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
        System.out.println(userLoginDTO.toString());
        Comment comment = new Comment();
        comment.setComment(ctm);
        comment.setRating(rating);
        comment.setUserId(userLoginDTO.getUserId());
        comment.setProduct(productService.findById(productId));
        commentService.save(comment);

        return "redirect:/product-detail/" + productId;
    }
}
