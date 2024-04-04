package ra.project_md03.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    private int orderId;
    private User user;
    private double orderTotal;
    private int orderStatus = 0;
    private Date order_at;
    private String address;
    private String note;
    private String payment;


}
