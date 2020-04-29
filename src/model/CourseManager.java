package model;

import graph.DiWeightedGraph;
import graph.Edge;
import graph.InvalidEdgeException;
import graph.InvalidVertexException;
import graph.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.MSG1;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class deals with mosth of the Graph values and executes the djikstra using a Strategy Pattern by using the criteria.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public final class CourseManager {

    private static CourseManager instance = new CourseManager();
    private DiWeightedGraph<Point, Conection> graph;
    User user;

    private CourseManager() {
        this.graph = new DiWeightedGraph<Point, Conection>();
        setGraph(graph);
    }

    /**
     * Returns this Course Manager instance.
     * @return Current Course Manager
     */
    public static CourseManager getInstance() {
        return instance;
    }

    /**
     * Return the user that's using the application.
     * @return User using the application.
     */
    public User getUser() {
        return user;
    }

    /**
     * Changes the current user variable.
     * @param user New User
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Changes the graph of the park.
     * @param newGraph Graph to replace the old one.
     */
    public void setGraph(DiWeightedGraph<Point, Conection> newGraph) {
        graph = newGraph;
    }

    /**
     * Returns the graph of the Course
     *
     * @return graph
     */
    public DiWeightedGraph<Point, Conection> getGraph() {
        return graph;
    }

    /**
     * Returns the Course Manager number of Points of Interest.
     * @return Number of Points of Interest.
     */
    public int numOfPoints() {
        return graph.numVertices();
    }

    /**
     * Returns the Course Manager number of Conections.
     * @return number of Conections.
     */
    public int numOfConections() {
        return graph.numEdges();
    }

    /**
     * Returns the Vertex that has the id given by the parameter
     * @param pointID ID of the wanted Vertex
     * @return Wanted Vertex
     * @throws InvalidVertexException if the Vertex doesn't exist or the
     * PointID is negative or zero
     */
    public Vertex<Point> checkPoint(int pointID) throws InvalidVertexException {
        if (pointID < 0) {
            throw new InvalidVertexException("InterestPointID has to be higher than 0");
        }

        Vertex<Point> find = null;
        for (Vertex<Point> v : graph.vertices()) {
            if (v.element().getId() == pointID) { // equals was overriden in Airport!!
                find = v;
            }
        }

        if (find == null) {
            throw new InvalidVertexException("Interest Point with code (" + pointID + ") does not exist");
        }

        return find;
    }

    /**
     * Adds a Point to the graph bye adding a new Vertex
     * @param point New Point to insert into to the graph as a Vertex
     * @throws InvalidVertexException If the given Point is null or if the Point
     * already exists in the graph
     */
    public void addPoint(Point point) throws InvalidVertexException {

        if (point == null) {
            throw new InvalidVertexException("Interest Point cannot be null");
        }

        try {
            graph.insertVertex(point);
        } catch (InvalidVertexException e) {
            throw new InvalidVertexException("Interest Point with code (" + point.getId() + ") already exists");

        }
    }

    /**
     * Check if exists an Edge with given ConectionID
     * @param conectionID ID of the wanted Conection
     * @return Wanted Conection
     * @throws InvalidEdgeException if the ConectionID is negative or zero, or
     * if the Conection doesn't exist
     */
    public Edge<Conection, Point> checkConection(int conectionID) throws InvalidEdgeException {
        if (conectionID < 0) {
            throw new InvalidEdgeException("ConectionID has to be higher than 0");
        }

        Edge<Conection, Point> find = null;
        for (Edge<Conection, Point> e : graph.edges()) {
            if (e.element().getId() == conectionID) { // equals was overriden in Airport!!
                find = e;
            }
        }

        if (find == null) {
            throw new InvalidEdgeException("Conection with code (" + find.element().getId() + ") does not exist");
        }

        return find;
    }

    /**
     * Adds a new Conection to the graph as an Edge
     * @param point1ID First Vertex of the Conection
     * @param point2ID Second Vertex of the Conection
     * @param conection Conection details
     * @throws InvalidEdgeException if the Conection is null or if it already
     * exists
     */
    public void addConection(int point1ID, int point2ID, Conection conection) throws InvalidEdgeException {

        if (conection == null) {
            throw new InvalidEdgeException("Conection is null");
        }

        Vertex<Point> a1 = checkPoint(point2ID);
        Vertex<Point> a2 = checkPoint(point1ID);

        try {
            if (conection.getType() == TypeConection.ponte) {
                graph.insertEdge(a1, a2, conection);
            } else {
                graph.insertEdge(a1, a2, conection);
                Conection aux = new Conection(conection.getId(), conection.getName(), conection.getCost(), conection.getDistance(),
                        conection.isNavigability(), TypeConection.caminho);
                graph.insertEdge(a2, a1, aux);
            }
        } catch (InvalidEdgeException e) {
            throw new InvalidEdgeException("The Conection (" + conection.getId() + ") already exists");
        }
    }

    private void executeDijkstra(Vertex<Point> start, HashMap<Vertex<Point>, Vertex<Point>> parents, HashMap<Vertex<Point>, Double> distances, int criteria, boolean navigability) {
        if (criteria == 0) {
            Dijkstra ds = new DistanceStrategy(graph);
            ds.executeDijkstra(start, parents, distances, navigability);
        } else if (criteria == 1) {
            Dijkstra cs = new CostStrategy(graph);
            cs.executeDijkstra(start, parents, distances, navigability);
        }
    }

    /**
     * Returns the lowest cost of the choosen path
     * @param start Start of the path
     * @param dst End of the path
     * @param returnPath Shortest Path
     * @param bike Navigability.
     * @param criteria If is 1 it claculates with cost, if 0 calculates with
     * distance
     * @return Cost of the path
     */
    public double minimumCostPath(Vertex<Point> start, Vertex<Point> dst, List<Point> returnPath, int criteria, boolean bike) {
        HashMap<Vertex<Point>, Double> distances = new HashMap<>();
        HashMap<Vertex<Point>, Vertex<Point>> parents = new HashMap<>();
        HashMap<Vertex<Point>, Double> distances1 = new HashMap<>();
        HashMap<Vertex<Point>, Vertex<Point>> parents1 = new HashMap<>();

        Vertex<Point> origin = checkPoint(start.element().getId());
        Vertex<Point> destination = checkPoint(dst.element().getId());

        if (origin.element().getName().equalsIgnoreCase("Entrada") && destination.element().getName().equalsIgnoreCase("Entrada")) {
            return 0.0;
        }

        returnPath.clear();
        //Caminho de Ida
        executeDijkstra(start, parents, distances, criteria, bike);

        do {
            returnPath.add(0, destination.element());
            destination = parents.get(destination);
        } while (destination != origin);
        returnPath.add(0, origin.element());

        destination = checkPoint(dst.element().getId());
        double cost1 = distances.get(checkPoint(dst.element().getId()));
        return cost1;
    }
}
