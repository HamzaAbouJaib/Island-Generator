package pathfinder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pathfinder.graphadt.Edge;
import pathfinder.graphadt.Graph;
import pathfinder.graphadt.Node;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortestPathTest {

    @Test
    public void shortestPathTest(){
        // Create nodes
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Node n5 = new Node();
        Node n6 = new Node();

        // Create edges connecting the nodes
        Edge e1 = new Edge(n1, n2);
        Edge e2 = new Edge(n2, n3);
        Edge e3 = new Edge(n3, n4);
        Edge e4 = new Edge(n4, n5);
        Edge e5 = new Edge(n5, n6);
        Edge e6 = new Edge(n2, n5);

        // Create a graph and add the edges to it
        Graph g = new Graph();
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);

        // Expected shortest path
        List<Node> shortestPath = new ArrayList<>();
        shortestPath.add(n1);
        shortestPath.add(n2);
        shortestPath.add(n5);
        shortestPath.add(n6);

        assertEquals(shortestPath, new ShortestPath().findPath(g, n1, n6));
    }
}
