package ra.project_md03.model.dao;

import java.util.List;

public interface IGenericDao<T, ID, ST> {
    List<T> findAll();

    boolean saveOrUpdate(T t);

    T findById(ID id);

    List<T> search(ST s);

    List<T> pagination(ID a, ID b);

    void changeStatus(ID id);
}
