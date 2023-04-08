package pathfinder;

import pathfinder.graphadt.Edge;
import pathfinder.graphadt.Graph;
import pathfinder.graphadt.Node;
import pathfinder.util.MinPriorityQueue;
import pathfinder.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShortestPath implements PathFinder{

    @Override
    public List<Node> findPath(Graph g, Node n1, Node n2) {
        // If either node is not in the graph return null
        if (!g.containsNode(n1) || !g.containsNode(n2)) return null;

        // Run Dijkstra's algorithm to find the shortest paths from the source node n1
        HashMap<Node, Node> path = dijkstra(g, n1).first();

        // Create an array list to store the nodes in the path from n1 to n2
        List<Node> shortestPath = new ArrayList<>();

        // Add n2 to the shortest path
        Node n = n2;
        shortestPath.add(n);

        // For each node get the node it links to and add it to the shortest path
        // If the start node n1 is reached break out of the loop
        while ( n != null){
            if (n.equals(n1)) {
                break;
            }
            n = path.get(n);
            shortestPath.add(0, n);
        }

        return shortestPath;
    }

    private static Pair<HashMap<Node, Node>, HashMap<Node, Double>> dijkstra(Graph g, Node s) {
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
