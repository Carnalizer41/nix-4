package ua.com.shyrkov.dao.impl;

import ua.com.shyrkov.dao.AuthorDao;
import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.entity.impl.BookEntity;
import ua.com.shyrkov.service.io.csv.AuthorCsvIo;
import ua.com.shyrkov.service.io.csv.BookCsvIo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class AuthorDaoImpl implements AuthorDao {

    private static AuthorCsvIo authorIo;
    private static BookCsvIo bookIo;

    public AuthorDaoImpl(String authorPath, String bookPath) {
        authorIo = AuthorCsvIo.getInstance(authorPath);
        bookIo = BookCsvIo.getInstance(bookPath);
    }

    public AuthorDaoImpl() {
        authorIo = AuthorCsvIo.getInstance();
        bookIo = BookCsvIo.getInstance();
    }

    @Override
    public List<AuthorEntity> findByBookId(int bookId) {
        BookEntity bookEntity = bookIo.read()
                                      .stream()
                                      .filter(book -> book.getId() == bookId)
                                      .findFirst()
                                      .orElseThrow(
                                              () -> new NoSuchElementException("There is no book with id = " + bookId));
        return authorIo.read()
                       .stream()
                       .filter(author -> bookEntity.getAuthorIds().contains(author.getId()))
                       .collect(Collectors.toList());
    }

    @Override
    public boolean isAuthorExists(int id) {
        return authorIo.read()
                       .stream()
                       .anyMatch(author -> author.getId() == id);
    }

    @Override
    public void create(AuthorEntity authorEntity) {
        List<AuthorEntity> all = findAll();
        int id = 1;
        if (!all.isEmpty())
            id = all.get(all.size() - 1).getId() + 1;
        authorEntity.setId(id);
        authorIo.writeNew(authorEntity);
    }

    @Override
    public AuthorEntity findById(int id) {
        return findAll()
                .stream()
                .filter(author -> author.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("There is no author with id = " + id));
    }

    @Override
    public List<AuthorEntity> findAll() {
        return authorIo.read();
    }

    @Override
    public void update(AuthorEntity authorEntity) {
        List<AuthorEntity> authors = authorIo.read();
        for (AuthorEntity entity : authors) {
            if (entity.getId().equals(authorEntity.getId())) {
                entity.setFirstName(authorEntity.getFirstName());
                entity.setLastName(authorEntity.getLastName());
                entity.setIsActive(authorEntity.getIsActive());
                entity.setBookIds(authorEntity.getBookIds());
                break;
            }
        }
        authorIo.writeAll(authors);
    }

    @Override
    public void delete(int id) {
        List<AuthorEntity> authors = authorIo.read();
        for (AuthorEntity authorEntity : authors) {
            if (authorEntity.getId() == id) {
                authorEntity.setIsActive(false);
            }
        }
        authorIo.writeAll(authors);
    }
}
