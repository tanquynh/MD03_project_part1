package ra.project_md03.model.dao.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.project_md03.model.dao.brand.BrandDao;
import ra.project_md03.model.dao.category.CategoryDao;
import ra.project_md03.model.entity.Product;
import ra.project_md03.util.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDapImpl implements ProductDao {
    public static int totalPage;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Product> findAll() {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_ALL()}");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public boolean update(Product product) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_EDIT(?,?,?,?,?,?,?,?)}");
            statement.setString(1, product.getProductName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getBrand().getBrandId());
            statement.setInt(5, product.getStock());
            statement.setString(6, product.getImage());
            statement.setInt(7, product.getCategory().getCategoryId());
            statement.setInt(8, product.getProductId());

            int check = statement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Product findById(Integer id) {
        Connection connection = null;
        Product product = new Product();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_BY_ID(?)}");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return product;
    }

    @Override
    public List<Product> findByName(String productName) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_BY_NAME(?)}");
            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public List<Product> pagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            totalPage = statement.getInt(3);
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_CHANGE_STATUS(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public Integer save(Product product) {
        Connection connection = null;
        int productIdAdd = 0;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_ADD(?,?,?,?,?,?,?,?)}");
            statement.setString(1, product.getProductName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getBrand().getBrandId());
            statement.setInt(5, product.getStock());
            statement.setString(6, product.getImage());
            statement.setInt(7, product.getCategory().getCategoryId());
            statement.registerOutParameter(8, java.sql.Types.INTEGER);
            statement.executeUpdate();
            productIdAdd = statement.getInt(8);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return productIdAdd;
    }

    @Override
    public List<Product> findAllActiveStatus(boolean status) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_COUNT_BY_STATUS(?,?)}");
            statement.setBoolean(1, status);
            statement.registerOutParameter(2, Types.BOOLEAN);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Integer countProductByStatus(Boolean status) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_COUNT_BY_STATUS(?,?)}");
            statement.setBoolean(1, status);
            statement.registerOutParameter(2, Types.BOOLEAN);
            statement.executeQuery();
            return statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Product> search(String name, int limit, int currentPage) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_PAGED(?,?,?)}");
            statement.setString(1, name);
            statement.setInt(2, limit);
            statement.setInt(3, currentPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Integer totalPageSearch(String name, int limit) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_COUNT(?)}");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return (int) Math.ceil((double) products.size() / limit);
    }

    @Override
    public Integer totalProductSearch(String name) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_COUNT(?)}");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products.size();
    }

    @Override
    public List<Product> paginationActive(Integer limit, Integer currentPage) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_ACTIVE_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            totalPage = statement.getInt(3);
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public List<Product> searchActive(String name, int limit, int currentPage) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_ACTIVE_FIND_PAGED(?,?,?)}");
            statement.setString(1, name);
            statement.setInt(2, limit);
            statement.setInt(3, currentPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Integer totalPageSearchActive(String name, int limit) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_ACTIVE_FIND(?)}");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return (int) Math.ceil((double) products.size() / limit);
    }

    @Override
    public Integer totalPageActive(int limit, int currentPage) {
        Connection connection = null;
        int totalPageActive;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_ACTIVE_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            totalPageActive = statement.getInt(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return totalPageActive;
    }

    @Override
    public Integer totalProductSearchActive(String name) {
        Connection connection = null;
        int count = 0;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_ACTIVE_FIND_NUMBER(?)}");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return count;
    }

    @Override
    public List<Product> paginationAndSortActive(Integer limit, Integer currentPage, String sortType) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_SORT_PAGINATION(?,?,?,?)}");
//            PreparedStatement statement= connection.prepareStatement("SELECT*FROM product ORDER BY PRICE");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.setString(3, sortType);
            statement.registerOutParameter(4, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
//            totalPage = statement.getInt(4);
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Integer totalPageActiveSort(Integer limit, Integer currentPage, String sortType) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_SORT_PAGINATION(?,?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.setString(3, sortType);
            statement.registerOutParameter(4, java.sql.Types.INTEGER);
            statement.executeQuery();
            return statement.getInt(4);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_BY_CATEGORY_ID(?)}");
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public List<String> listBrand() {
        Connection connection = null;
        List<String> brands = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT brand FROM project_database.product");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                brands.add(brand);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return brands;
    }

    @Override
    public List<Product> findByBrand(String brand) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_BY_BRAND(?,?)}");
            statement.setString(1, brand);
            statement.registerOutParameter(2, Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Integer countProductByBrand(String brand) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_BY_BRAND(?,?)}");
            statement.setString(1, brand);
            statement.registerOutParameter(2, Types.INTEGER);

            ResultSet resultSet = statement.executeQuery();
            return statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Product> paginationAndSort(Integer limit, Integer currentPage, String sortType) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_SORT_PAGINATION_ALL(?,?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.setString(3, sortType);
            statement.registerOutParameter(4, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
//            totalPage = statement.getInt(4);
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDescription(resultSet.getString("description"));
                product.setBrand(brandDao.findById(resultSet.getInt("brand_id")));
                product.setStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setImage(resultSet.getString("image"));
                product.setCategory(categoryDao.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Integer totalPageSort(Integer limit, Integer currentPage, String sortType) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_PRODUCT_SORT_PAGINATION_ALL(?,?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.setString(3, sortType);
            statement.registerOutParameter(4, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            return statement.getInt(4);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
