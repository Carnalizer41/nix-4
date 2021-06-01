package ua.com.shyrkov;

import ua.com.shyrkov.dao.impl.LocationDao;
import ua.com.shyrkov.dao.impl.ProblemDao;
import ua.com.shyrkov.dao.impl.RouteDao;
import ua.com.shyrkov.dao.impl.SolutionDao;
import ua.com.shyrkov.db.ConnectionProvider;
import ua.com.shyrkov.entity.Problem;
import ua.com.shyrkov.entity.Solution;
import ua.com.shyrkov.service.ShortPathFinder;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try (Connection connection = ConnectionProvider.getConnection()){
            LocationDao locationDao = new LocationDao(connection);
            RouteDao routeDao = new RouteDao(connection);
            ProblemDao problemDao = new ProblemDao(connection);
            SolutionDao solutionDao = new SolutionDao(connection);

            ShortPathFinder pathFinder = new ShortPathFinder(locationDao.findAll(), routeDao.findAll());
            for (Problem problem : problemDao.findAll()) {
                Solution solution = new Solution(problem.getId(),
                                                 pathFinder.findShortestPath(problem.getFromId(), problem.getToId()));
                solutionDao.update(solution);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
