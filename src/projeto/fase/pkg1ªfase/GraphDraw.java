package projeto.fase.pkg1ªfase;

import graph.DiWeightedGraph;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafxgraph_v2.graphview.CircularSortedPlacementStrategy;
import javafxgraph_v2.graphview.GraphPanel;
import javafxgraph_v2.graphview.VertexPlacementStrategy;
import model.Conection;
import model.CourseManager;
import model.Generator;
import model.Point;

/**
 * This class will create the graph drawing.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class GraphDraw {
    private DiWeightedGraph<Point, Conection> g;

    /**
     * Will create the class and get the current graph.
     */
    public GraphDraw() {
        this.g = CourseManager.getInstance().getGraph();
    
    
    
        //VertexPlacementStrategy strategy = new CircularPlacementStrategy();
        VertexPlacementStrategy strategy = new CircularSortedPlacementStrategy();
        //VertexPlacementStrategy strategy = new RandomPlacementStrategy();
    
        GraphPanel<Point, Conection> graphView = new GraphPanel<Point, Conection>(g, strategy);
        
        Scene scene = new Scene(graphView, 700, 700);
    
        graphView.plotGraph();
        
        //after creating, shows how to change some elements
        /*graphView.setVertexColor(CourseManager.getInstance().checkPoint(1), Color.GOLD, Color.BROWN);
        graphView.setEdgeColor(CourseManager.getInstance().checkConection(1), Color.CYAN, 0.8);*/
        
        
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Visualização do Mapa");
        Image image = new Image("/resources/icon.jpg");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }
}
