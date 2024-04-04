package ra.project_md03.model.service.cart;

public interface CartDBService {
    Integer checkCartUser(Integer userId);

    Integer save(Integer userId);

}
