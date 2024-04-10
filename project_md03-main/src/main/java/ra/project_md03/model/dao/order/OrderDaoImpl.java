package ra.project_md03.model.dao.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.dao.product.ProductDao;
import ra.project_md03.model.dao.user.UserDao;
import ra.project_md03.model.entity.CartItemDB;
import ra.project_md03.model.entity.Order;
import ra.project_md03.model.entity.OrderDetail;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Order> findAll() {
        Connection connection = null;
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_FIND_ALL()}");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setOrderStatus(resultSet.getInt("status"));
                order.setOrderTotal(resultSet.getBigDecimal("total"));
                order.setUser(userDao.findById(resultSet.getInt("user_id")));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
                order.setPayment(resultSet.getString("payment"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public Order save(Order order, List<CartItemDB> list) {
        Connection connection = null;
        int orderId;
        try {
            connection = ConnectionDatabase.openConnection();
            connection.setAutoCommit(false);
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_ADD(?,?,?,?,?,?)}");
            statement.setInt(1, order.getUser().getUserId());
            statement.setBigDecimal(2, order.getOrderTotal());
            statement.setString(3, order.getAddress());
            statement.setString(4, order.getNote());
            statement.setString(5, order.getPayment());
            statement.registerOutParameter(6, Types.INTEGER);
            int checkStatement = statement.executeUpdate();
            orderId = statement.getInt(6);

            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_ORDER_DETAIL_ADD(?,?,?,?)}");
            for (CartItemDB cartItemDB : list) {
                callableStatement.setInt(1, orderId);
                callableStatement.setInt(2, cartItemDB.getProduct().getProductId());
                callableStatement.setInt(3, cartItemDB.getQuantity());
                callableStatement.setBigDecimal(4, cartItemDB.getProduct().getPrice());
                callableStatement.addBatch(); // Thêm dòng này để thêm callableStatement vào batch
            }
            int[] batchResult = callableStatement.executeBatch(); // Thực thi batch

            if (checkStatement > 0 && batchResult.length > 0) {
                connection.commit();
                return order;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return order;
    }

    @Override
    public void changeStatus(Integer status, Integer orderId) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_CHANGE_STATUS(?,?)}");
            statement.setInt(1, status);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

    }

    @Override
    public Order findByOrderId(Integer orderId) {
        Connection connection = ConnectionDatabase.openConnection();
        Order order = new Order();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_FIND_BY_ID(?)}");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order.setOrderId(resultSet.getInt("id"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setOrderStatus(resultSet.getInt("status"));
                order.setOrderTotal(resultSet.getBigDecimal("total"));
                order.setUser(userDao.findById(resultSet.getInt("user_id")));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
                order.setPayment(resultSet.getString("payment"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return order;
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        Connection connection = null;
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_FIND_BY_USER_ID(?)}");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setOrderStatus(resultSet.getInt("status"));
                order.setOrderTotal(resultSet.getBigDecimal("total"));
                order.setUser(userDao.findById(resultSet.getInt("user_id")));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
                order.setPayment(resultSet.getString("payment"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Integer orderId) {
        Connection connection = ConnectionDatabase.openConnection();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDER_DETAIL_BY_ORDER_ID(?)}");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(findByOrderId(resultSet.getInt("order_id")));
                orderDetail.setProduct(productDao.findById(resultSet.getInt("product_id")));
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetail.setOrderPrice(resultSet.getBigDecimal("price"));
                orderDetailList.add(orderDetail);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetailList;
    }

    @Override
    public List<Order> pagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDER_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setOrderStatus(resultSet.getInt("status"));
                order.setOrderTotal(resultSet.getBigDecimal("total"));
                order.setUser(userDao.findById(resultSet.getInt("user_id")));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
                order.setPayment(resultSet.getString("payment"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public Integer totalPage(Integer limit, Integer currentPage) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDER_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            return statement.getInt(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Order> search(String name, int limit, int currentPage) {
        Connection connection = null;
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_SEARCH_PAGED(?,?,?)}");
            statement.setString(1, name);
            statement.setInt(2, currentPage);
            statement.setInt(3, currentPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setOrderStatus(resultSet.getInt("status"));
                order.setOrderTotal(resultSet.getBigDecimal("total"));
                order.setUser(userDao.findById(resultSet.getInt("user_id")));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
                order.setPayment(resultSet.getString("payment"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public Integer totalPageSearch(String name, int limit) {
        Connection connection = null;
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_SEARCH(?)}");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setOrderStatus(resultSet.getInt("status"));
                order.setOrderTotal(resultSet.getBigDecimal("total"));
                order.setUser(userDao.findById(resultSet.getInt("user_id")));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
                order.setPayment(resultSet.getString("payment"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return (int) (double) (orders.size() / limit);
    }

    @Override
    public Integer totalOrderSearch(String name) {
        Connection connection = null;
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_SEARCH(?)}");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setOrderStatus(resultSet.getInt("status"));
                order.setOrderTotal(resultSet.getBigDecimal("total"));
                order.setUser(userDao.findById(resultSet.getInt("user_id")));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
                order.setPayment(resultSet.getString("payment"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orders.size();
    }

    @Override
    public Integer countOrderByStatus(Integer status) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_ORDERS_COUNT_BY_STATUS(?,?)}");
            statement.setInt(1, status);
            statement.registerOutParameter(2, Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            return statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
