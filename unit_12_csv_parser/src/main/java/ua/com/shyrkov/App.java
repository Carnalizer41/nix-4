package ua.com.shyrkov;

import ua.com.shyrkov.entity.CsvTable;
import ua.com.shyrkov.entity.SongInfo;
import ua.com.shyrkov.service.CsvMapper;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CsvTable csvTable = CsvMapper.loadTableData("table.csv");
        List<SongInfo> songInfos = CsvMapper.initializeTable(csvTable, SongInfo.class);
        for (SongInfo songInfo : songInfos) {
            System.out.println(songInfo);
        }
    }
}
