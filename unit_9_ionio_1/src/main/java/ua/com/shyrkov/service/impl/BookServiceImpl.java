package ua.com.shyrkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.shyrkov.dao.AuthorDao;
import ua.com.shyrkov.dao.BookDao;
import ua.com.shyrkov.dao.impl.AuthorDaoImpl;
import ua.com.shyrkov.dao.impl.BookDaoImpl;
import ua.com.shyrkov.entity.BaseEntity;
import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.entity.impl.BookEntity;
import ua.com.shyrkov.service.BookService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private static BookDao dao = new BookDaoImpl();
    private static AuthorDao authorDao = new AuthorDaoImpl();
    private static final Logger logger = Logger.getLogger(BookServiceImpl.class);
    private static BookServiceImpl instance;

    private BookServiceImpl(String authorPath, String bookPath) {
        dao = new BookDaoImpl(authorPath, bookPath);
        authorDao = new AuthorDaoImpl(authorPath, bookPath);
    }

    private BookServiceImpl(){}

    @Override
    public List<BookEntity> findBooksByAuthorId(int id) {
        logger.info("Finding books with author`s id = " + id);
        try {
            return dao.findByAuthorId(id);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void create(BookEntity bookEntity) {
        logger.info("Creating book " + bookEntity.getTitle());
        for (Integer authorId : bookEntity.getAuthorIds()) {
            if(!authorDao.isAuthorExists(authorId))
                throw new NoSuchElementException("There is no author with id = "+authorId);
        }
        dao.create(bookEntity);
        for (Integer authorId : bookEntity.getAuthorIds()) {
            AuthorEntity author = authorDao.findById(authorId);
            author.addBookId(bookEntity.getId());
            authorDao.update(author);
        }
        logger.info("Book successfully created");
    }

    @Override
    public BookEntity findById(int id) {
        logger.info("Finding book with id = " + id);
        try {
            return dao.findById(id);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<BookEntity> findAll() {
        logger.info("Getting all books from database");
        return dao.findAll()
                  .stream()
                  .filter(BaseEntity::getIsActive)
                  .collect(Collectors.toList());
    }

    @Override
    public void update(BookEntity bookEntity) {
        if (dao.isBookExists(bookEntity.getId())) {
            logger.info("Updating " + bookEntity.getTitle() + " book info");
            dao.update(bookEntity);
            logger.info("Book was successfully updated");
        } else {
            logger.info("Creating book " + bookEntity.getTitle());
            create(bookEntity);
            logger.info("Book successfully created");
        }
    }

    @Override
    public void delete(int id) {
        logger.info("Deleting book with id = " + id);
        if (dao.isBookExists(id)) {
            dao.delete(id);
            logger.info("Book was successfully deleted");
        } else {
            logger.error("There is no book with id = " + id);
            throw new RuntimeException("There is no book with id = " + id);
        }
    }

    public static BookServiceImpl getInstance() {
        if (instance == null)
            instance = new BookServiceImpl();
        return instance;
    }

    public static BookServiceImpl getInstance(String authorPath, String bookPath) {
        if (instance == null)
            instance = new BookServiceImpl(authorPath, bookPath);
        return instance;
    }

}
