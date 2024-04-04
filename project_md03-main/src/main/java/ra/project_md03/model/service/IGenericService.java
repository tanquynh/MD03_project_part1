package ra.project_md03.model.service;

import java.util.List;

public interface IGenericService<T,ID,ST> {
    List<T> findAll();
    boolean saveOrUpdate(T t);
    T findById(ID id);
    List<T>search(ST s);
    List<T>pagination(ID a,ID b);
     void changeStatus(ID id);
}
