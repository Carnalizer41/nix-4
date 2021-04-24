package ua.com.shyrkov.dao.impl;

import ua.com.shyrkov.dao.BookDao;
import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.entity.impl.BookEntity;
import ua.com.shyrkov.service.io.csv.AuthorCsvIo;
import ua.com.shyrkov.service.io.csv.BookCsvIo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {

    private static AuthorCsvIo authorIo = AuthorCsvIo.getInstance();
    private static BookCsvIo bookIo = BookCsvIo.getInstance();

    public BookDaoImpl(String authorPath, String bookPath) {
        authorIo = AuthorCsvIo.getInstance(authorPath);
        bookIo = BookCsvIo.getInstance(bookPath);
    }

    public BookDaoImpl() {
        authorIo = AuthorCsvIo.getInstance();
        bookIo = BookCsvIo.getInstance();
    }

    @Override
    public List<BookEntity> findByAuthorId(int authorId) {
        AuthorEntity authorEntity = authorIo.read()
                                            .stream()
                                            .filter(author -> author.getId() == authorId)
                                            .findFirst()
                                            .orElseThrow(() -> new NoSuchElementException(
                                                    "There is no author with id = " + authorId));
        return bookIo.read()
                     .stream()
                     .filter(book -> authorEntity.getBookIds().contains(book.getId()))
                     .collect(Collectors.toList());
    }

    @Override
    public boolean isBookExists(int id) {
        return bookIo.read()
                     .stream()
                     .anyMatch(book -> book.getId() == id);
    }

    @Override
    public void create(BookEntity bookEntity) {
        List<BookEntity> all = findAll();
        int id = 1;
        if (!all.isEmpty())
            id = all.get(all.size() - 1).getId() + 1;
        bookEntity.setId(id);
        bookIo.writeNew(bookEntity);
    }

    @Override
    public BookEntity findById(int id) {
        return bookIo.read()
                     .stream()
                     .filter(book -> book.getId() == id)
                     .findFirst()
                     .orElseThrow(() -> new NoSuchElementException("There is no book with id = " + id));
    }

    @Override
    public List<BookEntity> findAll() {
        return bookIo.read();
    }

    @Override
    public void update(BookEntity bookEntity) {
        List<BookEntity> entities = findAll();
        for (BookEntity entity : entities) {
            if (entity.getId().equals(bookEntity.getId())) {
                entity.setTitle(bookEntity.getTitle());
                entity.setIsActive(bookEntity.getIsActive());
                entity.setAuthorIds(bookEntity.getAuthorIds());
                break;
            }
        }
        bookIo.writeAll(entities);
    }

    @Override
    public void delete(int id) {
        List<BookEntity> bookEntities = bookIo.read();
        for (BookEntity bookEntity : bookEntities) {
            if (bookEntity.getId() == id) {
                bookEntity.setIsActive(false);
            }
        }
        bookIo.writeAll(bookEntities);
    }
}
