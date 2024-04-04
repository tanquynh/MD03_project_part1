package ra.project_md03.model.dao.coupons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.entity.Coupons;
import ra.project_md03.model.service.UploadService;
import ra.project_md03.util.ConnectionDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static java.sql.JDBCType.INTEGER;

@Repository
public class CouponsImpl implements CouponsDao {
    public static int totalPagePagination;
    public static int totalPageSearch;
    @Autowired
    private UploadService uploadService;

    @Override
    public List<Coupons> findAll() {
        Connection connection = null;
        List<Coupons> couponsList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_COUPONS_FIND_ALL() }");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Coupons coupons = new Coupons();
                coupons.setId(rs.getInt("id"));
                coupons.setTitle(rs.getString("title"));
                coupons.setCode(rs.getString("code"));
                coupons.setDiscount(rs.getString("discount"));
                coupons.setStatus(rs.getBoolean("status"));
                coupons.setStartDate(rs.getTimestamp("startDay"));
                coupons.setEndDate(rs.getTimestamp("endDay"));
                coupons.setQuantity(rs.getInt("quantity"));
                couponsList.add(coupons);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return couponsList;
    }

    @Override
    public boolean saveOrUpdate(Coupons coupons) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            if (coupons.getId() == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_COUPONS_ADD(?,?,?,?,?,?)}");
                statement.setString(1, coupons.getTitle());
                statement.setString(2, coupons.getCode());
                statement.setString(3, coupons.getDiscount());
                statement.setTimestamp(4, new Timestamp(coupons.getStartDate().getTime()));
                statement.setTimestamp(5, new Timestamp(coupons.getEndDate().getTime()));
                statement.setInt(6, coupons.getQuantity());
                check = statement.executeUpdate();
            } else {
                CallableStatement statement = connection.prepareCall("{CALL PROC_COUPONS_EDIT(?,?,?,?,?,?,?)}");
                statement.setString(1, coupons.getTitle());
                statement.setString(2, coupons.getCode());
                statement.setString(3, coupons.getDiscount());
                statement.setTimestamp(4, new Timestamp(coupons.getStartDate().getTime()));
                statement.setTimestamp(5, new Timestamp(coupons.getEndDate().getTime()));
                statement.setInt(6, coupons.getQuantity());
                statement.setInt(7, coupons.getId());
                check = statement.executeUpdate();
            }
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
    public Coupons findById(Integer integer) {
        Connection connection = null;
        Coupons coupons = new Coupons();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_COUPONS_FIND_BY_ID(?)}");
            statement.setInt(1, integer);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                coupons.setId(rs.getInt("id"));
                coupons.setTitle(rs.getString("title"));
                coupons.setCode(rs.getString("code"));
                coupons.setDiscount(rs.getString("discount"));
                coupons.setStartDate(rs.getTimestamp("startDay"));
                coupons.setEndDate(rs.getTimestamp("endDay"));
                coupons.setQuantity(rs.getInt("quantity"));
                coupons.setStatus(rs.getBoolean("status"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return coupons;
    }

    @Override
    public List<Coupons> search(String searchName) {
        List<Coupons> couponsList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_COUPONS_FIND(?,?)}");
            statement.setString(1, searchName);
            statement.registerOutParameter(2, INTEGER);
            ResultSet rs = statement.executeQuery();
            totalPageSearch = statement.getInt(2);
            while (rs.next()) {
                Coupons coupons = new Coupons();
                coupons.setId(rs.getInt("id"));
                coupons.setTitle(rs.getString("title"));
                coupons.setCode(rs.getString("code"));
                coupons.setDiscount(rs.getString("discount"));
                coupons.setStatus(rs.getBoolean("status"));
                coupons.setStartDate(rs.getTimestamp("startDay"));
                coupons.setEndDate(rs.getTimestamp("endDay"));
                coupons.setQuantity(rs.getInt("quantity"));
                couponsList.add(coupons);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return couponsList;
    }

    @Override
    public List<Coupons> pagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        List<Coupons> couponsList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_COUPONS_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, INTEGER);
            ResultSet rs = statement.executeQuery();
            totalPagePagination = statement.getInt(3);
            while (rs.next()) {
                Coupons coupons = new Coupons();
                coupons.setId(rs.getInt("id"));
                coupons.setTitle(rs.getString("title"));
                coupons.setCode(rs.getString("code"));
                coupons.setDiscount(rs.getString("discount"));
                coupons.setStatus(rs.getBoolean("status"));
                coupons.setStartDate(rs.getTimestamp("startDay"));
                coupons.setEndDate(rs.getTimestamp("endDay"));
                coupons.setQuantity(rs.getInt("quantity"));
                couponsList.add(coupons);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return couponsList;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall(" { Call PROC_COUPONS_CHANGE_STATUS(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }


    public Integer getTotalPagePagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        int totalPage;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_COUPONS_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, Types.INTEGER);
            statement.execute();
            totalPage = statement.getInt(3);
            ResultSet rs = statement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return totalPage;
    }


    public Integer getTotalPageSearch(String searchName) {
        Connection connection = null;
        int totalPage;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND(?,?)}");
            statement.setString(1, searchName);
            statement.registerOutParameter(2, Types.INTEGER);
            statement.execute();
            totalPage = statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return totalPage;
    }


    @Override
    public Integer countCoupons(Boolean b) {
        Connection connection = null;
        int countCoupons;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_COUPONS_COUNT_BY_STATUS(?,?)}");
            statement.setBoolean(1, b);
            statement.registerOutParameter(2, Types.INTEGER);
            statement.execute();
            countCoupons = statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return countCoupons;
    }

    @Override
    public Boolean findByName(String name) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND_BY_NAME(?)}");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
    public Boolean checkCouponsNameExist(String categoryName) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM coupons WHERE name=?");
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }


}
