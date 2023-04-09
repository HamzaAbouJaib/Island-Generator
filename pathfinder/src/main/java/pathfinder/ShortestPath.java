package pathfinder;

import pathfinder.graphadt.Graph;
import pathfinder.graphadt.Node;
import pathfinder.util.Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShortestPath implements PathFinder{

    @Override
    public List<Node> findPath(Graph g, Node n1, Node n2) {
        // If either node is not in the graph return null
        if (!g.containsNode(n1) || !g.containsNode(n2)) return null;

        // Run Dijkstra's algorithm to find the shortest paths from the source node n1
        HashMap<Node, Node> path = Dijkstra.shortestPathFromSource(g, n1).first();

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


}
