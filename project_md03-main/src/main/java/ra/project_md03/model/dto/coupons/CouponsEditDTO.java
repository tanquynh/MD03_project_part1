package ra.project_md03.model.dto.coupons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CouponsEditDTO {
    private int id;
    private String title;
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endDate;
    private int quantity;
    private String discount;
}
