package ua.com.shyrkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.shyrkov.dao.AuthorDao;
import ua.com.shyrkov.dao.BookDao;
import ua.com.shyrkov.dao.impl.AuthorDaoImpl;
import ua.com.shyrkov.dao.impl.BookDaoImpl;
import ua.com.shyrkov.entity.AuthorEntity;
import ua.com.shyrkov.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final Logger logger = Logger.getLogger(AuthorServiceImpl.class);

    @Override
    public List<AuthorEntity> findAuthorsByBook(int id) {
        logger.info("Finding authors by book with id=" + id + "...");
        if (bookDao.isBookExists(id)) {
            return authorDao.findAuthorsByBookId(id);
        } else {
            logger.error("Book with id="+id+" doesn`t exists");
            throw new RuntimeException("Book with id="+id+" doesn`t exists");
        }
    }

    @Override
    public void create(AuthorEntity authorEntity) {
        logger.info("Creating author "+authorEntity.toString());
        authorDao.create(authorEntity);
        logger.info("Successfully created new author");
    }

    @Override
    public AuthorEntity findById(int id) {
        logger.info("Finding author with id="+id);
        if(authorDao.isAuthorExists(id)){
            return authorDao.findById(id);
        }else {
            logger.error("Author with id="+id+" doesn`t exists");
            throw new RuntimeException("Author with id="+id+" doesn`t exists");
        }
    }

    @Override
    public List<AuthorEntity> findAll() {
        logger.info("Finding all authors");
        List<AuthorEntity> all = authorDao.findAll();
        for (AuthorEntity authorEntity : all) {
            logger.info(authorEntity.toString());
        }
        return all;
    }

    @Override
    public void update(AuthorEntity authorEntity) {
        if(authorDao.isAuthorExists(authorEntity.getId())){
            logger.info("Updating author "+authorDao.findById(authorEntity.getId()).toString());
            authorDao.update(authorEntity);
            logger.info("Author was successfully updated with values "+authorEntity.toString());
        }else{
            logger.info("Creating new author "+authorDao.findById(authorEntity.getId()).toString());
            authorDao.create(authorEntity);
            logger.info("New author was successfully created");
        }
    }

    @Override
    public void delete(int id) {
        logger.info("Deleting author with id="+id);
        if(authorDao.isAuthorExists(id)){
            authorDao.delete(id);
            logger.info("Author with id="+id+" was successfully deleted");
        }else{
            logger.error("Author with id="+id+" doesn`t exists");
            throw new RuntimeException("Author with id="+id+" doesn`t exists");
        }
    }
}
