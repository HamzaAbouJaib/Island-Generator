package pathfinder;

import pathfinder.graphadt.Node;
import java.util.List;

public interface PathFinder {
    List<Node> findPath(Node n1, Node n2);
}