package ra.project_md03.model.service.brand;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.brand.BrandDao;
import ra.project_md03.model.dto.brand.BrandDTO;
import ra.project_md03.model.dto.brand.BrandDTOEdit;
import ra.project_md03.model.entity.Brand;
import ra.project_md03.model.service.UploadService;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;
    private static int totalPage;
    @Autowired
    private UploadService uploadService;


    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    public Brand findById(Integer id) {
        return brandDao.findById(id);
    }


    public List<Brand> search(String s) {
        return brandDao.search(s);
    }


    public List<Brand> pagination(Integer a, Integer b) {
        return brandDao.pagination(a, b);
    }


    @Override
    public void saveOrUpdate(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setBrandName(brandDTO.getBrandName());
        brandDao.saveOrUpdate(brand);
    }

    @Override
    public void changeStatus(Integer id) {
        brandDao.changeStatus(id);
    }

    @Override
    public Integer getTotalPagePagination(Integer limit, Integer currentPage) {
        return brandDao.getTotalPagePagination(limit, currentPage);
    }

    @Override
    public Integer getTotalPageSearch(String searchName) {
        return brandDao.getTotalPageSearch(searchName);
    }

    @Override
    public Integer countBrand(Boolean b) {
        return brandDao.countBrand(b);
    }

    @Override
    public Boolean findByName(String name) {
        return brandDao.findByName(name);
    }

    @Override
    public Boolean checkBrandNameExist(String brandName) {
        return brandDao.checkBrandNameExist(brandName);
    }

    @Override
    public Boolean checkBrandNameExistEdit(String categoryName, String name) {
        for (Brand brand : brandDao.findAll()) {
            if (!brand.getBrandName().equalsIgnoreCase(name)) {
                if (brand.getBrandName().equals(categoryName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void update(BrandDTOEdit brand) {
        Brand brand1 = new Brand();
        brand1.setBrandId(brand.getId());
        brand1.setBrandName(brand.getBrandName());
        brandDao.saveOrUpdate(brand1);
    }
}
