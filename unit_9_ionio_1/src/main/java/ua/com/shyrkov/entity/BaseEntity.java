package ua.com.shyrkov.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity {

    @NonNull
    protected Integer id;
    @NonNull
    protected Boolean isActive = true;

    public String getActiveStatus() {
        return isActive ? "Active" : "Inactive";
    }
}
