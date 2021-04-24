package ua.com.shyrkov.service.io.csv.format;

import ua.com.shyrkov.entity.impl.AuthorEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorConverter {

    public static AuthorEntity csvToEntity(String[] csvInfo){
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(Integer.parseInt(csvInfo[0]));
        authorEntity.setFirstName(csvInfo[1]);
        authorEntity.setLastName(csvInfo[2]);
        authorEntity.setIsActive(csvInfo[3].equalsIgnoreCase("active"));

        List<Integer> bookIds = new ArrayList<>();
        if(!csvInfo[4].equalsIgnoreCase("[]")) {
            String[] split = csvInfo[4].substring(1, csvInfo[4].length() - 1).split(", ");
            Arrays.stream(split).forEach(s -> bookIds.add(Integer.parseInt(s)));
        }
        authorEntity.setBookIds(bookIds);
        return authorEntity;
    }
}
