package ua.com.shyrkov.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Person implements Comparable<Person> {

    private String name;
    private int age;

    @Override
    public int compareTo(Person o) {
        int cmp = this.getName().compareTo(o.getName());
        if (cmp == 0) {
            return this.getAge() > o.getAge() ? 1 : 0;
        }
        return cmp;
    }

    @Override
    public String toString() {
        return name+", "+age;
    }
}
