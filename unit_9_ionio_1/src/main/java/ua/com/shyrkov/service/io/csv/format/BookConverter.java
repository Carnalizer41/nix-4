package ua.com.shyrkov.service.io.csv.format;

import ua.com.shyrkov.entity.impl.BookEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookConverter {

    public static BookEntity csvToEntity(String[] csvInfo){
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(Integer.parseInt(csvInfo[0]));
        bookEntity.setTitle(csvInfo[1]);
        bookEntity.setIsActive(csvInfo[2].equalsIgnoreCase("active"));

        List<Integer> authorIds = new ArrayList<>();
        if(!csvInfo[3].equalsIgnoreCase("[]")) {
            String[] split = csvInfo[3].substring(1, csvInfo[3].length() - 1).split(", ");
            Arrays.stream(split).forEach(s -> authorIds.add(Integer.parseInt(s)));
        }
        bookEntity.setAuthorIds(authorIds);
        return bookEntity;
    }
}
