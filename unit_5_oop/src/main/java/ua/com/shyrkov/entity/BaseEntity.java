package ua.com.shyrkov.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseEntity {

    @NonNull
    public int id;
}
