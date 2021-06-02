package ua.com.shyrkov.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ua.com.shyrkov.entity.CsvTable;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public static CsvTable parseCsv(InputStream input){
        List<List<String>> data = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(input))){
            List<String[]> temp = reader.readAll();
            for (int i = 0; i < temp.size(); i++) {
                data.add(new ArrayList<>());
                for (int j = 0; j < temp.get(i).length; j++) {
                    data.get(i).add(temp.get(i)[j]);
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return new CsvTable(data);
    }
}
