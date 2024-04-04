package ra.project_md03.model.dao.user;

import ra.project_md03.model.dao.IGenericDao;
import ra.project_md03.model.dto.user.UserRegisterDTO;
import ra.project_md03.model.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    boolean saveOrUpdate(User user);

    User findById(Integer id);

    List<User> pagination(Integer a, Integer b);
    void changeStatus(Integer id);
    void setRole(Integer id);
    User findByMail(String mail);
    Integer countUserByStatus(boolean b);
    List<User> search(String searchName);
    boolean register(UserRegisterDTO user);
    void changePassword(String password, Integer userId);
}
