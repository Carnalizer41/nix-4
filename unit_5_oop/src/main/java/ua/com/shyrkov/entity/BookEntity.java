package ua.com.shyrkov.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookEntity extends BaseEntity{

    @NonNull
    private String name;
    private List<Integer> authorsIds;

    @Override
    public String toString() {
        return "\""+name+"\"; id="+id;
    }
}
