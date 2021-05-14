package ua.com.shyrkov.task1.service.io;

import org.apache.log4j.Logger;
import ua.com.shyrkov.task1.DateParserRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriter {

    private static final File file = new File("src/main/java/ua/com/shyrkov/task1/db/output.txt");
    private static final Logger logger = Logger.getLogger(DateParserRunner.class);

    public static void write(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(
                new java.io.FileWriter("src/main/java/ua/com/shyrkov/task1/db/output.txt", true))) {
            cleanFile();
            for (String line : lines) {
                if (line != null) {
                    writer.append(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void cleanFile() {
        try {
            logger.info("Checking output file existence");
            if (!file.exists()) {
                logger.info("Creating new output file");
            }
            file.createNewFile();
            logger.info("Cleaning output file");
            new java.io.FileWriter(file, false).close();
        } catch (IOException e) {
            throw new RuntimeException("Can`t create new output file");
        }
    }
}
