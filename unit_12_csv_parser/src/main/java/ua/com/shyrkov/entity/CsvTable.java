package ua.com.shyrkov.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvTable {

    private Map<String, Integer> header = new HashMap<>();
    private List<List<String>> tableData;

    public CsvTable(){
        this.tableData = new ArrayList<>();
    }

    public CsvTable(List<List<String>> tableData) {
        this.tableData = tableData;
        for (int i = 0; i < tableData.get(0).size(); i++) {
            header.put(tableData.get(0).get(i), i);
        }
    }

    public List<List<String>> getTableData() {
        return tableData;
    }

    public String getCell(int rowIndex, int colIndex){
        return tableData.get(rowIndex).get(colIndex);
    }

    public String getCell(int rowIndex, String titleName) {
        return tableData.get(rowIndex).get(header.get(titleName));
    }

    public List<String> getHeaders(){
        return tableData.get(0);
    }


}
