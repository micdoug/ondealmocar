package web.dao;

import java.util.Collection;
import java.util.Map;

public interface DAO<T> {

    void create(T value);
    void update(T value);
    void delete(int id);
    Collection<T> getAll();
    T getById(int id);
    Collection<T> getFiltered(Map<String, Object> filters);

}
