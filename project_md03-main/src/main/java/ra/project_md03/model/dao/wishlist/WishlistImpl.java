package ra.project_md03.model.dao.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.dao.product.ProductDao;
import ra.project_md03.model.entity.Wishlist;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistImpl implements WishlistDao {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Wishlist> findByUserId(Integer id) {
        Connection connection = null;
        List<Wishlist> wishlists = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_WISHLIST_FIND_BY_USER_ID(?)}");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Wishlist wishlist = new Wishlist();
                wishlist.setWishlistId(rs.getInt("id"));
                wishlist.setUserId(rs.getInt("user_id"));
                wishlist.setProduct(productDao.findById(rs.getInt("product_id")));
                wishlists.add(wishlist);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return wishlists;
    }

    @Override
    public void save(Wishlist wishlist) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_WISHLIST_ADD(?,?)}");
            statement.setInt(1, wishlist.getUserId());
            statement.setInt(2, wishlist.getProduct().getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer productId) {
        Connection connection =ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_WISHLIST_DELETE(?)}");
            statement.setInt(1,productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public Wishlist findByProductID(Integer productId, Integer userId) {
        Connection connection = ConnectionDatabase.openConnection();
        Wishlist wishlist = new Wishlist();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_WISHLIST_FIND_BY_PRODUCT_ID(?,?) }");
            statement.setInt(1, productId);
            statement.setInt(2,userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                wishlist.setWishlistId(rs.getInt("id"));
                wishlist.setUserId(rs.getInt("user_id"));
                wishlist.setProduct(productDao.findById(rs.getInt("product_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return wishlist;
    }
}
