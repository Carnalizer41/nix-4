package ua.com.shyrkov.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorEntity extends BaseEntity {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName+" id="+id;
    }
}
