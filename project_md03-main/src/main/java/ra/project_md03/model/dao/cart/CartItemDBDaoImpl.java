package ra.project_md03.model.dao.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.dao.product.ProductDao;
import ra.project_md03.model.entity.CartItemDB;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartItemDBDaoImpl implements CartItemDBDao {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<CartItemDB> findItemByUserId(Integer userId) {
        Connection connection = null;
        List<CartItemDB> itemList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_GET_USER_CART_ITEM(?)}");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartItemDB item = new CartItemDB();
                item.setCartId(resultSet.getInt("cart_id"));
                item.setProduct(productDao.findById(resultSet.getInt("product_id")));
                item.setQuantity(resultSet.getInt("quantity"));
                itemList.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return itemList;
    }

    @Override
    public void save(CartItemDB item) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL CheckProductExistence(?,?,?)}");
            callableStatement.setInt(1, item.getProduct().getProductId());
            callableStatement.setInt(2, item.getCartId());
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();
            int check = callableStatement.getInt(3);

            if (check == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_ITEM_ADD(?,?,?)}");
                statement.setInt(1, item.getCartId());
                statement.setInt(2, item.getProduct().getProductId());
                statement.setInt(3, item.getQuantity());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer productId) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_DELETE_ITEM_BY_PRODUCT_ID(?)}");
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

    }

    @Override
    public void increase(Integer productId) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ITEM_INCREASE_QUANTITY(?)}");
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void decrease(Integer productId) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ITEM_DECREASE_QUANTITY(?)}");
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public CartItemDB findByProductId(Integer productId, Integer cartId) {
        Connection connection = null;
        CartItemDB item = new CartItemDB();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_FIND_ITEM_BY_PRODUCT_ID(?,?)}");
            statement.setInt(1, productId);
            statement.setInt(2, cartId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                item.setCartId(resultSet.getInt("cart_id"));
                item.setProduct(productDao.findById(resultSet.getInt("product_id")));
                item.setQuantity(resultSet.getInt("quantity"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return item;
    }

    @Override
    public List<CartItemDB> findItemByCartId(Integer cartId) {
        Connection connection = null;
        List<CartItemDB> itemList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_GET_USER_CART_ITEMS(?)}");
            statement.setInt(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartItemDB item = new CartItemDB();
                item.setCartId(resultSet.getInt("cart_id"));
                item.setProduct(productDao.findById(resultSet.getInt("product_id")));
                item.setQuantity(resultSet.getInt("quantity"));
                itemList.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return itemList;
    }

    @Override
    public void deleteCartItem(Integer cartId) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_DELETE_USER_CART_ITEM(?)}");
            statement.setInt(1, cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
