package ua.com.shyrkov.task1.service.io;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import ua.com.shyrkov.task1.DateParserRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private static final File file = new File("src/main/java/ua/com/shyrkov/task1/db/input.txt");
    private static final Logger logger = Logger.getLogger(DateParserRunner.class);

    @SneakyThrows
    public static List<String> readFile() {
        List<String> lines = new ArrayList<>();
        checkFile();
        BufferedReader reader = new BufferedReader(
                new java.io.FileReader("src/main/java/ua/com/shyrkov/task1/db/input.txt"));
        String line;
        logger.info("Reading line by line");
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private static void checkFile() {
        logger.info("Checking input file existence");
        if (!file.exists()) {
            try {
                logger.info("Creating new input file");
                file.createNewFile();
                logger.info("Input file created! \nFill it with dates info and re-run app");
            } catch (IOException e) {
                logger.error("Can not create new input file");
                e.printStackTrace();
            }
        }
    }
}
