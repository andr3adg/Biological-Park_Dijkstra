/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import graph.DiWeightedGraph.MyEdge;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import model.Conection;
import model.Point;
import model.TypeConection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class DiWeightedGraphTest {
    private DiWeightedGraph<Point, Conection> graph;
    private Point p1;
    private Point p2;
    private Point p3;
    private Conection c1;
    private Conection c2;
    private Conection c3;
    
    public DiWeightedGraphTest() {
    }
    
    @Before
    public void setUp() {
        graph = new DiWeightedGraph<Point, Conection>();
        p1 = new Point(1, "Point 1");
        p2 = new Point(2, "Point 2");
        p3 = new Point(3, "Point 3");
        c1 = new Conection(1, "Conection 1", 10.0, 250, true, TypeConection.ponte);
        c2 = new Conection(2, "Conection 2", 12.0, 200, true, TypeConection.ponte);
        c3 = new Conection(3, "Conection 3", 20.0, 210, true, TypeConection.ponte);
        graph.insertVertex(p1);
        graph.insertVertex(p2);
        graph.insertVertex(p3);
        graph.insertEdge(p1, p2, c1);
        graph.insertEdge(p2, p3, c2);
        graph.insertEdge(p1, p3, c3);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of numVertices on Create method, of class DiWeightedGraph.
     */
    @Test
    public void testNumVertices_OnCreate() {
        assertEquals(3, graph.numVertices());
    }
    
    /*
    * Test of numVertices method after one insert, of class DiWeightedGraph.
    */
    @Test
    public void testNumVertices_OnInsert(){
        graph.insertVertex(new Point(4, "Point 4"));
        assertEquals(4, graph.numVertices());
    }
    
    /*
    * Test of numVertices method after one remove, of class DiWeightedGraph.
    */
    @Test
    public void testNumVertices_OnRemove(){
        graph.removeVertex(graph.getVertexList().get(p1));
        assertEquals(2, graph.numVertices());
    }

    /**
     * Test of numEdges method, of class DiWeightedGraph.
     */
    @Test
    public void testNumEdges_onCreate() {
        assertEquals(3, graph.numEdges());
    }
    
    /*
    * Test of numEdges method after one insert, of class DiWeightedGraph.
    */
    @Test
    public void testnumEdges_OnInsert(){
        graph.insertEdge(p2, p1, new Conection(4, "Conection 4", 10.0, 250, true, TypeConection.ponte));
        assertEquals(4, graph.numEdges());
    }
    
    /*
    * Test of numEdges method after one remove, of class DiWeightedGraph.
    */
    @Test
    public void testnumEdges_OnRemove(){
        graph.removeEdge(graph.getEdgesList().get(c1));
        assertEquals(2, graph.numEdges());
    }

    /**
     * Test of incidentEdges method, of class DiWeightedGraph.
     */
    @Test
    public void testIncidentEdges_OnCreate() {        
        ArrayList<Edge<Conection, Point>> expected1 = new ArrayList<>();
        expected1.add(graph.getEdge(graph.getVertexList().get(p1), (Vertex<Point>) graph.getVertexList().get(p2)));
        ArrayList<Edge<Conection, Point>> result =  new ArrayList<>();
        result = (ArrayList<Edge<Conection, Point>>) graph.incidentEdges(graph.getVertexList().get(p2));
        assertEquals(expected1, result);
    }

    /**
     * Test of opposite method, of class DiWeightedGraph.
     */
    @Test
    public void testOpposite() {
        Vertex<Point> expResult = graph.opposite(graph.getVertexList().get(p1), graph.getEdgesList().get(c1));
        assertEquals(expResult, graph.getVertexList().get(p2));
    }

    /**
     * Test of areAdjacent method, of class DiWeightedGraph.
     */
    @Test
    public void testAreAdjacent() {
        assertEquals(graph.areAdjacent(graph.getVertexList().get(p1), graph.getVertexList().get(p2)), true);
        assertEquals(graph.areAdjacent(graph.getVertexList().get(p2), graph.getVertexList().get(p3)), true);
        assertEquals(graph.areAdjacent(graph.getVertexList().get(p1), graph.getVertexList().get(p3)), true);
    }

    /**
     * Test of insertVertex method, of class DiWeightedGraph.
     */
    @Test
    public void testInsertVertex() {
        graph.insertVertex(new Point(4, "Point 4"));
        assertEquals(4, graph.numVertices());
    }

    /**
     * Test of insertEdge method, of class DiWeightedGraph.
     */
    @Test
    public void testInsertEdge() {
        graph.insertEdge(p2, p1, new Conection(4, "Conection 4", 12.0, 250, true, TypeConection.ponte));
        assertEquals(4, graph.numEdges());
    }

    /**
     * Test of removeVertex method, of class DiWeightedGraph.
     */
    @Test
    public void testRemoveVertex() {
        graph.removeVertex(graph.getVertexList().get(p2));
        assertEquals(2, graph.numVertices());
    }

    /**
     * Test of removeEdge method, of class DiWeightedGraph.
     */
    @Test
    public void testRemoveEdge() {
        graph.removeEdge(graph.getEdgesList().get(c1));
        assertEquals(2, graph.numEdges());
    }

    /**
     * Test of replace method, of class DiWeightedGraph.
     */
    @Test
    public void testReplace_Vertex() {
        graph.replace(graph.getVertexList().get(p1), new Point(4, "Point 4"));
        assertEquals(4, graph.getVertexList().get(p1).element().getId());
    }

    /**
     * Test of replace method, of class DiWeightedGraph.
     */
    @Test
    public void testReplace_Edge() {
        graph.replace(graph.getEdgesList().get(c1), new Conection(4, "Conection 4", 12.0, 250, true, TypeConection.ponte));
        assertEquals(4, graph.getEdgesList().get(c1).element().getId());
    }
}
