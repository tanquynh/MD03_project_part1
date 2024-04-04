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
public class UserRegisterDTO {
    @NotEmpty(message = "Please fill username!")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Please include @ in email!")
    private String email;
        @NotEmpty(message = "Please fill address!")
    private String address;
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Phone start with 84, 03(5|7|8|9 and length form 10 to 11 characters")
    private String phone;
    @Size(min = 4, max = 12, message = "Password's length is from 4 to 12")
    private String password;

}
