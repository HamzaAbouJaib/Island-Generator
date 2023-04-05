package pathfinder;

import pathfinder.graphadt.Edge;
import pathfinder.graphadt.Node;

import java.util.List;

public interface PathFinder {
    public List<Edge> findPath(Node n1, Node n2);
}