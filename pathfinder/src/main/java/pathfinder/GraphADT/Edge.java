package pathfinder.GraphADT;

public class Edge {

    private Node n1;
    private Node n2;
    private double weight;

    public Edge(Node n1, Node n2, double weight) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = weight;
    }
    
}
