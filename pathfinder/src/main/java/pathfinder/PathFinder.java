package pathfinder;

import pathfinder.graphadt.Edge;
import pathfinder.graphadt.Node;
import java.util.List;
import java.util.Map;

public interface PathFinder {
    List<Node> findPath(Map<Node, List<Edge>> g, Node n1, Node n2);
}