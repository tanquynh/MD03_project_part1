package ra.project_md03.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEdit {
    private int id;
    @NotEmpty(message = "CategoryName must not be empty!")
    private String username;
    @NotEmpty(message = "Please fill username!")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Please include @ in email!")
    private String email;
    @NotEmpty(message = "Please fill phone!")
    private String phone;
    @NotEmpty(message = "Please fill address!")
    private String address;
    private String password;
    private MultipartFile img;
}
