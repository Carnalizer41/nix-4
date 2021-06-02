package ua.com.shyrkov.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.shyrkov.annotation.CsvCell;

@Getter
@Setter
@ToString
public class SongInfo {

    @CsvCell("Name")
    public String name;
    @CsvCell("Author")
    public String author;
    @CsvCell("Album")
    public String album;
    @CsvCell("Cost")
    public double cost;
    @CsvCell("Place in charts")
    public int chartPlace;
}
