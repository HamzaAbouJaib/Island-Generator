package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.ArrayList;
import java.util.List;

public class DelaunayTriangulationGen {

    public Mesh.Builder generate(Mesh.Builder mesh){
        GeometryFactory geometryFactory = new GeometryFactory();
        ArrayList<Coordinate> coords = new ArrayList<>();
        // Set sites to be already existing vertices in the mesh
        for (Structs.Polygon p : mesh.getPolygonsList()) {
            Structs.Vertex centroid = mesh.getVertices(p.getCentroidIdx());
            coords.add(new Coordinate(centroid.getX(), centroid.getY()));
        }

        DelaunayTriangulationBuilder triangleBuilder = new DelaunayTriangulationBuilder();
        triangleBuilder.setSites(coords);

        // Creates the triangles
        Geometry triangles = triangleBuilder.getTriangles(geometryFactory);
        // Polygon here is JTS Polygon
        ArrayList<Polygon> trianglesProduced = new ArrayList<>();

        if(triangles instanceof GeometryCollection geometryCollection) {
            for(int i = 0; i < geometryCollection.getNumGeometries(); i++) {
                // Convert triangles from Geometry into Polygons and add them to triangles produced list
                trianglesProduced.add((Polygon) geometryCollection.getGeometryN(i));
            }
        }

        // Stores the Polygons with neighbors
        ArrayList<Structs.Polygon.Builder> polygonBuilders = new ArrayList<>();
        // Stores Polygons that have been generated
        List<Structs.Polygon> polyList = mesh.getPolygonsList();

        // Loop over all polygons generated
        for (Structs.Polygon p : polyList) {
            Structs.Vertex centroid = mesh.getVertices(p.getCentroidIdx());
            // Recreate the polygon
            Structs.Polygon.Builder poly = Structs.Polygon.newBuilder().addAllSegmentIdxs(p.getSegmentIdxsList()).setCentroidIdx(p.getCentroidIdx());
            // Stores the index of the neighbor in the neighborIdx list
            int j = 0;
            // Loop over all the triangles produced
            for(Polygon t: trianglesProduced) {
                Coordinate[] tCoords = t.getCoordinates();
                // Check if the centroid of the polygon matches any of the triangle's vertices
                if (centroid.getX() == tCoords[0].getX() && centroid.getY() == tCoords[0].getY() || centroid.getX() == tCoords[1].getX() && centroid.getY() == tCoords[1].getY() || centroid.getX() == tCoords[2].getX() && centroid.getY() == tCoords[2].getY()) {
                    // Loop over all polygons generated
                    for (int i = 0; i < polyList.size(); i++){
                        // If we are not looking at the same polygon
                        if (!polyList.get(i).equals(p)) {
                            // Loop over all vertices in the triangle (last vertex is the same as the first one)
                            for (int w = 0; w < tCoords.length - 1; w++) {
                                Structs.Vertex centroid2 = mesh.getVertices(polyList.get(i).getCentroidIdx());
                                // if a vertex in the triangle matches the centroid of a polygon that polygon wasn't already declared a neighbor then add the polygon as a neighbor
                                if (centroid2.getX() == tCoords[w].getX() && centroid2.getY() == tCoords[w].getY()) {
                                    boolean exists = false;
                                    for (int n = 0; n < poly.getNeighborIdxsCount(); n++) {
                                        if (poly.getNeighborIdxs(n) == i) {
                                            exists = true;
                                            break;
                                        }
                                    }
                                    // If the neighbor is not already set as a neighbor then add it
                                    if (!exists) {
                                        poly.addNeighborIdxs(j).setNeighborIdxs(j, i);
                                        j++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            polygonBuilders.add(poly);
        }

        return mesh;
    }


}
