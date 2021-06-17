package ua.com.shyrkov.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dto.TransactionDto;
import ua.com.shyrkov.entity.*;
import ua.com.shyrkov.exception.DataLayerException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDao {

    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(TransactionDao.class);

    public TransactionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(TransactionDto transactionDto) throws DataLayerException {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            TransactionEntity entity = new TransactionEntity();
            entity.setTimestamp(transactionDto.getTimestamp());
            entity.setAmount(transactionDto.getAmount());
            entity.setDescription(transactionDto.getDescription());
            entity.setAccountEntity(transactionDto.getAccountEntity());
            entity.setCategoryEntities(transactionDto.getCategoryEntities());
            entityManager.persist(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            logger.error("Can`t save new transaction", e);
            transaction.rollback();
            throw new DataLayerException(e);
        }
    }



    public List<AccountEntity> findUserAccounts(Long userId) throws DataLayerException {
        TypedQuery<AccountEntity> query = entityManager.createQuery("select a from AccountEntity a " +
                                                                            "where a.userEntity.id = :id",
                                                                    AccountEntity.class);
        query.setParameter("id", userId);
        if (query.getResultList().isEmpty())
            throw new DataLayerException("There are no accounts of user with id = " + userId);
        return query.getResultList();
    }

    public UserEntity findUserByEmail(String email) throws DataLayerException {
        TypedQuery<UserEntity> query = entityManager.createQuery("select u from UserEntity u " +
                                                                         "where u.email = :email",
                                                                 UserEntity.class);
        query.setMaxResults(1);
        query.setParameter("email", email);
        if (query.getResultList().isEmpty())
            throw new DataLayerException("There is no user with email = " + email);
        return query.getSingleResult();
    }

    public CategoryTypeEntity findCategoryTypeById(Long id) throws DataLayerException {
        TypedQuery<CategoryTypeEntity> query = entityManager.createQuery("select c from CategoryTypeEntity c " +
                                                                                 "where c.id = :id",
                                                                         CategoryTypeEntity.class);
        query.setMaxResults(1);
        query.setParameter("id", id);
        if (query.getResultList().isEmpty())
            throw new DataLayerException("There is no type with id = " + id);
        return query.getSingleResult();
    }

    public List<CategoryEntity> findAllCategoriesByType(CategoryTypeEntity type) throws DataLayerException {
        TypedQuery<CategoryEntity> query = entityManager.createQuery("select c from CategoryEntity c " +
                                                                             "where c.categoryTypeEntity.id = :id",
                                                                     CategoryEntity.class);
        query.setParameter("id", type.getId());
        if (query.getResultList().isEmpty())
            throw new DataLayerException("There are no categories with type = " + type.getName());
        return query.getResultList();
    }

    public CategoryEntity findCategoryByName(String name) throws DataLayerException {
        TypedQuery<CategoryEntity> query = entityManager.createQuery("select c from CategoryEntity c " +
                                                                             "where c.name = :name",
                                                                     CategoryEntity.class);
        query.setMaxResults(1);
        query.setParameter("name", name);
        if (query.getResultList().isEmpty())
            throw new DataLayerException("There is no category with name = " + name);
        return query.getSingleResult();
    }



}
