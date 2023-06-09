package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import pathfinder.ShortestPath;
import pathfinder.graphadt.Graph;
import pathfinder.graphadt.Node;
import pathfinder.util.Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RoadGen {

    public List<Segment> transform(Mesh oMesh, Graph g) {
        List<Segment> roads = new ArrayList<>();

        // Get the city with the shortest distance to every other city
        Node centralCity = getCentralCity(g);
        // No roads exist
        if (centralCity == null) return roads;

        // Get all nodes
        List<Node> nodes = g.getNodes();

        // Get all cities
        List<Node> cities = new ArrayList<>();
        for (Node n : nodes) {
            if (Boolean.parseBoolean(n.getAttribute("is_city"))) {
                cities.add(n);
            }
        }

        // Label the central city as central
        centralCity.addAttribute("is_central", "true");
        cities.remove(centralCity);

        // Create roads (segments) connecting the cities to the central city
        Property color =  Property.newBuilder().setKey("rgb_color").setValue("120,120,120").build();
        Property thickness = Property.newBuilder().setKey("thickness").setValue("3").build();
        ShortestPath shortestPathFinder = new ShortestPath();
        for (Node n : cities) {
            List<Node> path = shortestPathFinder.findPath(g, centralCity, n);
            if (path != null) {
                for (int i = 0; i < path.size()-1; i++) {
                    Segment.Builder segBuilder = Segment.newBuilder();
                    segBuilder.setV1Idx(getCentroidIndex(oMesh, path.get(i))).setV2Idx(getCentroidIndex(oMesh, path.get(i+1)));
                    segBuilder.addProperties(color).addProperties(thickness);
                    Segment seg = segBuilder.build();
                    boolean addRoad = true;
                    for (Segment s : roads) {
                        if (s.getV1Idx() == seg.getV1Idx() && s.getV2Idx() == seg.getV2Idx()) {
                            addRoad = false;
                            break;
                        }
                    }
                    if (addRoad) {
                        roads.add(seg);
                    }
                }
            }
        }

        // Connect non capital cities/hamlets/villages
        // Close by cities/hamlets/villages are connected by small roads
        // Far away cities/hamlets/villages are connected by larger roads
        for (Node n1 : cities) {
            color =  Property.newBuilder().setKey("rgb_color").setValue("60,60,60").build();
            thickness = Property.newBuilder().setKey("thickness").setValue("2").build();
            // Find the closest city/hamlet/village
            if (!n1.equals(centralCity)) {
                Node closestNode = null;
                double shortestDistance = Double.MAX_VALUE;
                for (Node n2 : cities) {
                    if (!n1.equals(n2)) {
                        if (getDistanceBetweenNodes(oMesh, n1, n2) < shortestDistance) {
                            shortestDistance = getDistanceBetweenNodes(oMesh, n1, n2);
                            closestNode = n2;
                        }
                    }
                }
                // If no city/hamlet/villages exist then skip
                if (closestNode == null) continue;
                // Find the shortest path and connect the two cities/hamlet/villages
                List<Node> path = shortestPathFinder.findPath(g, n1, closestNode);
                if (path != null) {
                    if (path.size() < 7) {
                        color =  Property.newBuilder().setKey("rgb_color").setValue("20,20,20").build();
                        thickness = Property.newBuilder().setKey("thickness").setValue("1").build();
                    }
                    for (int i = 0; i < path.size() - 1; i++) {
                        Segment.Builder segBuilder = Segment.newBuilder();
                        segBuilder.setV1Idx(getCentroidIndex(oMesh, path.get(i))).setV2Idx(getCentroidIndex(oMesh, path.get(i + 1)));
                        segBuilder.addProperties(color).addProperties(thickness);
                        Segment seg = segBuilder.build();
                        boolean addRoad = true;
                        for (Segment s : roads) {
                            if (s.getV1Idx() == seg.getV1Idx() && s.getV2Idx() == seg.getV2Idx() || s.getV2Idx() == seg.getV1Idx() && s.getV1Idx() == seg.getV2Idx()) {
                                addRoad = false;
                                break;
                            }
                        }
                        if (addRoad) {
                            roads.add(seg);
                        }
                    }
                }

            }
        }


        return roads;
    }

    private double getDistanceBetweenNodes(Mesh mesh, Node n1, Node n2) {
        Structs.Vertex c1 = mesh.getVertices(getCentroidIndex(mesh, n1));
        Structs.Vertex c2 = mesh.getVertices(getCentroidIndex(mesh, n2));

        return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2)) + Math.sqrt(Math.pow(c1.getY() - c2.getY(), 2));
    }

    // Gets the centroid index of the respective node
    private int getCentroidIndex(Mesh mesh, Node n) {
        int pIdx = Integer.parseInt(n.getAttribute("polygon_index"));
        Structs.Polygon p = mesh.getPolygons(pIdx);
        return p.getCentroidIdx();
    }

    // Gets the central city that minimizes distance to all nodes
    private Node getCentralCity(Graph g) {
        Node centralNode = null;
        double minMaxPath = Double.MAX_VALUE;
        double maxSize = 0;
        for (Node n1 : g.getNodes()) {
            if (Boolean.parseBoolean(n1.getAttribute("is_city"))) {
                HashMap<Node, Double> cost = Dijkstra.shortestPathFromSource(g, n1).second();
                double maxPath = 0;
                for (Node n2 : cost.keySet()) {
                    double size = Double.parseDouble(n2.getAttribute("size"));
                    if (Boolean.parseBoolean(n2.getAttribute("is_city")) && cost.get(n2) != Double.MAX_VALUE && size >= maxSize) {
                        maxPath = Math.max(maxPath, cost.get(n2));
                    }
                }
                if (minMaxPath > maxPath && maxPath > 0) {
                    minMaxPath = maxPath;
                    centralNode = n1;
                }
            }
        }
        return centralNode;
    }

}
