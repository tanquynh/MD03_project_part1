package ra.project_md03.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Brand {
    private int brandId;
    private String brandName;
    private boolean brandStatus;
}
