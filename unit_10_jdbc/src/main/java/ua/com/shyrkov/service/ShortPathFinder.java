package ua.com.shyrkov.service;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import ua.com.shyrkov.entity.Location;
import ua.com.shyrkov.entity.Route;

import java.util.List;

public class ShortPathFinder {

    private SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(
            DefaultWeightedEdge.class);
    private List<Location> vertices;
    private List<Route> edges;

    public ShortPathFinder(List<Location> vertices, List<Route> edges) {
        this.vertices = vertices;
        this.edges = edges;
        addVertices();
        addEdges();
    }

    private void addVertices() {
        for (Location vertex : vertices) {
            graph.addVertex(vertex.getId());
        }
    }

    private void addEdges() {
        for (Route edge : edges) {
            DefaultWeightedEdge defaultWeightedEdge = graph.addEdge(edge.getFromId(), edge.getToId());
            if (defaultWeightedEdge != null)
                graph.setEdgeWeight(defaultWeightedEdge, edge.getCost());
        }
    }

    public int findShortestPath(int from, int to) {
        DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        return (int) dijkstraShortestPath.getPath(from, to).getWeight();
    }
}
