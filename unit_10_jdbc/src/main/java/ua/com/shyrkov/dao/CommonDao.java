package ua.com.shyrkov.dao;

import java.util.List;

public interface CommonDao<E> {

    List<E> findAll();
    void update(E entity);
}
