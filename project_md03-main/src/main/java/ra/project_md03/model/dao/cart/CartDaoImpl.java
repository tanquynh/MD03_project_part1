package ra.project_md03.model.dao.cart;

import org.springframework.stereotype.Repository;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;

@Repository
public class CartDaoImpl implements CartDao {

    @Override
    public Integer checkCartUser(Integer userId) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_GET_USER_CART_ID(?,?)}");
            statement.setInt(1, userId);
            statement.registerOutParameter(2, java.sql.Types.INTEGER);
            statement.execute();
          int id =  statement.getInt(2);
            if(id > 0) {
                return id;
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Integer save(Integer userId) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CART_INSERT(?,?)}");
            statement.setInt(1, userId);
            statement.registerOutParameter(2, Types.INTEGER);
            statement.executeUpdate();
            return statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
