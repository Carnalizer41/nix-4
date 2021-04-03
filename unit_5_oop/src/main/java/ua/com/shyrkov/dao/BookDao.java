package ua.com.shyrkov.dao;

import ua.com.shyrkov.entity.BookEntity;

import java.util.List;

public interface BookDao extends CommonDao<BookEntity> {

    List<BookEntity> findBooksByAuthorId(int id);
    boolean isBookExists(int id);
}
