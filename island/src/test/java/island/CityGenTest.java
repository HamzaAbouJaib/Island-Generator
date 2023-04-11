package island;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;
import island.generators.CityGen;
import pathfinder.graphadt.Edge;
import pathfinder.graphadt.Graph;

import static org.junit.jupiter.api.Assertions.*;

public class CityGenTest {
    
    private CityGen cgen = new CityGen();

    @Test
    public void nodesExistTest() {
        // Create a test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.OCEAN, null));

        Graph g = new Graph();
        g = cgen.transform(aMesh, tiles, g, 0, new Random());

        assertEquals(1, g.getNodes().size());
        assertEquals(aMesh.getPolygonsList().indexOf(p1), Integer.parseInt(g.getNodes().get(0).getAttribute("polygon_index")));

    }

    @Test
    public void citiesExistTest() {
        // Create a test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));

        Graph g = new Graph();
        g = cgen.transform(aMesh, tiles, g, 1, new Random());

        assertTrue(Boolean.parseBoolean(g.getNodes().get(0).getAttribute("is_city")));
        assertFalse(Boolean.parseBoolean(g.getNodes().get(1).getAttribute("is_city")));

    }

    @Test
    public void edgesBetweenNeighboursExistTest() {
        // Create a test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(0).setY(0).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));

        Graph g = new Graph();
        g = cgen.transform(aMesh, tiles, g, 2, new Random());
        List<Edge> city1Edges = g.getNodeEdges(g.getNodes().get(0));
        List<Edge> city2Edges = g.getNodeEdges(g.getNodes().get(1));

        assertEquals(1, city1Edges.size());
        assertEquals(1, city2Edges.size());
        assertEquals(city1Edges.get(0).getN1().getAttribute("polygon_index"), city2Edges.get(0).getN2().getAttribute("polygon_index"));
        assertEquals(city1Edges.get(0).getN2().getAttribute("polygon_index"), city2Edges.get(0).getN1().getAttribute("polygon_index"));
    }

}
