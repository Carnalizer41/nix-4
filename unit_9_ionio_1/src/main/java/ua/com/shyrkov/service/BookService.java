package ua.com.shyrkov.service;

import ua.com.shyrkov.entity.impl.BookEntity;

import java.util.List;

public interface BookService extends CommonService<BookEntity> {

    List<BookEntity> findBooksByAuthorId(int id);
}
