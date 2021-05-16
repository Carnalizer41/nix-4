package ua.com.shyrkov.task3;

import org.apache.log4j.Logger;
import ua.com.shyrkov.service.io.FileReader;
import ua.com.shyrkov.service.io.FileWriter;
import ua.com.shyrkov.task1.DateParserRunner;
import ua.com.shyrkov.task3.service.DijkstraGraph;
import ua.com.shyrkov.task3.service.entity.Node;
import ua.com.shyrkov.task3.service.entity.Town;

import java.util.*;

public class GraphRunner {

    private static final Logger logger = Logger.getLogger(DateParserRunner.class);

    public static void run() {
        logger.info("============== Task 3 ================");
        logger.info("Reading input.txt file");
        FileReader reader = new FileReader("src/main/java/ua/com/shyrkov/task3/io/input.txt");
        FileWriter writer = new FileWriter("src/main/java/ua/com/shyrkov/task3/io/output.txt");
        writer.cleanFile();
        List<String> input = reader.readFile();
        logger.info("Input:");
        for (String string : input) {
            logger.info(string);
        }
        List<Town> towns = new ArrayList<>();
        List<List<Node>> nodes = new ArrayList<>();

        int citiesAmount = Integer.parseInt(poll(input));
        for (int i = 1; i <= citiesAmount; i++) {
            Town town = new Town();
            town.setIndex(i);
            town.setName(poll(input));
            List<Node> neighbours = new ArrayList<>();
            int nAmount = Integer.parseInt(poll(input));
            for (int nIndex = 0; nIndex < nAmount; nIndex++) {
                String[] split = poll(input).split(" ");
                neighbours.add(new Node(Integer.parseInt(split[0]) - 1, Integer.parseInt(split[1])));
            }
            town.setNeighbours(neighbours);
            towns.add(town);
        }
        for (Town town : towns) {
            nodes.add(town.getNeighbours());
        }
        int searchWaysAmount = Integer.parseInt(poll(input));
        for (int i = 0; i < searchWaysAmount; i++) {
            String[] searchTowns = poll(input).split(" ");
            DijkstraGraph graph = new DijkstraGraph(citiesAmount);
            int firstTownIndex = 0, secondTownIndex = 0;
            for (Town town : towns) {
                if (town.getName().equals(searchTowns[0]))
                    firstTownIndex = town.getIndex();
                else if (town.getName().equals(searchTowns[1])) {
                    secondTownIndex = town.getIndex();
                    break;
                }
            }
            logger.info("Finding distance between " + searchTowns[0] + " and " + searchTowns[1]);
            graph.dijkstra(nodes, firstTownIndex - 1);
            int distance = graph.getDist()[secondTownIndex - 1];
            logger.info("Result distance is " + distance);
            logger.info("Writing result into output.txt file");
            writer.writeLine(String.valueOf(distance));
        }
    }

    private static String poll(List<String> list) {
        String s = list.get(0);
        list.remove(0);
        return s;
    }
}
