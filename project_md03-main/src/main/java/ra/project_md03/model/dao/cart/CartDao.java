package ra.project_md03.model.dao.cart;

public interface CartDao {
    Integer checkCartUser(Integer userId);
    Integer save (Integer userId);
}
