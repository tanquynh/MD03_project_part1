package ra.project_md03.model.dao.comment;

import ra.project_md03.model.entity.Comment;

import java.util.List;

public interface CommentDao {
    void save(Comment comment);
    void delete(Integer productId);
    List<Comment> findByProductId(Integer productId, Integer userId);
}
