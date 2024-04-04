package ra.project_md03.model.service.coupons;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.coupons.CouponsDao;
import ra.project_md03.model.dto.coupons.CouponsDTO;
import ra.project_md03.model.dto.coupons.CouponsEditDTO;
import ra.project_md03.model.entity.Coupons;
import ra.project_md03.model.service.UploadService;

import java.util.List;

@Service
public class CouponsServiceImpl implements CouponsService {
    @Autowired
    private CouponsDao countDao;
    private static int totalPage;
    @Autowired
    private UploadService uploadService;


    public List<Coupons> findAll() {
        return countDao.findAll();
    }

    public Coupons findById(Integer id) {
        return countDao.findById(id);
    }


    public List<Coupons> search(String s) {
        return countDao.search(s);
    }


    public List<Coupons> pagination(Integer a, Integer b) {
        return countDao.pagination(a, b);
    }


    @Override
    public void saveOrUpdate(CouponsDTO couponsDTO) {
        Coupons coupons = new Coupons();
        coupons.setTitle(couponsDTO.getTitle());
        coupons.setCode(couponsDTO.getCode());
        coupons.setDiscount(couponsDTO.getDiscount());
        coupons.setStartDate(couponsDTO.getStartDate());
        coupons.setEndDate(couponsDTO.getEndDate());
        coupons.setQuantity(couponsDTO.getQuantity());
        countDao.saveOrUpdate(coupons);
    }

    @Override
    public void changeStatus(Integer id) {
        countDao.changeStatus(id);
    }


    @Override
    public Integer getTotalPagePagination(Integer limit, Integer currentPage) {
        return countDao.getTotalPagePagination(limit, currentPage);
    }

    @Override
    public Integer getTotalPageSearch(String searchName) {
        return countDao.getTotalPageSearch(searchName);
    }

    @Override
    public Integer countCoupons(Boolean b) {
        return countDao.countCoupons(b);
    }

    @Override
    public Boolean findByName(String name) {
        return countDao.findByName(name);
    }

    @Override
    public Boolean checkCouponsNameExist(String CouponsName) {
        return countDao.checkCouponsNameExist(CouponsName);
    }


    public void update(CouponsEditDTO couponsDTO) {
        Coupons coupons = new Coupons();
        coupons.setId(couponsDTO.getId());
        coupons.setTitle(couponsDTO.getTitle());
        coupons.setCode(couponsDTO.getCode());
        coupons.setDiscount(couponsDTO.getDiscount());
        coupons.setStartDate(couponsDTO.getStartDate());
        coupons.setEndDate(couponsDTO.getEndDate());
        coupons.setQuantity(couponsDTO.getQuantity());
        countDao.saveOrUpdate(coupons);
    }
}
