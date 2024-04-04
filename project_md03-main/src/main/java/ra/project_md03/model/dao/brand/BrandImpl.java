package ra.project_md03.model.dao.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.service.UploadService;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.INTEGER;

@Repository
public class BrandImpl implements BrandDao {
    public static int totalPagePagination;
    public static int totalPageSearch;
    @Autowired
    private UploadService uploadService;

    @Override
    public List<Brand> findAll() {
        Connection connection = null;
        List<Brand> brandList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_BRAND_FIND_ALL() }");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();


                brand.setBrandId(rs.getInt("id"));
                brand.setBrandName(rs.getString("name"));
                brand.setBrandStatus(rs.getBoolean("status"));
                brandList.add(brand);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return brandList;
    }

    @Override
    public boolean saveOrUpdate(Brand Brand) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            if (Brand.getBrandId() == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_BRAND_ADD(?)}");
                statement.setString(1, Brand.getBrandName());
                check = statement.executeUpdate();
            } else {
                CallableStatement statement = connection.prepareCall("{CALL PROC_BRAND_EDIT(?,?)}");
                statement.setString(1, Brand.getBrandName());
                statement.setInt(2, Brand.getBrandId());
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
    public Brand findById(Integer integer) {
        Connection connection = null;
        Brand brand = new Brand();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_FIND_BY_ID(?)}");
            statement.setInt(1, integer);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                brand.setBrandId(rs.getInt("id"));
                brand.setBrandName(rs.getString("name"));
                brand.setBrandStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return brand;
    }

    @Override
    public List<Brand> search(String searchName) {
        List<Brand> brandList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_BRAND_FIND(?,?)}");
            statement.setString(1, searchName);
            statement.registerOutParameter(2, INTEGER);
            ResultSet rs = statement.executeQuery();
            totalPageSearch = statement.getInt(2);
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setBrandId(rs.getInt("id"));
                brand.setBrandName(rs.getString("name"));
                brand.setBrandStatus(rs.getBoolean("status"));

                brandList.add(brand);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return brandList;
    }

    @Override
    public List<Brand> pagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        List<Brand> brandList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_BRAND_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, INTEGER);
            ResultSet rs = statement.executeQuery();
            totalPagePagination = statement.getInt(3);
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setBrandId(rs.getInt("id"));
                brand.setBrandName(rs.getString("name"));
                brand.setBrandStatus(rs.getBoolean("status"));

                brandList.add(brand);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return brandList;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall(" { Call PROC_BRAND_CHANGE_STATUS(?)}");
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
            CallableStatement statement = connection.prepareCall("{CALL PROC_BRAND_PAGINATION(?,?,?)}");
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
            CallableStatement statement = connection.prepareCall("{CALL PROC_BRAND_FIND(?,?)}");
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
    public Integer countBrand(Boolean b) {
        Connection connection = null;
        int countBrand;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_BRAND_COUNT_BY_STATUS(?,?)}");
            statement.setBoolean(1, b);
            statement.registerOutParameter(2, Types.INTEGER);
            statement.execute();
            countBrand = statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return countBrand;
    }

    @Override
    public Boolean findByName(String name) {
        Connection connection = null;
        List<Brand> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_Brand_FIND_BY_NAME(?)}");
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
    public Boolean checkBrandNameExist(String BrandName) {
        Connection connection = null;
        List<Brand> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Brand WHERE name=?");
            preparedStatement.setString(1, BrandName);
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
