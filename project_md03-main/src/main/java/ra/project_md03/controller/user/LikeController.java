package ra.project_md03.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.service.like.LikeServiceImpl;

import javax.servlet.http.HttpSession;

@Controller
public class LikeController {
    @Autowired
    private HttpSession session;
    @Autowired
    private LikeServiceImpl likeService;
    @RequestMapping(value = "/add-likeUser")
    public String addLike(@RequestParam("productId") Integer productId) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) session.getAttribute("userLoginUser");
        likeService.save(userLoginDTO.getUserId(), productId);
        return "redirect:/";
    }
}

