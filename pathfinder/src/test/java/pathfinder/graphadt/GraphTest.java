package pathfinder.graphadt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void emptyGraphTest() {
        Graph g = new Graph();
        assertEquals(0, g.getNodes().size());
    }

    @Test
    public void addNodesToGraph(){
        // Create nodes
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        // Create nodes and add them to the graph
        Graph g = new Graph();
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);

        // Verify that the nodes have been added to the graph
        assertTrue(g.getNodes().contains(n1));
        assertTrue(g.getNodes().contains(n2));
        assertTrue(g.getNodes().contains(n3));
    }

    @Test
    public void graphContainsNodeTest(){
        // Create a node
        Node n1 = new Node();

        // Create a graph and the node to it
        Graph g = new Graph();
        g.addNode(n1);

        // Check if the graph contains the node using the built-in method
        assertTrue(g.containsNode(n1));
        assertEquals(g.containsNode(n1), g.getNodes().contains(n1));
    }

    @Test
    public void addEdgesToGraph(){
        // Create nodes
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        // Create edges connecting the nodes
        Edge e1 = new Edge(n1, n2);
        Edge e2 = new Edge(n1, n3);
        Edge e3 = new Edge(n2, n3);

        // Create a graph and add the edges to it
        Graph g = new Graph();
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        // Verify that adding the edges also added the nodes
        assertTrue(g.getNodes().contains(n1));
        assertTrue(g.getNodes().contains(n2));
        assertTrue(g.getNodes().contains(n3));

        // Verify that the graph contains the edges
        assertTrue(g.getNodeEdges(n1).contains(e1));
        assertTrue(g.getNodeEdges(n1).contains(e2));
        assertTrue(g.getNodeEdges(n2).contains(e3));

    }

    @Test
    public void removeNodeFromGraph() {
        // Create a node
        Node n1 = new Node();

        // Create a graph and add the node to it
        Graph g = new Graph();
        g.addNode(n1);

        // Verify that the graph contains the node
        assertTrue(g.containsNode(n1));

        // Remove the node from the graph
        g.removeNode(n1);

        // Verify that the node has been removed from the graph
        assertFalse(g.containsNode(n1));
    }

    @Test
    public void removeEdgeFromGraph() {
        // Create nodes
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        // Create edges connecting the nodes
        Edge e1 = new Edge(n1, n2);
        Edge e2 = new Edge(n1, n3);
        Edge e3 = new Edge(n2, n3);

        // Create a graph and add the edges to it
        Graph g = new Graph();
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        /* Repeatedly remove one edge at a time and verify that
         it has been removed without affecting the other edges */

        assertTrue(g.getNodeEdges(n1).contains(e1));
        assertTrue(g.getNodeEdges(n1).contains(e2));
        assertTrue(g.getNodeEdges(n2).contains(e3));

        g.removeEdge(e1);

        assertFalse(g.getNodeEdges(n1).contains(e1));
        assertTrue(g.getNodeEdges(n1).contains(e2));
        assertTrue(g.getNodeEdges(n2).contains(e3));

        g.removeEdge(e3);

        assertFalse(g.getNodeEdges(n1).contains(e1));
        assertTrue(g.getNodeEdges(n1).contains(e2));
        assertFalse(g.getNodeEdges(n2).contains(e3));

        g.removeEdge(e2);

        assertFalse(g.getNodeEdges(n1).contains(e1));
        assertFalse(g.getNodeEdges(n1).contains(e2));
        assertFalse(g.getNodeEdges(n2).contains(e3));

    }

}
