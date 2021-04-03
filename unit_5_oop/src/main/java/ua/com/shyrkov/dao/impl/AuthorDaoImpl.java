package ua.com.shyrkov.dao.impl;

import ua.com.shyrkov.dao.AuthorDao;
import ua.com.shyrkov.db.LibraryDb;
import ua.com.shyrkov.entity.AuthorEntity;

import java.util.List;

public class AuthorDaoImpl implements AuthorDao {

    @Override
    public void create(AuthorEntity authorEntity) {
        LibraryDb.getInstance().createAuthor(authorEntity);
    }

    @Override
    public AuthorEntity findById(int id) {
        return LibraryDb.getInstance().findAuthorById(id);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return LibraryDb.getInstance().findAllAuthors();
    }

    @Override
    public void update(AuthorEntity authorEntity) {
        LibraryDb.getInstance().updateAuthor(authorEntity);
    }

    @Override
    public void delete(int id) {
        LibraryDb.getInstance().deleteAuthor(id);
    }

    @Override
    public List<AuthorEntity> findAuthorsByBookId(int id) {
        return LibraryDb.getInstance().findAuthorsByBookId(id);
    }

    @Override
    public boolean isAuthorExists(int id) {
        return LibraryDb.getInstance().isAuthorExists(id);
    }
}
