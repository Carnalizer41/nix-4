package ua.com.shyrkov.task3.service;

import ua.com.shyrkov.task3.service.entity.Node;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraGraph {

    private int[] dist;
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    private int V;
    List<List<Node>> adj;

    public DijkstraGraph(int V) {
        this.V = V;
        dist = new int[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
    }

    public void dijkstra(List<List<Node>> adj, int src) {
        this.adj = adj;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        pq.add(new Node(src, 0));

        dist[src] = 0;
        while (settled.size() != V) {

            int u = pq.remove().getNode();

            settled.add(u);

            e_Neighbours(u);
        }
    }

    private void e_Neighbours(int u) {
        int edgeDistance = -1;
        int newDistance = -1;

        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);

            if (!settled.contains(v.getNode())) {
                edgeDistance = v.getCost();
                newDistance = dist[u] + edgeDistance;

                if (newDistance < dist[v.getNode()])
                    dist[v.getNode()] = newDistance;

                pq.add(new Node(v.getNode(), dist[v.getNode()]));
            }
        }
    }

    public int[] getDist() {
        return dist;
    }
}
