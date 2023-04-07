package pathfinder.graphadt;

import java.util.*;

public class Graph {
    
    private final Map<Node, List<Edge>> graph = new HashMap<>();

    public List<Edge> getNodeEdges(Node n){
        return graph.get(n);
    }

    public List<Node> getNodes() {
        return new ArrayList<>(graph.keySet());
    }

    public boolean containsNode(Node n){
        return graph.containsKey(n);
    }


    public void addNode(Node node) {
        // Add node if it doesn't exist
        graph.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Edge e) {
        if (!containsNode(e.getN1()))
            addNode(e.getN1());
        if (!containsNode(e.getN2()))
            addNode(e.getN2());
        graph.get(e.getN1()).add(e);
    }

    public void removeNode(Node node) {
        // Remove node
        graph.remove(node);

        // Remove edges including this node
        for(Node n : graph.keySet()) {
            graph.get(n).removeIf(e -> e.getN2().equals(node));
        }
    }

    public void removeEdge(Edge edge) {
        // Remove edge
        graph.get(edge.getN1()).remove(edge);
    }


}
