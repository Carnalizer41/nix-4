package ua.com.shyrkov.service;

import ua.com.shyrkov.annotation.PropertyKey;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class PropertyHandler {

    public static <T> T initializeObject(Properties properties, Class<T> tClass){
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor();
            T objectInstance = constructor.newInstance();
            for (Field declaredField : tClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                PropertyKey key = declaredField.getAnnotation(PropertyKey.class);
                if(key!=null){
                    String property = properties.getProperty(key.value());
                    Class<?> fieldType = declaredField.getType();
                    if(fieldType.equals(String.class))
                        declaredField.set(objectInstance, property);
                    else if(fieldType.equals(Integer.class) || fieldType.equals(int.class))
                        declaredField.setInt(objectInstance, Integer.parseInt(property));
                    else if(fieldType.isEnum()){
                        for (Object en : fieldType.getEnumConstants()) {
                            if(String.valueOf(en).equalsIgnoreCase(property)) {
                                declaredField.set(objectInstance, en);
                                break;
                            }
                        }
                    }
                }
            }
            return objectInstance;
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Initialization failed");
        }
    }

    public static Properties loadProperties(String filePath){
        Properties properties = new Properties();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read properties file");
        }
        return properties;
    }
}
