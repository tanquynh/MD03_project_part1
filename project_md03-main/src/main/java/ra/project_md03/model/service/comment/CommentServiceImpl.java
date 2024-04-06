package ra.project_md03.model.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.dao.comment.CommentDao;
import ra.project_md03.model.dao.comment.CommentDaoImpl;
import ra.project_md03.model.entity.Comment;

import java.util.List;

@Repository
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDaoImpl commentDao;

    @Override
    public void save(Comment comment) {
        commentDao.save(comment);
    }

    @Override
    public void delete(Integer productId) {
        commentDao.delete(productId);
    }

    @Override
    public List<Comment> findByProductId(Integer productId, Integer userId) {
        return commentDao.findByProductId(productId, userId);
    }
}
