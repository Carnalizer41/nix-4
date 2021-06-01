package ua.com.shyrkov.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dao.CommonDao;
import ua.com.shyrkov.entity.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDao implements CommonDao<Location> {

    private Connection connection;
    private static final Logger log = LoggerFactory.getLogger(LocationDao.class);

    public LocationDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        log.info("Finding all from locations");
        try (PreparedStatement statement = connection.prepareStatement("select * from locations")){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                locations.add(new Location(resultSet.getInt("id"), resultSet.getString("name")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    @Override
    public void update(Location entity) {

    }
}
