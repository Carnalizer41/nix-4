package ua.com.shyrkov.task3.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Town {

    private int index;
    private String name;
    private List<Node> neighbours;

}
