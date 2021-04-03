package ua.com.shyrkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.shyrkov.dao.AuthorDao;
import ua.com.shyrkov.dao.BookDao;
import ua.com.shyrkov.dao.impl.AuthorDaoImpl;
import ua.com.shyrkov.dao.impl.BookDaoImpl;
import ua.com.shyrkov.entity.BookEntity;
import ua.com.shyrkov.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final Logger logger = Logger.getLogger(BookServiceImpl.class);

    @Override
    public List<BookEntity> findBooksByAuthor(int id) {
        logger.info("Finding books by author with id="+id);
        if(authorDao.isAuthorExists(id)){
            return bookDao.findBooksByAuthorId(id);
        }else{
            logger.error("Author with id="+id+" doesn't exists");
            throw new RuntimeException("Author with id="+id+" doesn't exists");
        }
    }

    @Override
    public void create(BookEntity bookEntity) {
        logger.info("Creating book "+bookEntity.toString());
        bookDao.create(bookEntity);
        logger.info("New book was successfully created");
    }

    @Override
    public BookEntity findById(int id) {
        logger.info("Finding book with id="+id);
        if(bookDao.isBookExists(id)){
            return bookDao.findById(id);
        }else{
            logger.error("Book with id="+id+" doesn`t exists");
            throw new RuntimeException("Book with id="+id+" doesn`t exists");
        }
    }

    @Override
    public List<BookEntity> findAll() {
        logger.info("Finding all books");
        return bookDao.findAll();
    }

    @Override
    public void update(BookEntity bookEntity) {
        if(bookDao.isBookExists(bookEntity.getId())){
            logger.info("Updating book "+bookDao.findById(bookEntity.getId()).toString());
            bookDao.update(bookEntity);
            logger.info("Book was successfully updated");
        }else{
            logger.info("Creating new book");
            bookDao.create(bookEntity);
            logger.info("New book was successfully created");
        }
    }

    @Override
    public void delete(int id) {
        logger.info("Deleting book with id="+id);
        if(bookDao.isBookExists(id)){
            bookDao.delete(id);
            logger.info("Book was successfully deleted");
        }else{
            logger.error("Book with id="+id+" doesn`t exists");
            throw new RuntimeException("Book with id="+id+" doesn`t exists");
        }
    }
}
