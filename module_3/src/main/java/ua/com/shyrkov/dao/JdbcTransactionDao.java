package ua.com.shyrkov.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dto.TransactionInfoDto;
import ua.com.shyrkov.entity.AccountEntity;
import ua.com.shyrkov.entity.UserEntity;
import ua.com.shyrkov.exception.DataLayerException;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDao {

    private final Connection connection;
    private final Logger logger = LoggerFactory.getLogger(JdbcTransactionDao.class);

    public JdbcTransactionDao(Connection connection) {
        this.connection = connection;
    }

    public List<TransactionInfoDto> findTransactionsInfoInPeriod(Long accountId, Instant from, Instant to)
            throws DataLayerException {
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT t.id, t.date, t.amount, t.description, ct.name as ctName,  c.name as cName " +
                                     "FROM transactions t " +
                                     "inner join transaction_categories tc on tc.transaction_id = t.id " +
                                     "inner join categories c on tc.category_id = c.id " +
                                     "inner join category_types ct on c.type_id = ct.id " +
                                     "where t.account_id = ? and t.date between ? and ?",
                             ResultSet.TYPE_SCROLL_SENSITIVE,
                             ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, accountId);
            statement.setTimestamp(2, Timestamp.from(from));
            statement.setTimestamp(3, Timestamp.from(to));
            ResultSet resultSet = statement.executeQuery();

            List<TransactionInfoDto> transactionInfoList = new ArrayList<>();
            while (resultSet.next()) {
                TransactionInfoDto transactionInfo = new TransactionInfoDto();
                transactionInfo.setId(resultSet.getLong("id"));
                transactionInfo.setTimestamp(resultSet.getTimestamp("date").toInstant());
                transactionInfo.setAmount(resultSet.getDouble("amount"));
                transactionInfo.setDescription(resultSet.getString("description"));
                transactionInfo.setTransactionType(resultSet.getString("ctName"));
                transactionInfo.getCategories().add(resultSet.getString("cName"));
                while (resultSet.next()) {
                    if (resultSet.getLong("id") == transactionInfo.getId()) {
                        transactionInfo.getCategories().add(resultSet.getString("cName"));
                    } else {
                        resultSet.previous();
                        break;
                    }
                }
                transactionInfoList.add(transactionInfo);
            }
            if (transactionInfoList.isEmpty())
                throw new DataLayerException("There are no transactions in this period");
            return transactionInfoList;

        } catch (Exception e) {
            logger.error("Can`t fetch transactions");
            throw new DataLayerException("Can`t fetch transactions: " + e.getMessage());
        }
    }

    public UserEntity findUserByEmail(String email) throws DataLayerException {
        try (PreparedStatement statement = connection.prepareStatement("select * from users u " +
                                                                               "where u.email = ?")) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(resultSet.getLong("id"));
                userEntity.setEmail(resultSet.getString("email"));
                userEntity.setPhone(resultSet.getString("phone"));
                return userEntity;
            } else
                throw new DataLayerException("There is no user with email = " + email);
        } catch (SQLException e) {
            logger.error("Can`t find user with email = " + email);
            throw new DataLayerException("Can`t find user by email: " + e.getMessage());
        }
    }

    public List<AccountEntity> findAccountsByUser(UserEntity userEntity) throws DataLayerException {
        List<AccountEntity> accounts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from accounts a " +
                                                                               "where a.id = ?")) {
            statement.setLong(1, userEntity.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AccountEntity accountEntity = new AccountEntity();
                accountEntity.setId(resultSet.getLong("id"));
                accountEntity.setUserEntity(userEntity);
                accounts.add(accountEntity);
            }
        } catch (SQLException e) {
            logger.error("Can`t find accounts of user");
            throw new DataLayerException("Can`t find accounts of user");
        }
        return accounts;
    }

}
