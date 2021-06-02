package ua.com.shyrkov.service;

import ua.com.shyrkov.annotation.CsvCell;
import ua.com.shyrkov.entity.CsvTable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CsvMapper {

    public static <T> List<T> initializeTable(CsvTable table, Class<T> tClass){
        List<T> data = new ArrayList<>();
        try {
            for (int i = 1; i < table.getTableData().size(); i++) {
                Constructor<T> constructor = tClass.getConstructor();
                T instance = constructor.newInstance();

                for (Field field : instance.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    CsvCell cellAnnotation = field.getAnnotation(CsvCell.class);
                    if(cellAnnotation!=null){
                        Class<?> fieldType = field.getType();

                        if(fieldType.equals(String.class))
                            field.set(instance, table.getCell(i, cellAnnotation.value()));
                        else if(fieldType.equals(Integer.class) || fieldType.equals(int.class))
                            field.set(instance, Integer.parseInt(table.getCell(i, cellAnnotation.value())));
                        else if(fieldType.equals(Double.class) || fieldType.equals(double.class))
                            field.set(instance, Double.parseDouble(table.getCell(i, cellAnnotation.value())));
                        else if(fieldType.equals(Boolean.class) || fieldType.equals(boolean.class))
                            field.set(instance, Boolean.parseBoolean(table.getCell(i, cellAnnotation.value())));
                        else if(fieldType.isEnum()){
                            for (Object en : fieldType.getEnumConstants()) {
                                if(String.valueOf(en).equalsIgnoreCase(table.getCell(i, cellAnnotation.value()))) {
                                    field.set(instance, en);
                                    break;
                                }
                            }
                        }
                    }
                }
                data.add(instance);
            }
            return data;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Initialization failed");
        }
    }

    public static CsvTable loadTableData(String csvPath){
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(csvPath)) {
            return CsvParser.parseCsv(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read csv file");
        }
    }
}
