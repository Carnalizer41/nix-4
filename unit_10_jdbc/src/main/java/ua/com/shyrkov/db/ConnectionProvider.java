package ua.com.shyrkov.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    private static final Logger log = LoggerFactory.getLogger(ConnectionProvider.class);

    public static Connection getConnection() throws SQLException {
        Properties props = loadProperties();
        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        log.info("Connecting to {}", url);

        Connection connection = DriverManager.getConnection(url, user, password);
        log.info("Opened connection to {}", url);
        return connection;
    }

    private static Properties loadProperties(){
        Properties props = new Properties();

        try(InputStream input = ConnectionProvider.class.getResourceAsStream("/jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }
}
