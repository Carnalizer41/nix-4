package ua.com.shyrkov.service;

import ua.com.shyrkov.entity.BaseEntity;

import java.util.List;

public interface CommonService<E extends BaseEntity> {

    void create(E e);
    E findById(int id);
    List<E> findAll();
    void update(E e);
    void delete(int id);

}
