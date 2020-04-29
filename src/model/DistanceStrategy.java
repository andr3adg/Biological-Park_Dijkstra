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
 * Class the implements the Djikstra method using the distance as it's criteria.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class DistanceStrategy extends Dijkstra{
    /**
     *Creates the class and receives the graph of the path.
     * @param g Graph.
     */
    public DistanceStrategy(DiWeightedGraph<Point, Conection> g) {
        super(g);
    }

    /**
     * Calculates the shortest video from the graph
     * @param start Point where the user will start it's path.
     * @param parents Parents.
     * @param navigability Bollean variable. If true the userr will be riding a bike and if false he will not be riding a bike.
     * @param distances Distance.
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
                        dist = edge.element().getDistance() + distances.get(vertex);
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

    /*
     * Returns the lowest cost Vertex<Point> in the given List
     */
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
