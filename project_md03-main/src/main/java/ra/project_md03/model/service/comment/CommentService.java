package ra.project_md03.model.service.comment;

import ra.project_md03.model.entity.Comment;
import ra.project_md03.model.entity.Wishlist;

import java.util.List;

public interface CommentService {

    void save(Comment comment);
    void delete(Integer productId);
    List<Comment>  findByProductId(Integer productId,Integer userId);
}
