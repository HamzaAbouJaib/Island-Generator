package pathfinder.GraphADT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    
    private Map<Node, List<Edge>> graph = new HashMap<>();


    public void addNode(Node node) {
        // Add node if it doesn't exist
        graph.putIfAbsent(node, new ArrayList<>());
    }

    public void removeNode(Node node) {
        // Remove node
        graph.remove(node);

        // Remove edges including this node
        for(Node n : graph.keySet()) {
            for(Edge e : graph.get(n)) {
                if(e.getN2().equals(node)) {
                    graph.get(n).remove(e);
                }
            }
        }
    }

    public void removeEdge(Edge edge) {
        // Remove edge
        graph.get(edge.getN1()).remove(edge);
    }


}
