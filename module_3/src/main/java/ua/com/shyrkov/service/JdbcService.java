package ua.com.shyrkov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dao.JdbcTransactionDao;
import ua.com.shyrkov.dto.TransactionInfoDto;
import ua.com.shyrkov.dto.TransactionsReportDto;
import ua.com.shyrkov.entity.AccountEntity;
import ua.com.shyrkov.entity.UserEntity;
import ua.com.shyrkov.exception.DataLayerException;

import java.sql.Connection;
import java.time.Instant;
import java.util.List;

public class JdbcService {

    private final Logger logger = LoggerFactory.getLogger(JdbcService.class);
    private final JdbcTransactionDao transactionDao;

    public JdbcService(Connection connection) {
        this.transactionDao = new JdbcTransactionDao(connection);
    }

    public TransactionsReportDto getTransactionsReportInPeriod(Long accountId, Instant from, Instant to) {
        TransactionsReportDto report = new TransactionsReportDto();
        try {
            logger.info("Finding transactions in period");
            List<TransactionInfoDto> transactionsInPeriod = transactionDao
                    .findTransactionsInfoInPeriod(accountId, from, to);
            report.setTransactions(transactionsInPeriod);
            double totalIncome = 0.0;
            double totalExpense = 0.0;
            for (TransactionInfoDto transactionDto : transactionsInPeriod) {
                if (transactionDto.getTransactionType().equals("Income"))
                    totalIncome += transactionDto.getAmount();
                else totalExpense += transactionDto.getAmount();
            }
            report.setTotalIncome(totalIncome);
            report.setBalance(totalIncome - totalExpense);
            return report;
        } catch (DataLayerException e) {
            logger.error("Error was occurred during creation transactions report: " + e.getMessage());
            return null;
        }
    }

    public UserEntity findUserByEmail(String email) {
        try {
            logger.info("Finding user by email = " + email);
            return transactionDao.findUserByEmail(email);
        } catch (DataLayerException e) {
            logger.error("Error was occurred during finding user: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<AccountEntity> findAccountsByUser(UserEntity userEntity){
        try {
            logger.info("Finding accounts by user with id = " + userEntity.getId());
            return transactionDao.findAccountsByUser(userEntity);
        } catch (DataLayerException e) {
            logger.error("Error was occurred during finding user: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
