package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;
import pathfinder.graphadt.Edge;
import pathfinder.graphadt.Graph;
import pathfinder.graphadt.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class CityGen {

    public Graph transform(Mesh oMesh, List<Tile> tiles, Graph g, int numOfCities, Random rnd) {

        List<Node> validCityLocations = getValidCityLocations(oMesh, tiles, g);
        createEdgeConnections(oMesh, g);
        numOfCities = (validCityLocations.size() == 0) ? 0 : numOfCities; // Create no cities if there are no valid spots

        for (int j = 0; j < numOfCities && validCityLocations.size() != 0; j++) {
            // Set random valid tile as a city
            Node n = validCityLocations.get(rnd.nextInt(validCityLocations.size()));
            n.addAttribute("is_city", "true");
            n.addAttribute("size", String.valueOf(rnd.nextInt(7, 12)));  
            validCityLocations.remove(n);
        }
        return g;
    }


    // Connects neighbouring nodes on the graph
    private void createEdgeConnections(Mesh mesh, Graph g) {
        List<Node> nodes = g.getNodes();
        for (Node n1 : nodes) {
            int pIdx = Integer.parseInt(n1.getAttribute("polygon_index"));
            List<Integer> nIdx = mesh.getPolygons(pIdx).getNeighborIdxsList();
            for (int i : nIdx) {
                for (Node n2 : nodes) {
                    if (n2.getAttribute("polygon_index").equals(String.valueOf(i))) {
                        Edge e = new Edge(n1, n2, getDistanceBetweenNodes(mesh, n1, n2));
                        g.addEdge(e);
                        break;
                    }
                }
            }
        }
    }

    // Gets the distance between two nodes
    private double getDistanceBetweenNodes(Mesh mesh, Node n1, Node n2) {
        int pIdx1 = Integer.parseInt(n1.getAttribute("polygon_index"));
        Polygon p1 = mesh.getPolygons(pIdx1);
        Vertex c1 = mesh.getVertices(p1.getCentroidIdx());
        int pIdx2 = Integer.parseInt(n2.getAttribute("polygon_index"));
        Polygon p2 = mesh.getPolygons(pIdx2);
        Vertex c2 = mesh.getVertices(p2.getCentroidIdx());

        return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2)) + Math.sqrt(Math.pow(c1.getY() - c2.getY(), 2));
    }

    // Gets land polygons indexes that have no water neighbors
    private List<Node> getValidCityLocations(Mesh mesh, List<Tile> tiles, Graph g) {
        List<Node> validCityLocations = new ArrayList<>();
        for (int i = 0; i < mesh.getPolygonsCount(); i++) {
            if (tiles.get(i).getType().equals(Type.OCEAN.toString()) || tiles.get(i).getType().equals(Type.LAKE.toString()))
                continue;
            Node n = createNode(i, false, 1);
            g.addNode(n);
            validCityLocations.add(n);
        }
        return validCityLocations;
    }

    private Node createNode(int idx, boolean isCity, int size) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("polygon_index", Integer.toString(idx));
        attributes.put("is_city", Boolean.toString(isCity));
        attributes.put("size", Integer.toString(size));
        return new Node(attributes);
    }

}
