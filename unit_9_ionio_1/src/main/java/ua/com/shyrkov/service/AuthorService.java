package ua.com.shyrkov.service;

import ua.com.shyrkov.entity.impl.AuthorEntity;

import java.util.List;

public interface AuthorService extends CommonService<AuthorEntity> {

    List<AuthorEntity> findAuthorsByBookId(int id);
}
