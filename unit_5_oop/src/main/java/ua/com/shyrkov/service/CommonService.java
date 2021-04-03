package ua.com.shyrkov.service;

import ua.com.shyrkov.entity.BaseEntity;

import java.util.List;

public interface CommonService<DATA extends BaseEntity> {

    void create(DATA data);
    DATA findById(int id);
    List<DATA> findAll();
    void update(DATA data);
    void delete(int id);
}
