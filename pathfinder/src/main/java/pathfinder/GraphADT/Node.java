package pathfinder.GraphADT;

import java.util.HashMap;

public class Node {

    HashMap<String, String> attributes;

    // Create a Node with no attributes
    public Node() {
        this.attributes = new HashMap<>();
    }

    // Create a Node using a predefined HashMap of attributes
    public Node(HashMap<String, String> attr) {
        this.attributes = attr;
    }

    // Add an attribute to the Node
    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    // Get an attribute from the Node
    public String getAttribute(String key) {
        return this.attributes.get(key);
    }
}
