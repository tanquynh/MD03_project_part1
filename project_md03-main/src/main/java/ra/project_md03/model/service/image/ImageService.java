package ra.project_md03.model.service.image;



import ra.project_md03.model.entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();
    List<Image>findByProductId(int productId);
    boolean saveOrUpdate(Image images);
    void delete(Integer productId);
}
