package ra.project_md03.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project_md03.model.dto.user.UserLoginDTO;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    private int cartId;
    private UserLoginDTO user;
}
