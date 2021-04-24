package ua.com.shyrkov.service.io.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.apache.log4j.Logger;
import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.entity.impl.BookEntity;
import ua.com.shyrkov.service.io.csv.format.BookConverter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookCsvIo {

    private static String bookFilePath;
    private static final String[] bookHeader = new String[]{"id", "title", "active_status", "authors"};
    private static BookCsvIo instance;
    private static String[] bookInfo = new String[4];
    private static List<String[]> bookFileData;
    private static final Logger logger = Logger.getLogger(AuthorCsvIo.class);

    private BookCsvIo(String booksPath) {
        bookFilePath = booksPath;
        checkFile(new File(bookFilePath), bookHeader);
    }

    private BookCsvIo() {
        bookFilePath = "src/main/resources/db/books.csv";
        checkFile(new File(bookFilePath), bookHeader);
    }

    public void writeNew(BookEntity entity) {
        bookInfo[0] = entity.getId().toString();
        bookInfo[1] = entity.getTitle();
        bookInfo[2] = entity.getActiveStatus();
        bookInfo[3] = entity.getAuthorIds().toString();

        try (CSVWriter writer = new CSVWriter(new FileWriter(bookFilePath, true))) {
            writer.writeNext(bookInfo);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }

    public void writeAll(List<BookEntity> entities) {
        bookFileData = new ArrayList<>();
        for (BookEntity entity : entities) {
            String[] info = new String[4];
            info[0] = entity.getId().toString();
            info[1] = entity.getTitle();
            info[2] = entity.getActiveStatus();
            info[3] = entity.getAuthorIds().toString();
            bookFileData.add(info);
        }
        writeHeader(new File(bookFilePath), bookHeader);
        try (CSVWriter writer = new CSVWriter(new FileWriter(bookFilePath, true))) {
            writer.writeAll(bookFileData);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }

    }

    public List<BookEntity> read() {
        List<BookEntity> bookEntities = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(bookFilePath))) {
            bookFileData = reader.readAll();
            bookFileData.remove(0);
        } catch (CsvException e) {
            logger.error("Can`t read data from books.csv file:\n" + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }

        if (!bookFileData.isEmpty())
            for (String[] bookInfo : bookFileData) {
                bookEntities.add(BookConverter.csvToEntity(bookInfo));
            }
        return bookEntities;
    }

    private void checkFile(File file, String[] header) {
        if (!file.exists()) {
            try {
                logger.info("Creating new .csv file in db");
                file.createNewFile();
                writeHeader(file, header);
            } catch (IOException e) {
                logger.error("Can`t create new .csv file. Wrong filepath");
                throw new RuntimeException("Can`t create new .csv file. Wrong filepath");
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

    public static BookCsvIo getInstance() {
        if (instance == null) {
            instance = new BookCsvIo();
        }
        return instance;
    }

    public static BookCsvIo getInstance(String booksPath) {
        if (instance == null) {
            instance = new BookCsvIo(booksPath);
        }
        return instance;
    }
}
