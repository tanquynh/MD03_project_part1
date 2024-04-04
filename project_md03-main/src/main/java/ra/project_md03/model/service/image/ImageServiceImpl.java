package ra.project_md03.model.service.image;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md03.model.dao.image.ImageDao;
import ra.project_md03.model.entity.Image;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imagesDao;

    @Override
    public List<Image> findAll() {
        return imagesDao.findAll();
    }

    @Override
    public List<Image> findByProductId(int productId) {
        return imagesDao.findByProductId(productId);
    }

    @Override
    public boolean saveOrUpdate(Image images) {
        return imagesDao.saveOrUpdate(images);
    }

    @Override
    public void delete(Integer productId) {
        imagesDao.delete(productId);
    }
}
