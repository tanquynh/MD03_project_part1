package ra.project_md03.model.service.user;


import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.dto.user.UserRegisterDTO;
import ra.project_md03.model.entity.User;
import ra.project_md03.model.service.IGenericService;

import java.util.List;

public interface UserService extends IGenericService<User, Integer, String> {
    void setRole(Integer id);

    User findByMail(String mail);

    Integer countUserByStatus(Boolean b);

    List<User> search(String searchName);

    Boolean register(UserRegisterDTO user);

    UserLoginDTO login(String email, String password);

    Boolean checkPassword(String password,User user);

    Boolean checkConfirmedPassword(String password, String confirmedPassword);
    void changePassword(String password, Integer userId);
}