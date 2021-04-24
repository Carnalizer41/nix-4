package ua.com.shyrkov.entity.impl;

import lombok.*;
import ua.com.shyrkov.entity.BaseEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookEntity extends BaseEntity {

    @NonNull
    private String title;
    @NonNull
    private List<Integer> authorIds;

    public BookEntity(@NonNull String title, @NonNull List<Integer> authorIds) {
        this.title = title;
        this.authorIds = authorIds;
    }

    public void addAuthorId(int id) {
        authorIds.add(id);
    }

    @Override
    public String toString() {
        return "{" + title + "}";
    }
}
