package island;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import island.Tile.Type;
import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.generators.CityGen;
import island.generators.RoadGen;
import pathfinder.graphadt.Graph;

public class RoadGenTest {

    @Test
    public void noRoadsZeroCitiesTest() {
        // Create an empty graph
        Graph g = new Graph();

        // Create an empty mesh
        Mesh m = Mesh.newBuilder().build();

        RoadGen rgen = new RoadGen();
        List<Segment> segments = rgen.transform(m, g);
        assertTrue(segments.isEmpty());
    }

    @Test
    public void noRoadsOneCityTest() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(0).setY(10).build();
        Vertex v3 = Vertex.newBuilder().setX(0).setY(20).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, 1).setNeighborIdxs(1, 2).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, 0).setNeighborIdxs(1, 2).build();
        Polygon p3 = Polygon.newBuilder().setCentroidIdx(2).addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, 0).setNeighborIdxs(1, 1).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addVertices(v3).addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));

        // Create an empty graph
        Graph g = new Graph();

        // Create cities
        CityGen cgen = new CityGen();
        g = cgen.transform(aMesh, tiles, g,1, new Random());

        RoadGen rgen = new RoadGen();
        List<Segment> segments = rgen.transform(aMesh, g);
        assertTrue(segments.isEmpty());
    }

    @Test
    public void roadsExistTest() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(0).setY(10).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));

        // Create an empty graph
        Graph g = new Graph();

        // Create cities
        CityGen cgen = new CityGen();
        g = cgen.transform(aMesh, tiles, g, 2, new Random());

        RoadGen rgen = new RoadGen();
        List<Segment> roads = rgen.transform(aMesh, g);
        assertEquals(1, roads.size());
        assertTrue(roads.get(0).getV1Idx() == 0 && roads.get(0).getV2Idx() == 1 || roads.get(0).getV1Idx() == 1 && roads.get(0).getV2Idx() == 0);
    }

}
