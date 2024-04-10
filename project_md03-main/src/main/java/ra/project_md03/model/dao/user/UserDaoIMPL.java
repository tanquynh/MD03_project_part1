package ra.project_md03.model.dao.user;

import org.springframework.stereotype.Repository;
import ra.project_md03.model.dto.user.UserRegisterDTO;
import ra.project_md03.model.entity.User;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;

@Repository
public class UserDaoIMPL implements UserDao {
    public static int totalPageSearch;
    public static int totalPagePagination;

    @Override
    public List<User> findAll() {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_FIND_ALL()}");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getBoolean("role"));
                user.setUserStatus(rs.getBoolean("status"));
                user.setAvatar(rs.getString("avatar"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return users;
    }

    @Override
    public boolean saveOrUpdate(User user) {
        Connection connection = null;
        int check;

        try {
            connection = ConnectionDatabase.openConnection();
            if (user.getUserId() == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_USER_ADD(?,?,?,?,?)}");
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getAddress());
                statement.setString(4, user.getPhone());
                statement.setString(5, user.getPassword());
                check = statement.executeUpdate();
            } else {
                CallableStatement statement = connection.prepareCall("{CALL PROC_USER_EDIT(?,?,?,?,?,?)}");
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getAddress());
                statement.setString(4, user.getPhone());
                statement.setString(5, user.getAvatar());
                statement.setInt(6, user.getUserId());
                check = statement.executeUpdate();

            }
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public User findById(Integer id) {
        User user = new User();
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_FIND_BY_ID(?) }");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getBoolean("role"));
                user.setAvatar(rs.getString("avatar"));
                user.setPassword(rs.getString("password"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> pagination(Integer a, Integer b) {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_PAGINATION(?,?,?)}");
            statement.setInt(1, a);
            statement.setInt(2, b);
            statement.registerOutParameter(3, INTEGER);
            ResultSet rs = statement.executeQuery();
            totalPagePagination = statement.getInt(3);
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getBoolean("role"));
                user.setUsername(rs.getString("status"));
                user.setAvatar(rs.getString("avatar"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return users;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_CHANGE_STATUS(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

    }

    @Override
    public void setRole(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_SET_ROLE(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public User findByMail(String mail) {
        Connection connection = null;
        User user = null;

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_FIND_BY_MAIL(?)}");
            statement.setString(1, mail);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getBoolean("role"));
                user.setUserStatus(rs.getBoolean("status"));
                user.setAvatar(rs.getString("avatar"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public Integer countUserByStatus(boolean b) {
        Connection connection = null;
        int countUser = 0;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_COUNT_BY_STATUS(?,?)}");
            statement.setBoolean(1, b);
            statement.registerOutParameter(2, Types.INTEGER);
            statement.executeUpdate();
            countUser = statement.getInt(2);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }


        return countUser;
    }

    @Override
    public List<User> search(String searchName) {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_FIND(?,?,?)}");
            statement.setString(1, searchName);
            statement.registerOutParameter(2, INTEGER);
            statement.registerOutParameter(3, INTEGER);
            ResultSet rs = statement.executeQuery();
            totalPageSearch = statement.getInt(3);
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getBoolean("role"));
                user.setUserStatus(rs.getBoolean("status"));
                user.setAvatar(rs.getString("avatar"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return users;
    }

    @Override
    public boolean register(UserRegisterDTO user) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_ADD(?,?,?,?,?)}");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getPassword());
            check = statement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public void changePassword(String password, Integer userId) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_CHANGE_PASSWORD(?,?)}");
            statement.setString(1, password);
            statement.setInt(2, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
