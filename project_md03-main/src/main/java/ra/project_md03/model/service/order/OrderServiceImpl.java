package ra.project_md03.model.service.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.order.OrderDao;
import ra.project_md03.model.entity.CartItemDB;
import ra.project_md03.model.entity.Order;
import ra.project_md03.model.entity.OrderDetail;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order save(Order order, List<CartItemDB> list) {
        return orderDao.save(order, list);
    }

    @Override
    public void changeStatus(Integer status, Integer orderId) {
        orderDao.changeStatus(status, orderId);
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        return orderDao.findByUserId(userId);
    }

    @Override
    public Order findByOrderId(Integer orderId) {
        return orderDao.findByOrderId(orderId);
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Integer orderId) {
        return orderDao.findOrderDetailByOrderId(orderId);
    }

    @Override
    public List<Order> pagination(Integer limit, Integer currentPage) {
        return orderDao.pagination(limit, currentPage);
    }

    @Override
    public List<Order> search(String name, int limit, int currentPage) {
        return orderDao.search(name, limit, currentPage);
    }

    @Override
    public Integer totalPageSearch(String name, int limit) {
        return orderDao.totalPageSearch(name, limit);
    }

    @Override
    public Integer totalOrderSearch(String name) {
        return orderDao.totalOrderSearch(name);
    }

    @Override
    public Integer countOrderByStatus(Integer status) {
        return orderDao.countOrderByStatus(status);
    }

    @Override
    public Integer totalPage(Integer limit, Integer currentPage) {
        return orderDao.totalPage(limit, currentPage);
    }
}
