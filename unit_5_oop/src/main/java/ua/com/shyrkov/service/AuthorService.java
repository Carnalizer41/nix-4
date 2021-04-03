package ua.com.shyrkov.service;

import ua.com.shyrkov.entity.AuthorEntity;

import java.util.List;

public interface AuthorService extends CommonService<AuthorEntity> {

    List<AuthorEntity> findAuthorsByBook(int id);
}
