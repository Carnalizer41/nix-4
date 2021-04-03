package ua.com.shyrkov.dao;

import ua.com.shyrkov.entity.AuthorEntity;

import java.util.List;

public interface AuthorDao extends CommonDao<AuthorEntity>{

    List<AuthorEntity> findAuthorsByBookId(int id);
    boolean isAuthorExists(int id);
}
