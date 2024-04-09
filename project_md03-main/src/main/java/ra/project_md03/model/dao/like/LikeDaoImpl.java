package ra.project_md03.model.dao.like;

import org.springframework.stereotype.Component;
import ra.project_md03.model.entity.Like;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LikeDaoImpl implements LikeDao {
    public Map<Integer, Integer> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<Integer, Integer> likeCountMap = new HashMap<>(); // Map để lưu số lượng like theo productId

        try {
            connection = ConnectionDatabase.openConnection();
            String sql = "SELECT productId, COUNT(*) AS likeCount FROM LikeTable GROUP BY productId";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                int likeCount = resultSet.getInt("likeCount");
                likeCountMap.put(productId, likeCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return likeCountMap;
    }
    @Override
    public void save(Integer userId, Integer productId) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int likeId = 0;
        try {
            CallableStatement callableStatement = connection.prepareCall("{Call FindLikeByUserAndProduct(?,?,?)}");
            callableStatement.setInt(1, userId);
            callableStatement.setInt(2, productId);
            callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
            callableStatement.execute();
            likeId = callableStatement.getInt(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (likeId == 0) {
                CallableStatement statement = connection.prepareCall("{CALL AddNewLike(?,?)}");
                statement.setInt(1, productId);
                statement.setInt(2, userId);
                statement.execute();
            } else {
                CallableStatement statement = connection.prepareCall("{CALL DeleteLike(?)}");
                statement.setInt(1, likeId);
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public Integer countLikeInProduct(Integer productId) {
        int countLike = 0;
        Connection connection = null;

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL CountLikesByProduct(?,?)}");
            statement.setInt(1, productId);
            statement.setInt(2, Types.INTEGER);
            statement.execute();
            countLike = statement.getInt(2);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countLike;
    }

}
