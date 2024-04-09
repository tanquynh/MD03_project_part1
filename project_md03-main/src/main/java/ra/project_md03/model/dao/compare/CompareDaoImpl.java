package ra.project_md03.model.dao.compare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.dao.product.ProductDao;
import ra.project_md03.model.entity.Compare;
import ra.project_md03.model.service.compare.CompareService;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompareDaoImpl implements CompareService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Compare> findByUserId(Integer id) {
        Connection connection = null;
        List<Compare> compares = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_COMPARE_FIND_NEWEST_BY_USER_ID(?)}");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Compare compare = new Compare();
                compare.setCompareId(rs.getInt("id"));
                compare.setUserId(rs.getInt("user_id"));
                compare.setProduct(productDao.findById(rs.getInt("product_id")));
                compares.add(compare);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return compares;
    }

    @Override
    public void save(Compare compare) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_COMPARE_ADD(?,?)}");
            statement.setInt(1, compare.getUserId());
            statement.setInt(2, compare.getProduct().getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer productId) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_COMPARE_DELETE(?)}");
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }


    @Override
    public Compare findByProductId(Integer productId, Integer userId) {
        Connection connection = ConnectionDatabase.openConnection();
        Compare compare = new Compare();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_COMPARE_FIND_BY_PRODUCT_ID(?,?) }");
            statement.setInt(1, productId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                compare.setCompareId(rs.getInt("id"));
                compare.setUserId(rs.getInt("user_id"));
                compare.setProduct(productDao.findById(rs.getInt("product_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return compare;
    }
}
