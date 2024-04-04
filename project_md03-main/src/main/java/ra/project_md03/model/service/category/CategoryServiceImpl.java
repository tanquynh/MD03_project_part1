package ra.project_md03.model.service.category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.category.CategoryDao;
import ra.project_md03.model.dto.category.CategoryDTO;
import ra.project_md03.model.dto.category.CategoryEditDTO;
import ra.project_md03.model.entity.Category;
import ra.project_md03.model.service.UploadService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    private static int totalPage;
    @Autowired
    private UploadService uploadService;


    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Category findById(Integer id) {
        return categoryDao.findById(id);
    }


    public List<Category> search(String s) {
        return categoryDao.search(s);
    }


    public List<Category> pagination(Integer a, Integer b) {
        return categoryDao.pagination(a, b);
    }


    @Override
    public void saveOrUpdate(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setParentId(categoryDTO.getParentId());
        //upload anh
        if (categoryDTO.getImg().getSize() > 0) {
            String imageUrl = uploadService.uploadFileToServer(categoryDTO.getImg());
            category.setImage(imageUrl);
        }
        categoryDao.saveOrUpdate(category);
    }

    @Override
    public void Update(Category category) {
        categoryDao.saveOrUpdate(category);
    }
    @Override
    public void Update(CategoryEditDTO categoryEditDTO) {
        Category category = new Category();
        category.setCategoryId(categoryEditDTO.getCategoryId());
        category.setCategoryName(categoryEditDTO.getCategoryName());
        category.setParentId(categoryEditDTO.getParentId());
        //upload anh
        if (categoryEditDTO.getImg().getSize() > 0) {
            String imageUrl = uploadService.uploadFileToServer(categoryEditDTO.getImg());
            category.setImage(imageUrl);
        } else {
            category.setImage(categoryDao.findById(categoryEditDTO.getCategoryId()).getImage());
        }
        categoryDao.saveOrUpdate(category);
    }

    @Override
    public void changeStatus(Integer id) {
        categoryDao.changeStatus(id);
    }

    @Override
    public List<Category> findParent() {
        return categoryDao.findParent();
    }

    @Override
    public Integer getTotalPagePagination(Integer limit, Integer currentPage) {
        return categoryDao.getTotalPagePagination(limit, currentPage);
    }

    @Override
    public Integer getTotalPageSearch(String searchName) {
        return categoryDao.getTotalPageSearch(searchName);
    }

    @Override
    public Integer countCategory(Boolean b) {
        return categoryDao.countCategory(b);
    }

    @Override
    public Boolean findByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public Boolean checkCategoryNameExist(String categoryName) {
        return categoryDao.checkCategoryNameExist(categoryName);
    }

    @Override
    public Boolean checkCategoryNameExistEdit(String categoryName, String name) {
        for (Category category : categoryDao.findAll()) {
            if (!category.getCategoryName().equalsIgnoreCase(name)) {
                if (category.getCategoryName().equals(categoryName)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public List<Category> findByParentId(Integer parentId) {
        return categoryDao.findByParentId(parentId);
    }


    @Override
    public Boolean checkParentId(Integer parentId) {
        if (parentId == null) {
            return true;
        }
        return false;
    }
}
