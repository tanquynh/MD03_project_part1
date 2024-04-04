package ra.project_md03.model.service.product;


import ra.project_md03.model.dto.product.ProductDTO;
import ra.project_md03.model.dto.product.ProductEditDTO;
import ra.project_md03.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    boolean update(Product product);

    Product findById(Integer id);

    List<Product> findByName(String s);

    List<Product> pagination(Integer limit, Integer currentPage);

    void changeStatus(Integer id);

    Integer save(Product product);

    boolean save(ProductDTO productDTO);

    boolean update(ProductEditDTO EditDTO);

    boolean checkProductNameExist(String productName);

    List<Product> findAllActiveStatus(boolean status);

    boolean checkCategoryNull(Integer categoryId);

    List<Product> search(String name, int limit, int currentPage);

    Integer totalPageSearch(String name, int limit);

    int countProductByStatus(boolean b);

    Integer totalProductSearch(String name);

    List<Product> paginationActive(Integer limit, Integer currentPage);

    List<Product> searchActive(String name, int limit, int currentPage);

    Integer totalPageSearchActive(String name, int limit);

    Integer totalPageActive(int limit, int currentPage);

    Integer totalProductSearchActive(String name);

    List<Product> paginationAndSortActive(Integer limit, Integer currentPage, String sortType);

    Integer totalPageActiveSort(Integer limit, Integer currentPage, String sortType);

    List<Product> findByCategoryId(Integer categoryId);

    List<String> listBrand();

    List<Product> findByBrand(String brand);

    Integer countProductByBrand(String brand);

    List<Product> paginationAndSort(Integer limit, Integer currentPage, String sortType);

    Integer totalPageSort(Integer limit, Integer currentPage, String sortType);

}
