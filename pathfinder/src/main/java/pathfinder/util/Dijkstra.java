package pathfinder.util;

import pathfinder.graphadt.Edge;
import pathfinder.graphadt.Graph;
import pathfinder.graphadt.Node;

import java.util.HashMap;

public class Dijkstra {

    public static Pair<HashMap<Node, Node>, HashMap<Node, Double>> shortestPathFromSource(Graph g, Node s) {
        // Create and initialize a HashMap to store the shortest path
        HashMap<Node, Node> path = new HashMap<>();
        for (Node n : g.getNodes()) {path.put(n, null);}
        // Initialize path for start node
        path.put(s, s);
        // Create and initialize a HashMap to store the cost
        HashMap<Node, Double> cost = new HashMap<>();
        for (Node n : g.getNodes()) {cost.put(n, Double.MAX_VALUE);}
        // Initialize cost for start node
        cost.put(s, 0d);

        // Create a minimum priority queue that holds node s with priority 0
        MinPriorityQueue<Node> mpq = new MinPriorityQueue<>();
        mpq.enqueue(s, 0d);

        // While the minimum priority queue is not empty run Dijkstra's algorithm
        while (mpq.size() > 0) {
            Node m = mpq.dequeue();
            for(Edge e : g.getNodeEdges(m)) {
                Node n = e.getN2();
                if (cost.get(m) + e.getWeight() < cost.get(n)){
                    path.put(n, m);
                    cost.put(n, cost.get(m) + e.getWeight());
                    mpq.enqueue(n, cost.get(n));
                }
            }
        }

        return new Pair<>(path, cost);
    }

}
