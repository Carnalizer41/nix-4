package ua.com.shyrkov.task2;

import org.apache.log4j.Logger;
import ua.com.shyrkov.service.io.FileReader;
import ua.com.shyrkov.service.io.FileWriter;
import ua.com.shyrkov.task1.DateParserRunner;
import ua.com.shyrkov.task2.service.UniqueFinder;

import java.util.List;

public class UniqueFinderRunner {

    private static final Logger logger = Logger.getLogger(DateParserRunner.class);

    public static void run() {
        logger.info("============== Task 2 ================");
        logger.info("Reading input.txt file");
        FileReader reader = new FileReader("src/main/java/ua/com/shyrkov/task2/io/input.txt");
        List<String> strings = reader.readFile();
        logger.info("Input:");
        for (String string : strings) {
            logger.info(string);
        }
        logger.info("Finding first unique string");
        String uniqueString = UniqueFinder.findUniqueString(strings);
        logger.info("Result: " + uniqueString);
        logger.info("Writing result into output.txt file");
        FileWriter writer = new FileWriter("src/main/java/ua/com/shyrkov/task2/io/output.txt");
        writer.writeLine(uniqueString);
    }
}
