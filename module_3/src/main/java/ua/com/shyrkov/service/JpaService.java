package ua.com.shyrkov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dao.TransactionDao;
import ua.com.shyrkov.dto.TransactionDto;
import ua.com.shyrkov.dto.TransactionsReportDto;
import ua.com.shyrkov.entity.AccountEntity;
import ua.com.shyrkov.entity.CategoryEntity;
import ua.com.shyrkov.entity.CategoryTypeEntity;
import ua.com.shyrkov.entity.UserEntity;
import ua.com.shyrkov.exception.DataLayerException;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JpaService {

    private final TransactionDao transactionDao;
    private final Logger logger = LoggerFactory.getLogger(JpaService.class);

    public JpaService(EntityManager entityManager) {
        this.transactionDao = new TransactionDao(entityManager);
    }

    public void createTransaction(TransactionDto transactionDto) {
        try {
            logger.info("Saving new transaction");
            transactionDao.create(transactionDto);
        } catch (DataLayerException e) {
            logger.error("Error was occurred during saving new transaction: " + e.getMessage());
        }
    }

    public List<AccountEntity> findUserAccounts(Long userId){
        try {
            logger.info("Searching user`s accounts");
            return transactionDao.findUserAccounts(userId);
        }catch (DataLayerException e){
            logger.error("Error was occurred during finding user`s accounts: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public UserEntity findUserByEmail(String email){
        try {
            logger.info("Searching user by email");
            return transactionDao.findUserByEmail(email);
        }catch (DataLayerException e) {
            logger.error("Error was occurred during finding user: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public CategoryTypeEntity findCategoryTypeById(Long id){
        try {
            logger.info("Searching type by id");
            return transactionDao.findCategoryTypeById(id);
        }catch (DataLayerException e) {
            logger.error("Error was occurred during finding categories type: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<CategoryEntity> findAllCategoriesByType(CategoryTypeEntity type){
        try {
            logger.info("Searching categories");
            return transactionDao.findAllCategoriesByType(type);
        }catch (DataLayerException e) {
            logger.error("Error was occurred during finding categories: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public CategoryEntity findCategoryByName(String name){
        try {
            logger.info("Searching category: "+name);
            return transactionDao.findCategoryByName(name);
        }catch (DataLayerException e) {
            logger.error("Error was occurred during finding category: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
