/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.DiWeightedGraph;
import graph.Edge;
import graph.Vertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that creates one of the strategies for the djikstra method using the cost as a criteria.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class CostStrategy extends Dijkstra{
    /**
     * Creates the Djikstra class using the graph it's given by parameter
     * @param g Graph to use for the cost calculation
     */
    public CostStrategy(DiWeightedGraph<Point, Conection> g) {
        super(g);
    }

    /**
     * Calculates the shortest video from the graph
     *
     * @param start Start position.
     * @param parents Parents.
     * @param distances Distances.
     * @param navigability Navigability of the ticket.
     */
    public void executeDijkstra(Vertex<Point> start, HashMap<Vertex<Point>, Vertex<Point>> parents, HashMap<Vertex<Point>, Double> distances, boolean navigability) {
        Set<Vertex<Point>> notVisited = new HashSet();
        boolean bikePath = true;

        for (Vertex<Point> vertex : graph.vertices()) {
            notVisited.add(vertex);
            distances.put(vertex, Double.MAX_VALUE);
            parents.put(start, null);
        }
        distances.put(start, 0.0);

        while (!notVisited.isEmpty()) {
            Vertex<Point> vertex = minimumCost(notVisited, distances);
            notVisited.remove(vertex);

            for (Edge<Conection, Point> edge : graph.ascendentEdges(vertex)) {
                if (navigability == true && !(edge.element().isNavigability())) {
                    bikePath = false;
                }
                if (bikePath == true) {
                    Vertex<Point> opposite = graph.opposite(vertex, edge);
                    if (notVisited.contains(opposite)) {
                        double dist = 0.0;
                        dist = edge.element().getCost() + distances.get(vertex);
                        if (distances.get(opposite) > dist) {
                            distances.put(opposite, dist);
                            parents.put(opposite, vertex);
                        }
                    }
                }
                bikePath = true;
            }
        }
    }
    
    private Vertex<Point> minimumCost(Set<Vertex<Point>> notVisited, HashMap<Vertex<Point>, Double> distances) {
        double cost = Double.MAX_VALUE;
        Vertex<Point> returnVertex = null;

        for (Vertex<Point> v : notVisited) {
            if (distances.get(v) <= cost) {
                returnVertex = v;
                cost = distances.get(v);
            }
        }

        return returnVertex;
    }
}
