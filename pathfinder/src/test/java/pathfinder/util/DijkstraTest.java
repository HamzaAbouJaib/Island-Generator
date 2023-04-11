package pathfinder.util;

import org.junit.jupiter.api.Test;
import pathfinder.ShortestPath;
import pathfinder.graphadt.Edge;
import pathfinder.graphadt.Graph;
import pathfinder.graphadt.Node;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraTest {


    @Test
    public void shortestPathFromSourceTest() {
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

        HashMap<Node, Node> path = Dijkstra.shortestPathFromSource(g, n1).first();

        assertEquals(n1, path.get(n1));
        assertEquals(n1, path.get(n2));
        assertEquals(n2, path.get(n5));
        assertEquals(n5, path.get(n6));
        assertEquals(n3, path.get(n4));

    }

    @Test
    public void shortestPathFromSourceCostTest() {
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

        HashMap<Node, Double> cost = Dijkstra.shortestPathFromSource(g, n1).second();

        assertEquals(0d, cost.get(n1));
        assertEquals(1d, cost.get(n2));
        assertEquals(2d, cost.get(n5));
        assertEquals(3d, cost.get(n6));
        assertEquals(3d, cost.get(n4));

    }

}
