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
public class Coupons {
    private int id;
    private String title;
    private String code;
    private String discount;
    private Date startDate;
    private Date endDate;
    private int quantity;
    private boolean status;
}
