package ua.com.shyrkov.task1;

import ua.com.shyrkov.task1.service.DateParser;
import ua.com.shyrkov.service.io.FileReader;
import ua.com.shyrkov.service.io.FileWriter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DateParserRunner {

    private static final Logger logger = Logger.getLogger(DateParserRunner.class);

    public static void run(){
        logger.info("Reading input.txt file");
        FileReader reader = new FileReader("src/main/java/ua/com/shyrkov/task1/db/input.txt");
        List<String> strings = reader.readFile();
        DateParser parser = new DateParser();
        List<String> res = new ArrayList<>();
        logger.info("Parsing strings into custom format");
        for (String string : strings) {
            res.add(parser.parse(string));
        }
        logger.info("Writing results into output.txt file");
        FileWriter writer = new FileWriter("src/main/java/ua/com/shyrkov/task1/db/output.txt");
        writer.writeAll(res);
    }
}
