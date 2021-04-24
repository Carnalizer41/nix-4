package ua.com.shyrkov.service.io.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.apache.log4j.Logger;
import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.service.io.csv.format.AuthorConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorCsvIo {

    private static String authorFilePath;
    private static final String[] authorHeader = new String[]{"id", "first_name", "last_name", "active_status", "books"};
    private static AuthorCsvIo instance;
    private static String[] authorInfo = new String[5];
    private static List<String[]> authorFileData;
    private static final Logger logger = Logger.getLogger(AuthorCsvIo.class);

    private AuthorCsvIo(String authorsPath) {
        authorFilePath = authorsPath;
        checkFile(new File(authorFilePath), authorHeader);
    }

    private AuthorCsvIo() {
        authorFilePath = "src/main/resources/db/authors.csv";
        checkFile(new File(authorFilePath), authorHeader);
    }

    public void writeNew(AuthorEntity entity) {
        authorInfo[0] = entity.getId().toString();
        authorInfo[1] = entity.getFirstName();
        authorInfo[2] = entity.getLastName();
        authorInfo[3] = entity.getActiveStatus();
        authorInfo[4] = entity.getBookIds().toString();

        try (CSVWriter writer = new CSVWriter(new FileWriter(authorFilePath, true))) {
            writer.writeNext(authorInfo);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }

    public void writeAll(List<AuthorEntity> entities) {
        authorFileData = new ArrayList<>();
        for (AuthorEntity authorEntity : entities) {
            String[] info = new String[5];
            info[0] = authorEntity.getId().toString();
            info[1] = authorEntity.getFirstName();
            info[2] = authorEntity.getLastName();
            info[3] = authorEntity.getActiveStatus();
            info[4] = authorEntity.getBookIds().toString();
            authorFileData.add(info);
        }
        writeHeader(new File(authorFilePath), authorHeader);
        try (CSVWriter writer = new CSVWriter(new FileWriter(authorFilePath, true))) {
            writer.writeAll(authorFileData);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }

    public List<AuthorEntity> read() {
        List<AuthorEntity> authorEntities = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(authorFilePath))) {
            authorFileData = reader.readAll();
            authorFileData.remove(0);
        } catch (CsvException e) {
            logger.error("Can`t read data from authors.csv file:\n" + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }

        if (!authorFileData.isEmpty())
            for (String[] authorInfo : authorFileData) {
                authorEntities.add(AuthorConverter.csvToEntity(authorInfo));
            }
        return authorEntities;
    }

    private void checkFile(File file, String[] header) {
        if (!file.exists()) {
            try {
                logger.info("Creating new .csv file in db");
                file.createNewFile();
                writeHeader(file, header);
            } catch (IOException e) {
                logger.error("Can`t create new .csv file");
                throw new RuntimeException("Can`t create new .csv file");
            }
        }
    }

    private void writeHeader(File file, String[] header) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            writer.writeNext(header);
        } catch (IOException e) {
            logger.error("Can`t write header");
            throw new RuntimeException("Can`t write header");
        }
    }

    public static AuthorCsvIo getInstance() {
        if (instance == null) {
            instance = new AuthorCsvIo();
        }
        return instance;
    }

    public static AuthorCsvIo getInstance(String authorsPath) {
        if (instance == null) {
            instance = new AuthorCsvIo(authorsPath);
        }
        return instance;
    }
}
