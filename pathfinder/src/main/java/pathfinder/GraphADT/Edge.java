package pathfinder.graphadt;

public class Edge {

    private Node n1;
    private Node n2;
    private double weight;

    public Edge(Node n1, Node n2, double weight) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = weight;
    }
    
    
    public Node getN1(){
        return this.n1;
    }

    public Node getN2(){
        return this.n2;
    }

    public double getWeight(){
        return this.weight;
    }
}
