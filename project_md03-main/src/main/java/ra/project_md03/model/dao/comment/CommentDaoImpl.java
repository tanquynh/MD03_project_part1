package ra.project_md03.model.dao.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.entity.Comment;
import ra.project_md03.model.service.product.ProductServiceImpl;
import ra.project_md03.model.service.user.UserServiceImpl;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public void save(Comment comment) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_COMMENT_ADD(?,?,?,?,?)}");
            statement.setString(1, comment.getComment());
            statement.setInt(2, comment.getRating());
            statement.setInt(3, comment.getProduct().getProductId());
            long currentTimeMillis = System.currentTimeMillis();

// Tạo một đối tượng java.sql.Date từ thời gian hiện tại
            java.sql.Date currentDate = new java.sql.Date(currentTimeMillis);

// Sử dụng đối tượng java.sql.Date trong prepared statement
            statement.setDate(4, currentDate);
            statement.setInt(5, comment.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer productId) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_COMMENT_DELETE(?)}");
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Comment> findByProductId(Integer productId, Integer userId) {
        Connection connection = ConnectionDatabase.openConnection();

        List<Comment> commentList = new ArrayList<>();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_COMMENT_FIND_BY_PRODUCTID(?) }");
            statement.setInt(1, productId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("id"));
                comment.setComment(rs.getString("comment"));
                comment.setTime(rs.getDate("time"));
                comment.setUserId(rs.getInt("userId"));
                comment.setProduct(productService.findById(rs.getInt("productId")));
                commentList.add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return commentList;
    }
}
