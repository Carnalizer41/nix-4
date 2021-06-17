package ua.com.shyrkov.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dto.TransactionDto;
import ua.com.shyrkov.dto.TransactionsReportDto;
import ua.com.shyrkov.entity.AccountEntity;
import ua.com.shyrkov.entity.CategoryEntity;
import ua.com.shyrkov.entity.CategoryTypeEntity;
import ua.com.shyrkov.entity.UserEntity;
import ua.com.shyrkov.exception.DataLayerException;
import ua.com.shyrkov.exception.ValidationException;
import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.service.ConsoleHelperService;
import ua.com.shyrkov.service.JdbcService;
import ua.com.shyrkov.service.JpaService;
import ua.com.shyrkov.util.CsvWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private String csvFilePath = "src/main/resources/reports/";
    private static final ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();

    public void run(String[] args) {
        String email;
        String username;
        String password;
        try {
            email = args[0];
            username = args[1];
            password = args[2];
        } catch (IndexOutOfBoundsException e) {
            logger.error("Wrong input credentials");
            throw new RuntimeException("Input arguments are not valid: " + e.getMessage());
        }
        consoleHelper.printMessage("Select operation: \n" +
                                           "1: Add new transaction\n" +
                                           "2: Get account statement\n" +
                                           "0: Exit");
        int operationChoice;
        while (true) {
            try {
                operationChoice = consoleHelper.readIntegerFromConsole();
                switch (operationChoice) {
                    case 1: {
                        addTransaction(email, username, password);
                        break;
                    }
                    case 2: {
                        getTransactionReport(email, username, password, csvFilePath);
                        break;
                    }
                    case 0:
                        System.exit(0);
                    default:
                        consoleHelper.printMessage("Wrong input! Try again");

                }
                consoleHelper.printMessage("Select operation: \n" +
                                                   "1: Add new transaction\n" +
                                                   "2: Get account statement\n" +
                                                   "0: Exit");

            } catch (NumberFormatException e) {
                consoleHelper.printMessage("Wrong input! Try again");
            } catch (Exception e) {
                logger.error("Error was occurred: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private void getTransactionReport(String email, String username, String password, String filePath) {
        try (Connection connection = getConnection(username, password)) {
            JdbcService service = new JdbcService(connection);
            UserEntity userByEmail = service.findUserByEmail(email);
            long accountId;
            while (true) {
                consoleHelper.printMessage("Enter account id to find transactions:");
                try {
                    accountId = consoleHelper.readIntegerFromConsole();
                    boolean isAccountExists = false;
                    for (AccountEntity accountEntity : service.findAccountsByUser(userByEmail)) {
                        if (accountId == accountEntity.getId()) {
                            isAccountExists = true;
                            break;
                        }
                    }
                    if (!isAccountExists) {
                        logger.error("There is no such account with id = " + accountId);
                        throw new DataLayerException("There is no such account");
                    }
                    break;
                } catch (NumberFormatException | DataLayerException e) {
                    consoleHelper.printMessage("Wrong account id input! Try again");
                }
            }
            Instant startDate;
            Instant endDate;
            while (true) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    consoleHelper.printMessage("Enter start date in format yyyy-mm-dd");
                    String startStringDate = consoleHelper.readStringFromConsole();
                    startDate = sdf.parse(startStringDate).toInstant();
                    consoleHelper.printMessage("Enter end date in format yyyy-mm-dd");
                    String endStringDate = consoleHelper.readStringFromConsole();
                    endDate = sdf.parse(endStringDate).toInstant();
                    break;
                } catch (ParseException e) {
                    consoleHelper.printMessage("Wrong date input! Try again");
                }
            }
            TransactionsReportDto report = service.getTransactionsReportInPeriod(accountId, startDate, endDate);
            if (report == null)
                throw new DataLayerException("Can`t create transactions report");
            filePath += "transactionReport_"+Instant.now().toString().replaceAll(":", "-")+".csv";
            checkFile(new File(filePath));
            logger.info("Saving transactions report");
            CsvWriter.writeReportToCsv(filePath, report);
        } catch (SQLException e) {
            logger.error("JDBC connection error" + e.getMessage());
        } catch (DataLayerException e) {
            logger.error("Error was occurred during creation transactions report: " + e.getMessage());
        }
    }

    private void checkFile(File file) {
        if (!file.exists()) {
            try {
                logger.info("Creating new .csv file in db");
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Can`t create new .csv file");
                throw new RuntimeException("Can`t create new .csv file");
            }
        }
    }

    private Connection getConnection(String username, String password) {
        logger.info("Connecting to database...");
        Properties properties = new Properties();
        try (InputStream inputStream = Controller.class.getResourceAsStream("/jdbc.properties")) {
            properties.load(inputStream);
            properties.setProperty("user", username);
            properties.setProperty("password", password);
            return DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (IOException | SQLException e) {
            logger.error("JDBC connection error" + e.getMessage());
            throw new RuntimeException("JDBC connection error" + e.getMessage());
        }
    }

    private void addTransaction(String email, String username, String password) {
        logger.info("Configuring connection to database...");
        Configuration configuration = new Configuration().configure();
        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);
        logger.info("Opening session...");
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            TransactionDto transactionDto = new TransactionDto();
            JpaService service = new JpaService(session);
            UserEntity userByEmail = service.findUserByEmail(email);

            consoleHelper.printMessage("Enter account id to operate with: ");
            int accountId = consoleHelper.readIntegerFromConsole();
            for (AccountEntity userAccount : service.findUserAccounts(userByEmail.getId())) {
                if (userAccount.getId() == accountId) {
                    transactionDto.setAccountEntity(userAccount);
                    break;
                }
            }
            Instant now = Instant.now();
            transactionDto.setTimestamp(now);
            consoleHelper.printMessage(now.toString());
            consoleHelper.printMessage("Choose transaction type:\n" +
                                               "1: Income\n" +
                                               "2: Expense");
            CategoryTypeEntity categoryType;
            while (true) {
                try {
                    Long typeId = (long) consoleHelper.readIntegerFromConsole();
                    categoryType = service.findCategoryTypeById(typeId);
                    break;
                } catch (NumberFormatException e) {
                    consoleHelper.printMessage("Wrong input! Try again");
                }
            }
            consoleHelper.printMessage("Enter operation money amount: ");
            while (true) {
                try {
                    double amount = consoleHelper.readDoubleFromConsole();
                    transactionDto.setAmount(amount);
                    break;
                } catch (NumberFormatException e) {
                    consoleHelper.printMessage("Wrong input! Try again");
                }
            }
            while (true) {
                consoleHelper.printMessage("Enter amount of categories for this transaction:");
                try {
                    int categoriesAmount = consoleHelper.readIntegerFromConsole();
                    if (categoriesAmount <= 0)
                        throw new ValidationException("Categories amount can`t be less than 1");
                    List<CategoryEntity> categories = new ArrayList<>(categoriesAmount);
                    consoleHelper.printMessage("Choose category:");
                    for (CategoryEntity categoryEntity : service.findAllCategoriesByType(categoryType)) {
                        consoleHelper.printMessage(categoryEntity.getName());
                    }
                    for (int i = 0; i < categoriesAmount; i++) {
                        consoleHelper.printMessage("Enter category name:");
                        String categoryName = consoleHelper.readStringFromConsole();
                        categories.add(service.findCategoryByName(categoryName));
                    }
                    for (CategoryEntity category : categories) {
                        consoleHelper.printMessage(category.toString());
                    }
                    transactionDto.setCategoryEntities(categories);
                    break;
                } catch (Exception e) {
                    consoleHelper.printMessage("Wrong input! Try again");
                }
            }
            consoleHelper.printMessage("Please, write description for transaction");
            transactionDto.setDescription(consoleHelper.readStringFromConsole());
            service.createTransaction(transactionDto);
        }
    }


}
