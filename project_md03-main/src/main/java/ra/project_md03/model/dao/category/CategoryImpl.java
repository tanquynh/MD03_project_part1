package ra.project_md03.model.dao.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.service.UploadService;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.INTEGER;

@Repository
public class CategoryImpl implements CategoryDao {
    public static int totalPagePagination;
    public static int totalPageSearch;
    @Autowired
    private UploadService uploadService;

    @Override
    public List<Category> findAll() {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND_ALL() }");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("id"));
                category.setCategoryName(rs.getString("name"));
                category.setCategoryStatus(rs.getBoolean("status"));
                category.setParentId(rs.getInt("parent_id"));
//                category.setImage(rs("image"));
                categories.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public boolean saveOrUpdate(Category category) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            if (category.getCategoryId() == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_ADD(?,?,?)}");
                statement.setString(1, category.getCategoryName());
                statement.setInt(2, category.getParentId());
                statement.setString(3, category.getImage());
                check = statement.executeUpdate();
            } else {
                CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_EDIT(?,?,?,?)}");
                statement.setString(1, category.getCategoryName());
                statement.setInt(2, category.getParentId());
                statement.setString(3, category.getImage());
                statement.setInt(4, category.getCategoryId());
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
    public Category findById(Integer integer) {
        Connection connection = null;
        Category category = new Category();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_FIND_CATEGORY_BY_ID(?)}");
            statement.setInt(1, integer);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                category.setCategoryId(rs.getInt("id"));
                category.setCategoryName(rs.getString("name"));
                category.setCategoryStatus(rs.getBoolean("status"));
                category.setParentId(rs.getInt("parent_id"));
                category.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return category;
    }

    @Override
    public List<Category> search(String searchName) {
        List<Category> categories = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND(?,?)}");
            statement.setString(1, searchName);
            statement.registerOutParameter(2, INTEGER);
            ResultSet rs = statement.executeQuery();
            totalPageSearch = statement.getInt(2);
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("id"));
                category.setCategoryName(rs.getString("name"));
                category.setCategoryStatus(rs.getBoolean("status"));
                category.setParentId(rs.getInt("parent_id"));
                category.setImage(rs.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public List<Category> pagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, INTEGER);
            ResultSet rs = statement.executeQuery();
            totalPagePagination = statement.getInt(3);
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("id"));
                category.setCategoryName(rs.getString("name"));
                category.setCategoryStatus(rs.getBoolean("status"));
                category.setParentId(rs.getInt("parent_id"));
                category.setImage(rs.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall(" { Call PROC_CATEGORY_CHANGE_STATUS(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Category> findParent() {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_PARENT()}");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("id"));
                category.setCategoryName(rs.getString("name"));
                category.setCategoryStatus(rs.getBoolean("status"));
                category.setParentId(rs.getInt("parent_id"));
                category.setImage(rs.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }


    public Integer getTotalPagePagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        int totalPage;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_PAGINATION(?,?,?)}");
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
            statement.registerOutParameter(2,Types.INTEGER);
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
    public Integer countCategory(Boolean b) {
        Connection connection = null;
        int countCategory;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_COUNT_BY_STATUS(?,?)}");
            statement.setBoolean(1, b);
            statement.registerOutParameter(2, Types.INTEGER);
            statement.execute();
            countCategory = statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return countCategory;
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
    public Boolean checkCategoryNameExist(String categoryName) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE name=?");
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

    @Override
    public List<Category> findByParentId(Integer parentId) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND_BY_PARENT_ID(?)}");
            statement.setInt(1, parentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                category.setParentId(resultSet.getInt("parent_id"));
                category.setImage(resultSet.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }
}
