/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.DiWeightedGraph;
import graph.Vertex;
import java.util.HashMap;

/**
 * Class that represents the Djikstra method to use different types of this method.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public abstract class Dijkstra {

    /**
     * Graph to execute the Djikstra.
     */
    protected DiWeightedGraph<Point, Conection> graph;
    
    /**
     * Creates the class.
     * @param g Graph to use.
     */
    public Dijkstra(DiWeightedGraph<Point, Conection> g) {
        this.graph = g;
    }
    
    /**
    * Executes the djikstra with two different methods possible, using the distance and cost as it's criteria. If it want's to know
    * the shortest path it will use the DistanceStrategy class, and if it want's to know the cheapest path it will use the 
    * CostStrategy class.
    * @param start Point where the user will start it's path.
    * @param parents Parents.
    * @param navigability Bollean variable. If true the userr will be riding a bike and if false he will not be riding a bike.
    * @param distances Distances.
    */
    public abstract void executeDijkstra(Vertex<Point> start, HashMap<Vertex<Point>, Vertex<Point>> parents, HashMap<Vertex<Point>, Double> distances, boolean navigability);
}
