package ua.com.shyrkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.shyrkov.dao.AuthorDao;
import ua.com.shyrkov.dao.BookDao;
import ua.com.shyrkov.dao.impl.AuthorDaoImpl;
import ua.com.shyrkov.dao.impl.BookDaoImpl;
import ua.com.shyrkov.entity.BaseEntity;
import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.entity.impl.BookEntity;
import ua.com.shyrkov.service.AuthorService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class AuthorServiceImpl implements AuthorService {

    private static AuthorDao dao;
    private static BookDao bookDao;
    private static final Logger logger = Logger.getLogger(BookServiceImpl.class);
    private static AuthorServiceImpl instance;

    private AuthorServiceImpl(String authorPath, String bookPath) {
        dao = new AuthorDaoImpl(authorPath, bookPath);
        bookDao = new BookDaoImpl(authorPath, bookPath);
    }

    private AuthorServiceImpl(){
        dao = new AuthorDaoImpl();
        bookDao = new BookDaoImpl();
    }

    @Override
    public List<AuthorEntity> findAuthorsByBookId(int id) {
        logger.info("Finding author with book`s id = " + id);
        try {
            return dao.findByBookId(id);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void create(AuthorEntity entity) {
        logger.info("Creating author " + entity.getFirstName() + " " + entity.getLastName());
        for (Integer bookId : entity.getBookIds()) {
            if (!bookDao.isBookExists(bookId))
                throw new NoSuchElementException("There is no book with id = " + bookId);
        }
        dao.create(entity);
        for (Integer bookId : entity.getBookIds()) {
            BookEntity book = bookDao.findById(bookId);
            book.addAuthorId(entity.getId());
            bookDao.update(book);
        }
        logger.info("Author successfully created");
    }

    @Override
    public AuthorEntity findById(int id) {
        logger.info("Finding author with id = " + id);
        try {
            return dao.findById(id);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<AuthorEntity> findAll() {
        logger.info("Getting all authors from database");
        return dao.findAll()
                  .stream()
                  .filter(BaseEntity::getIsActive)
                  .collect(Collectors.toList());
    }

    @Override
    public void update(AuthorEntity entity) {
        if (dao.isAuthorExists(entity.getId())) {
            logger.info("Updating author`s info");
            dao.update(entity);
            logger.info("Author was successfully updated");
        } else {
            logger.info("Creating author " + entity.getFirstName() + " " + entity.getLastName());
            create(entity);
            logger.info("Author successfully created");
        }
    }

    @Override
    public void delete(int id) {
        logger.info("Deleting author with id = " + id);
        if (dao.isAuthorExists(id)) {
            dao.delete(id);
            logger.info("Author was successfully deleted");
        } else {
            logger.error("There is no author with id = " + id);
            throw new RuntimeException("There is no author with id = " + id);
        }
    }

    public static AuthorServiceImpl getInstance() {
        if (instance == null)
            instance = new AuthorServiceImpl();
        return instance;
    }

    public static AuthorServiceImpl getInstance(String authorPath, String bookPath) {
        if (instance == null)
            instance = new AuthorServiceImpl(authorPath, bookPath);
        return instance;
    }

}
