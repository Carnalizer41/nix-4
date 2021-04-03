package ua.com.shyrkov.service;

import ua.com.shyrkov.entity.BookEntity;

import java.util.List;

public interface BookService extends CommonService<BookEntity> {

    List<BookEntity> findBooksByAuthor(int id);
}
