package ua.com.shyrkov.dao.impl;

import ua.com.shyrkov.dao.BookDao;
import ua.com.shyrkov.db.LibraryDb;
import ua.com.shyrkov.entity.BookEntity;

import java.util.List;

public class BookDaoImpl implements BookDao {

    @Override
    public void create(BookEntity bookEntity) {
        LibraryDb.getInstance().createBook(bookEntity);
    }

    @Override
    public BookEntity findById(int id) {
        return LibraryDb.getInstance().findBookById(id);
    }

    @Override
    public List<BookEntity> findAll() {
        return LibraryDb.getInstance().findAllBooks();
    }

    @Override
    public void update(BookEntity bookEntity) {
        LibraryDb.getInstance().updateBook(bookEntity);
    }

    @Override
    public void delete(int id) {
        LibraryDb.getInstance().deleteBook(id);
    }

    @Override
    public List<BookEntity> findBooksByAuthorId(int id) {
        return LibraryDb.getInstance().findBooksByAuthorId(id);
    }

    @Override
    public boolean isBookExists(int id) {
        return LibraryDb.getInstance().isBookExists(id);
    }
}
