package ua.com.shyrkov.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dao.CommonDao;
import ua.com.shyrkov.entity.Problem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDao implements CommonDao<Problem> {

    private Connection connection;
    private static final Logger log = LoggerFactory.getLogger(ProblemDao.class);

    public ProblemDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Problem> findAll() {
        List<Problem> problems = new ArrayList<>();
        log.info("Finding all from problems");
        try (PreparedStatement statement = connection.prepareStatement("select * from problems")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                problems.add(new Problem(resultSet.getInt("id"),
                                         resultSet.getInt("from_id"),
                                         resultSet.getInt("to_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return problems;
    }

    @Override
    public void update(Problem entity) {

    }
}
