package ua.com.shyrkov.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dao.CommonDao;
import ua.com.shyrkov.entity.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDao implements CommonDao<Route> {

    private Connection connection;
    private static final Logger log = LoggerFactory.getLogger(RouteDao.class);

    public RouteDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Route> findAll() {
        List<Route> routes = new ArrayList<>();
        log.info("Finding all from routes");
        try (PreparedStatement statement = connection.prepareStatement("select * from routes")){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                routes.add(new Route(resultSet.getInt("id"),
                                     resultSet.getInt("from_id"),
                                     resultSet.getInt("to_id"),
                                     resultSet.getInt("cost")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    public void update(Route entity) {

    }
}
