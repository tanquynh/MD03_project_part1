package ra.project_md03.model.service.order;



import ra.project_md03.model.entity.CartItemDB;
import ra.project_md03.model.entity.Order;
import ra.project_md03.model.entity.OrderDetail;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    Order save(Order order, List<CartItemDB>list);

    void changeStatus(Integer status,Integer orderId);
    List<Order> findByUserId(Integer userId);
    Order findByOrderId(Integer orderId);
    List<OrderDetail>findOrderDetailByOrderId(Integer orderId);
    List<Order> pagination(Integer limit, Integer currentPage);
    List<Order> search(String name,int limit,int currentPage);
    Integer totalPageSearch(String name,int limit);
    Integer totalOrderSearch(String name);
    Integer countOrderByStatus(Integer status);
    Integer totalPage(Integer limit, Integer currentPage);
}
