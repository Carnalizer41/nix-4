package ua.com.shyrkov.entity.impl;

import lombok.*;
import ua.com.shyrkov.entity.BaseEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthorEntity extends BaseEntity {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private List<Integer> bookIds;

    public AuthorEntity(@NonNull String firstName, @NonNull String lastName,
                        @NonNull List<Integer> bookIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookIds = bookIds;
    }

    public void addBookId(int id) {
        bookIds.add(id);
    }

    @Override
    public String toString() {
        return "{" + firstName + " " + lastName + '}';
    }
}
