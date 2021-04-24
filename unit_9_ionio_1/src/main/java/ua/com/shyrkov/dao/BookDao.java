package ua.com.shyrkov.dao;

import ua.com.shyrkov.entity.impl.BookEntity;

import java.util.List;

public interface BookDao extends CommonDao<BookEntity> {

    List<BookEntity> findByAuthorId(int authorId);
    boolean isBookExists(int id);
}
