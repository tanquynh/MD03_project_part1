package ra.project_md03.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private int userId;
    private String username;
    private String email;
    private String address;
    private String phone;
    private boolean role = true;
    private boolean userStatus = true;
    private String avatar;
    private String password;
}
