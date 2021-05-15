package ua.com.shyrkov.service.io;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import ua.com.shyrkov.task1.DateParserRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private File file;
    private String filePath;
    private static final Logger logger = Logger.getLogger(DateParserRunner.class);

    public FileReader(String filePath){
        this.filePath = filePath;
        file = new File(this.filePath);
    }

    @SneakyThrows
    public List<String> readFile() {
        List<String> lines = new ArrayList<>();
        checkFile();
        BufferedReader reader = new BufferedReader(
                new java.io.FileReader(filePath));
        String line;
        logger.info("Reading line by line");
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private void checkFile() {
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
