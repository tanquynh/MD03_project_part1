package ra.project_md03.model.service.user;


import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.user.UserDao;
import ra.project_md03.model.dto.user.UserLoginDTO;
import ra.project_md03.model.dto.user.UserRegisterDTO;
import ra.project_md03.model.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean saveOrUpdate(User user) {
        return userDao.saveOrUpdate(user);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> pagination(Integer limit, Integer currentPage) {
        return userDao.pagination(limit, currentPage);
    }

    @Override
    public void changeStatus(Integer id) {
        userDao.changeStatus(id);
    }

    @Override
    public void setRole(Integer id) {
        userDao.setRole(id);
    }

    @Override
    public User findByMail(String mail) {
        return userDao.findByMail(mail);
    }

    @Override
    public Integer countUserByStatus(Boolean b) {
        return userDao.countUserByStatus(b);
    }

    @Override
    public List<User> search(String searchName) {
        return userDao.search(searchName);
    }

    @Override
    public Boolean register(UserRegisterDTO user) {
//        User user1=new User();
//        user1.setUsername(user.getUsername());
//        user1.setEmail(user.getEmail());
//        user1.setAddress(user.getAddress());

        User user1 = new ModelMapper().map(user, User.class);
        //ma hoa password
        String hassPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user1.setPassword(hassPassword);

        return userDao.saveOrUpdate(user1);
    }


    @Override
    public UserLoginDTO login(String email, String password) {
        User user = userDao.findByMail(email);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword()) &&user.isUserStatus()) {
                UserLoginDTO userLogin=new UserLoginDTO();
                userLogin.setUserId(user.getUserId());
                userLogin.setUsername(user.getUsername());
                userLogin.setEmail(user.getEmail());
                userLogin.setUserStatus(user.isUserStatus());
                userLogin.setRole(user.isRole());
                userLogin.setPassword(user.getPassword());
                return userLogin;
            }
        }
        return null;
    }

    @Override
    public Boolean checkPassword( String password,User user) {
//        if(BCrypt.checkpw(user.getPassword(),password)){
//            return true;
//        }
//        return false;
        return BCrypt.checkpw(password,user.getPassword());
    }

    @Override
    public Boolean checkConfirmedPassword(String password,String confirmedPassword) {
        return password.equals(confirmedPassword);
    }

    @Override
    public void changePassword(String password, Integer userId) {
        //ma hoa password
        String hassPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        userDao.changePassword(hassPassword, userId);
    }


}
