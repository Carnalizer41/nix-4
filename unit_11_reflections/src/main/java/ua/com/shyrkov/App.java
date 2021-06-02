package ua.com.shyrkov;

import ua.com.shyrkov.entity.AppProperties;
import ua.com.shyrkov.service.PropertyHandler;

import java.util.Properties;

public class App {
    public static void main(String[] args) {
        Properties properties = PropertyHandler.loadProperties("app.properties");
        AppProperties appProperties = PropertyHandler.initializeObject(properties, AppProperties.class);
        System.out.println(appProperties);
    }
}
