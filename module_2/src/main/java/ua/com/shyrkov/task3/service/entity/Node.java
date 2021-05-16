package ua.com.shyrkov.task3.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Comparator<Node> {

    private int node;
    private int cost;

    @Override
    public int compare(Node node1, Node node2) {
        return Integer.compare(node1.cost, node2.cost);
    }
}
