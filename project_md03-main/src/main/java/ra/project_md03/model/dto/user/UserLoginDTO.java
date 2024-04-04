package ra.project_md03.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginDTO {
    private int userId;
    @NotEmpty(message = "Please fill username!")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Please include @ in email!")
    private String email;
    @NotEmpty(message = "Please fill username!")
    @Size(min = 4, max = 12, message = "Password's length is from 4 to 12")
    private String password;
    private String username;
    private boolean userStatus = true;
    private boolean role = true;
}

