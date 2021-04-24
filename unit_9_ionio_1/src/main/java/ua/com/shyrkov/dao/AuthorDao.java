package ua.com.shyrkov.dao;

import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.entity.impl.BookEntity;

import java.util.List;

public interface AuthorDao extends CommonDao<AuthorEntity> {

    List<AuthorEntity> findByBookId(int bookId);
    boolean isAuthorExists(int id);
}
