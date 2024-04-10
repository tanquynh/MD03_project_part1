package ra.project_md03.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.project_md03.model.entity.Order;
import ra.project_md03.model.entity.OrderDetail;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.order.OrderService;
import ra.project_md03.model.service.product.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @GetMapping({"", "/{i}"})
    public String pagination(Model model, @PathVariable(value = "i", required = false) Integer page,
                             @RequestParam(value = "search-name", required = false) String searchName) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Order> orderList = new ArrayList<>();

        if (searchName != null && !searchName.isEmpty()) {
            orderList = orderService.search(searchName, 5, currentPage);
            int totalPageSearch = orderService.totalPageSearch(searchName, 5);
            model.addAttribute("totalPageSearch", totalPageSearch);
            int totalOrderSearch = orderService.totalOrderSearch(searchName);
            model.addAttribute("totalOrderSearch", totalOrderSearch);

        } else {
            orderList = orderService.pagination(5, currentPage);
            int totalPage = orderService.totalPage(5, currentPage);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("orderList", orderList);

        }
        int pendingOrder = orderService.countOrderByStatus(0);
        int confirmedOrder = orderService.countOrderByStatus(1);
        int cancelOrder = orderService.countOrderByStatus(2);


        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pendingOrder", pendingOrder);
        model.addAttribute("confirmedOrder", confirmedOrder);
        model.addAttribute("cancelOrder", cancelOrder);
        model.addAttribute("nameSearch", searchName);

        return "admin/order/order-list";
    }

    @GetMapping("/order-show/{id}")
    public String showOrder(@PathVariable("id") Integer orderId, Model model) {
        Order order = orderService.findByOrderId(orderId);
        model.addAttribute("order", order);

        List<OrderDetail> orderDetailList = orderService.findOrderDetailByOrderId(orderId);
        model.addAttribute("orderDetailList", orderDetailList);

//        int cartTotal = (int) orderDetailList.stream().mapToDecima(orderDetail -> orderDetail.getOrderPrice() * orderDetail.getQuantity()).sum();
        model.addAttribute("cartTotal", order.getOrderTotal());
        return "admin/order/order-detail";
    }

    @GetMapping("/order-confirm/{id}")
    public String confirm(@PathVariable("id") Integer orderId, RedirectAttributes redirectAttributes) {
        orderService.changeStatus(1, orderId);
        redirectAttributes.addFlashAttribute("message", "Confirmed order successfully!");
        return "redirect:/admin/order";
    }

    @GetMapping("/order-cancel/{id}")
    public String cancel(@PathVariable("id") Integer orderId, RedirectAttributes redirectAttributes) {
        orderService.changeStatus(2, orderId);
        List<OrderDetail> orderDetails = orderService.findOrderDetailByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            for (Product product : productService.findAll()) {
                if (Objects.equals(orderDetail.getProduct().getProductId(), product.getProductId())) {
                    product.setStock(product.getStock() + orderDetail.getQuantity());
                    productService.save(product);
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "Cancelled order successfully!");
        return "redirect:/admin/order";
    }
}
