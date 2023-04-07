package pathfinder.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MinPriorityQueue <T> {

    List<HashMap<T, Double>> minPriorityQueue = new ArrayList<>();


    public int size() {
        return minPriorityQueue.size();
    }

    public void enqueue(T node, Double priority) {
        HashMap<T, Double> entry = new HashMap<>();
        entry.put(node, priority);
        minPriorityQueue.add(entry);
    }

    public T dequeue() {
        if (minPriorityQueue.size() > 0) {
            T minNode = findLowestPriority();
            minPriorityQueue.removeIf(node -> node.containsKey(minNode));
            return minNode;
        }
        return null;
    }

    private T findLowestPriority () {
        T minNode = minPriorityQueue.get(0).keySet().iterator().next();
        Double minPriority = minPriorityQueue.get(0).values().iterator().next();

        for (HashMap<T, Double> n : minPriorityQueue) {
            T node = n.keySet().iterator().next();
            double priority = n.values().iterator().next();
            if (priority < minPriority) {
                minNode = node;
                minPriority = priority;
            }
        }

        return minNode;


    }


}
