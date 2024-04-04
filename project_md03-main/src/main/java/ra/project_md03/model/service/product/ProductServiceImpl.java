package ra.project_md03.model.service.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.project_md03.model.dao.brand.BrandDao;
import ra.project_md03.model.dao.category.CategoryDao;
import ra.project_md03.model.dao.image.ImageDao;
import ra.project_md03.model.dao.product.ProductDapImpl;
import ra.project_md03.model.dto.product.ProductDTO;
import ra.project_md03.model.dto.product.ProductEditDTO;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.entity.Image;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.service.UploadService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDapImpl productDao;
    @Autowired
    private UploadService uploadService;

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    BrandDao brandDao;
    @Autowired
    ImageDao imageDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public boolean update(Product product) {
        return productDao.update(product);
    }

    @Override
    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findByName(String s) {
        return productDao.findByName(s);
    }

    @Override
    public List<Product> pagination(Integer litmit, Integer currentPage) {
        return productDao.pagination(litmit, currentPage);
    }

    @Override
    public void changeStatus(Integer id) {
        productDao.changeStatus(id);
    }

    @Override
    public Integer save(Product product) {
        return productDao.save(product);
    }

    @Override
    public boolean save(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setStock(productDTO.getStock());
        product.setBrand(brandDao.findById(productDTO.getBrandId()));
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(categoryDao.findById(productDTO.getCategoryId()));
        // upload anh chinh
        if (productDTO.getImage().getSize() > 0) {
            String imageUrl = uploadService.uploadFileToServer(productDTO.getImage());
            product.setImage(imageUrl);
        }
        Integer productId = productDao.save(product);
        List<Image> subImageUrls = new ArrayList<>();
        if (!productDTO.getSubImage().isEmpty()) {
            for (MultipartFile f : productDTO.getSubImage()) {
                Image image = new Image(null, productId, uploadService.uploadFileToServer(f));
                imageDao.saveOrUpdate(image);
                subImageUrls.add(image);
            }
        }
        product.setImageList(subImageUrls);
        product.setProductId(productId);

        return productDao.update(product);
    }

    @Override
    public boolean update(ProductEditDTO productEditDTO) {
        Product product = new Product();
        product.setProductId(productEditDTO.getProductId());
        product.setProductName(productEditDTO.getProductName());
        product.setStock(productEditDTO.getStock());
        product.setBrand(brandDao.findById(productEditDTO.getBrandId()));
        product.setDescription(productEditDTO.getDescription());
        product.setPrice(productEditDTO.getPrice());
        product.setCategory(categoryDao.findById(productEditDTO.getCategoryId()));
        List<Image> subImageUrls = new ArrayList<>();
        // upload anh chinh
        if (!productEditDTO.getImage().isEmpty() && productEditDTO.getSubImage().isEmpty()) {
            String imageUrl = uploadService.uploadFileToServer(productEditDTO.getImage());
            product.setImage(imageUrl);
            product.setImageList(productDao.findById(productEditDTO.getProductId()).getImageList());
        } else if (productEditDTO.getImage().isEmpty() && !productEditDTO.getSubImage().isEmpty()) {
            product.setImage(productDao.findById(productEditDTO.getProductId()).getImage());
            for (MultipartFile f : productEditDTO.getSubImage()) {
                Image image = new Image(null, productEditDTO.getProductId(), uploadService.uploadFileToServer(f));
                imageDao.saveOrUpdate(image);
                subImageUrls.add(image);
            }
            product.setImageList(subImageUrls);
        } else if (!productEditDTO.getImage().isEmpty() && !productEditDTO.getSubImage().isEmpty()) {
            String imageUrl = uploadService.uploadFileToServer(productEditDTO.getImage());
            product.setImage(imageUrl);
            for (MultipartFile f : productEditDTO.getSubImage()) {
                Image image = new Image(null, productEditDTO.getProductId(), uploadService.uploadFileToServer(f));
                imageDao.saveOrUpdate(image);
                subImageUrls.add(image);
            }
            product.setImageList(subImageUrls);
        } else {
            product.setImage(productDao.findById(productEditDTO.getProductId()).getImage());
            product.setImageList(productDao.findById(productEditDTO.getProductId()).getImageList());
        }
        return productDao.update(product);

    }


    @Override
    public boolean checkProductNameExist(String productName) {
        List<Product> productList = productDao.findByName(productName);
        if (!productList.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Product> findAllActiveStatus(boolean status) {
        return productDao.findAllActiveStatus(status);
    }

    @Override
    public boolean checkCategoryNull(Integer categoryId) {
        Category category = categoryDao.findById(categoryId);
        if (category == null) {
            return true;
        }
        return false;
    }

    @Override
    public List<Product> search(String name, int limit, int currentPage) {
        return productDao.search(name, limit, currentPage);
    }

    @Override
    public Integer totalPageSearch(String name, int limit) {
        return productDao.totalPageSearch(name, limit);
    }

    @Override
    public int countProductByStatus(boolean b) {
        return productDao.countProductByStatus(b);
    }

    @Override
    public Integer totalProductSearch(String name) {
        return productDao.totalProductSearch(name);
    }

    @Override
    public List<Product> paginationActive(Integer limit, Integer currentPage) {
        return productDao.paginationActive(limit, currentPage);
    }

    @Override
    public List<Product> searchActive(String name, int limit, int currentPage) {
        return productDao.searchActive(name, limit, currentPage);
    }

    @Override
    public Integer totalPageSearchActive(String name, int limit) {
        return productDao.totalPageSearchActive(name, limit);
    }

    @Override
    public Integer totalPageActive(int limit, int currentPage) {
        return productDao.totalPageActive(limit, currentPage);
    }

    @Override
    public Integer totalProductSearchActive(String name) {
        return productDao.totalProductSearchActive(name);
    }

    @Override
    public List<Product> paginationAndSortActive(Integer limit, Integer currentPage, String sortType) {
        return productDao.paginationAndSortActive(limit, currentPage, sortType);
    }

    @Override
    public Integer totalPageActiveSort(Integer limit, Integer currentPage, String sortType) {
        return productDao.totalPageActiveSort(limit, currentPage, sortType);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productDao.findByCategoryId(categoryId);
    }

    @Override
    public List<String> listBrand() {
        return productDao.listBrand();
    }

    @Override
    public List<Product> findByBrand(String brand) {
        return productDao.findByBrand(brand);
    }

    @Override
    public Integer countProductByBrand(String brand) {
        return productDao.countProductByBrand(brand);
    }

    @Override
    public List<Product> paginationAndSort(Integer limit, Integer currentPage, String sortType) {
        return productDao.paginationAndSort(limit, currentPage, sortType);
    }

    @Override
    public Integer totalPageSort(Integer limit, Integer currentPage, String sortType) {
        return productDao.totalPageSort(limit, currentPage, sortType);
    }


}
