/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.DiWeightedGraph;
import graph.Edge;
import graph.Vertex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class CourseManagerTest {
    private CourseManager cs;
    private Point p1;
    private Point p2;
    private Conection c;
    
    public CourseManagerTest() {
    }
    
    @Before
    public void setUp() throws IOException {
        User user = new User("Duarte", "same", 123456789);
        cs = CourseManager.getInstance();
        Generator gen = new Generator("mapa1.dat", user);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of checkPoint method, of class CourseManager.
     */
    @Test
    public void testCheckPoint() {
        Vertex<Point> expected = cs.checkPoint(1);
        assertEquals(expected, cs.checkPoint(1));
    }
    
    /**
     * Test of addPoint method, of class CourseManager.
     */
    @Test
    public void testAddPoint() {
        cs.addPoint(new Point(9, "Test"));
        assertEquals(9, cs.numOfPoints());
    }
    
    /**
     * Test of checkConection method, of class CourseManager.
     */
    @Test
    public void testCheckConection() {
        Edge<Conection, Point> expected = cs.checkConection(1);
        assertEquals(expected, cs.checkConection(1));
    }
    
    /**
     * Test of addConection method, of class CourseManager.
     */
    @Test
    public void testAddConection() {
        cs.addPoint(new Point(9, "Teste 9"));
        cs.addPoint(new Point(10, "Teste 10"));
        cs.addConection(9, 10, new Conection(13, "Teste 13", 10.0, 250.0, true, TypeConection.caminho));
        assertEquals(23, cs.numOfConections());
    }
    
    /**
     * Test of minimumCostPath, of class CourseManager
     */
    @Test
    public void testMinimumCostPath(){
        List<Point> pathPoints = new ArrayList<>();
        List<Point> pathPoints1 = new ArrayList<>();
        List<Conection> pathConections = new ArrayList<>();
        assertEquals(500.0, cs.minimumCostPath(cs.checkPoint(1), cs.checkPoint(5), pathPoints, 0, false), 0);
    }
}
