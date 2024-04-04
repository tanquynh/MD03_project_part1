package ra.project_md03.model.dao.image;

import ra.project_md03.model.entity.Image;

import java.util.List;

public interface ImageDao {
    List<Image> findAll();
    List<Image>findByProductId(int productId);
    boolean saveOrUpdate(Image image);
    void delete(Integer productId);
}
