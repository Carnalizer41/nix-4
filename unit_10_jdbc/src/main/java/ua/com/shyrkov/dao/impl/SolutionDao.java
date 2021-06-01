package ua.com.shyrkov.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.shyrkov.dao.CommonDao;
import ua.com.shyrkov.entity.Solution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolutionDao implements CommonDao<Solution> {

    private Connection connection;
    private static final Logger log = LoggerFactory.getLogger(SolutionDao.class);

    public SolutionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Solution> findAll() {
        List<Solution> solutions = new ArrayList<>();
        log.info("Finding all from solutions");
        try (PreparedStatement statement = connection.prepareStatement("select * from solutions")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                solutions.add(new Solution(resultSet.getInt("problem_id"),
                                           resultSet.getInt("cost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solutions;
    }

    public Solution findById(int id) {
        log.info("Finding solution for problem with id = " + id);
        Solution solution = null;
        try (PreparedStatement statement = connection
                .prepareStatement("select * from solutions where problem_id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                solution = new Solution(id, null);
                if (resultSet.getString("cost") != null)
                    return new Solution(resultSet.getInt("problem_id"),
                                        resultSet.getInt("cost"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solution;
    }

    @Override
    public void update(Solution entity) {
        if (findById(entity.getProblemId()) != null) {
            log.info("Updating solution for problem with id = "+entity.getProblemId());
            try (PreparedStatement statement = connection
                    .prepareStatement("update solutions set cost = ? where problem_id = ?")) {
                statement.setInt(1, entity.getCost());
                statement.setInt(2, entity.getProblemId());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            log.info("Saving solution for problem with id = "+entity.getProblemId());
            try (PreparedStatement statement = connection
                    .prepareStatement("insert into solutions(problem_id, cost) values(?, ?)")) {
                statement.setInt(1, entity.getProblemId());
                statement.setInt(2, entity.getCost());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
