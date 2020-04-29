package javafx;

import graph.Edge;
import graph.Vertex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Caretaker;
import model.Conection;
import model.CourseManager;
import model.Generator;
import model.PDFGenerator;
import model.PDFMethods;
import model.Point;
import model.Statistics;
import model.TicketSaver;
import model.UserDAOOneJson;

/**
 * Class that will show the outward and return path with the points that user want to visit and its total distance or cost.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Ticket extends BorderPane {
    private Text textBilhete;
    private Label labelIda;
    private ListView listIda;
    private Label labelVolta;
    private ListView listVolta;
    private Label labelTotalDinheiro;
    private TextField textFieldTotalDinheiro;
    private Label labelTotalDistancia;
    private TextField textFieldTotalDistancia;
    private GridPane grid1;
    private GridPane grid2;
    private HBox HBox1;
    private HBox HBox2;
    private List<Point> pathPoints = new ArrayList<>();
    private List<Point> pathPoints1 = new ArrayList<>();
    private Generator generator;
    private boolean bike;
    private int criteria;
    Stage stage;
    private Button btnBack;
    private Button btnPrint;
    private int distance = 0;
    private int cost = 0;

    /**
     * Screen creation
     * @param stage Stage where the screen will be shown
     * @param pathPoints Outward trip list
     * @param pathPoints1 Return trip list
     * @param bike Boolean variable to know if the user wants to ride a bike or not.
     * @param criteria Int variable. If 0 the user wants to know the distance and if 1 it wants to know the cost.
     * @param caretaker Class for the user to be able to return to last screen and keep the old variables
     */
    public Ticket(Stage stage, List<Point> pathPoints, List<Point> pathPoints1, boolean bike, int criteria, Caretaker caretaker) {
        this.stage = stage;
        this.generator = generator;
        this.bike = bike;
        this.criteria = criteria;
        this.setWidth(1280);
        this.setHeight(768);
        
        this.pathPoints = pathPoints;
        this.pathPoints1 = pathPoints1;
        setAlignment(this, Pos.CENTER);
        this.setPadding(new Insets(15, 15, 15, 15));
        textBilhete = createText();
        labelIda = new Label("---Ida---");
        labelIda.setMinWidth(70);
        listIda = new ListView();
        labelVolta = new Label("--Volta--");
        labelVolta.setMinWidth(69);
        listVolta = new ListView();

        //If the criteria is 1 it will show the total cost, and if its 0 it will show the distance
        if (criteria == 1) {
            labelTotalDinheiro = new Label("Total(Dinheiro)");
            labelTotalDinheiro.setMinWidth(70);
            textFieldTotalDinheiro = new TextField();
        } else if (criteria == 0) {
            labelTotalDistancia = new Label("Total(Distância)");
            labelTotalDistancia.setMinWidth(70);
            textFieldTotalDistancia = new TextField();
        }

        //Back button
        btnBack = createButton1("back.png", 20, 20);
        btnBack.setOnAction(e -> {
            Map map = new Map(stage, bike, true);
            //Gets the ticket as it was when the ticket was asked
            setPreviousValues(map, caretaker.getMemento(0).getListDisponiveis(), caretaker.getMemento(0).getListSelecionados(), caretaker.getMemento(0).getTotalDistance(), caretaker.getMemento(0).getTotalCost());
            Scene scene = new Scene(map, map.getWidth(), map.getHeight());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        });
        setTop(btnBack);

        grid1 = createButtonsGrid();
        grid2 = createButtonsGrid();

        StackPane auxStack = new StackPane();
        auxStack.setMinWidth(100);
        setLeft(auxStack);
        setCenter(grid1);

        grid1.add(grid2, 0, 0);
        grid2.add(textBilhete, 0, 0);

        grid2.add(labelIda, 0, 1);
        grid2.add(listIda, 0, 2);
        grid2.add(labelVolta, 0, 3);
        grid2.add(listVolta, 0, 4);

        HBox1 = createHBox();
        HBox2 = createHBox();

        if (criteria == 1) {
            HBox1.getChildren().add(labelTotalDinheiro);
            HBox1.getChildren().add(textFieldTotalDinheiro);
            grid2.add(HBox1, 0, 5);
        } else if (criteria == 0) {
            HBox2.getChildren().add(labelTotalDistancia);
            HBox2.getChildren().add(textFieldTotalDistancia);
            grid2.add(HBox2, 0, 6);
        }

        //Outward Path
        for (Point p : pathPoints) {
            listIda.getItems().add(p.getName());
        }

        //Return Path
        for (Point p : pathPoints1) {
            listVolta.getItems().add(p.getName());
        }

        if (criteria == 0) {
            distance = calculateDistance();
            cost = calculateCost();
            textFieldTotalDistancia.setText(Integer.toString(calculateDistance()));
        } else if (criteria == 1) {
            distance = calculateDistance();
            cost = calculateCost();
            textFieldTotalDinheiro.setText(Integer.toString(calculateCost()));
        }

        //Confirm Button
        btnPrint = createButton1("confirm.png", 40, 40);
        btnPrint.setOnAction(e -> {
            printButton();
        });
        setRight(btnPrint);
    }
    
    private void printButton(){
        TicketSaver savedTicket = null;
            if (criteria == 0) {
                List<String> trip1 = new ArrayList<>();
                for (Point p : pathPoints) {
                    trip1.add(p.getName());
                }
                List<String> trip2 = new ArrayList<>();
                for (Point p : pathPoints1) {
                    trip2.add(p.getName());
                }
                savedTicket = new TicketSaver(trip1, trip2, distance, cost, bike);
                Statistics.getInstance().addTicket(savedTicket);
            } else if (criteria == 1) {
                List<String> trip1 = new ArrayList<>();
                for (Point p : pathPoints) {
                    trip1.add(p.getName());
                }
                List<String> trip2 = new ArrayList<>();
                for (Point p : pathPoints1) {
                    trip2.add(p.getName());
                }
                savedTicket = new TicketSaver(trip1, trip2, distance, cost, bike);
                Statistics.getInstance().addTicket(savedTicket);
            }

            //Adds a ticket to the Statistics Class
            CourseManager.getInstance().getUser().addTicket(savedTicket);
            Statistics.getInstance().save();
            UserDAOOneJson.getInstance().saveUser1(CourseManager.getInstance().getUser());

            PDFGenerator pdf = new PDFMethods(pathPoints, pathPoints1, CourseManager.getInstance().getUser().getNif(), distance, cost, bike);
            //Creates the ticket PDF
            pdf.generateTicket();
            //Creates the InVoice PDF
            pdf.generateInVoice();

            //Returns to the starting screen to make another ticket
            Start start = null;
            try {
                start = new Start(stage, false, CourseManager.getInstance().getUser());
            } catch (IOException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(start, start.getWidth(), start.getHeight());
            stage.setScene(scene);
            stage.show();
    }
    
    private int calculateDistance(){
        int auxDistance = 0;
        for(int i = 0; i < pathPoints.size() - 1; i++){
                Vertex<Point> aux = CourseManager.getInstance().checkPoint( pathPoints.get(i).getId());
                for(Edge<Conection, Point> edge: CourseManager.getInstance().getGraph().ascendentEdges(aux)){
                    if(pathPoints.get(i + 1).getName().equalsIgnoreCase(edge.vertices()[1].element().getName())){
                        auxDistance += edge.element().getDistance();
                    }
                }
            }
            for(int i = 0; i < pathPoints1.size() - 1; i++){
                Vertex<Point> aux = CourseManager.getInstance().checkPoint(pathPoints1.get(i).getId());
                for(Edge<Conection, Point> edge: CourseManager.getInstance().getGraph().ascendentEdges(aux)){
                    if(pathPoints1.get(i + 1).getName().equalsIgnoreCase(edge.vertices()[1].element().getName())){
                        auxDistance += edge.element().getDistance();
                    }
                }
            }
            return auxDistance;
    }
    
    private int calculateCost(){
        int auxCost = 0;
        for(int i = 0; i < pathPoints.size() - 1; i++){
                Vertex<Point> aux = CourseManager.getInstance().checkPoint( pathPoints.get(i).getId());
                for(Edge<Conection, Point> edge: CourseManager.getInstance().getGraph().ascendentEdges(aux)){
                    if(pathPoints.get(i + 1).getName().equalsIgnoreCase(edge.vertices()[1].element().getName())){
                        auxCost += edge.element().getCost();
                    }
                }
            }
            for(int i = 0; i < pathPoints1.size() - 1; i++){
                Vertex<Point> aux = CourseManager.getInstance().checkPoint(pathPoints1.get(i).getId());
                for(Edge<Conection, Point> edge: CourseManager.getInstance().getGraph().ascendentEdges(aux)){
                    if(pathPoints1.get(i + 1).getName().equalsIgnoreCase(edge.vertices()[1].element().getName())){
                        auxCost += edge.element().getCost();
                    }
                }
            }
            return auxCost;
    }
    
    private void setPreviousValues(Map map, ListView<String> listDisponiveis, ListView<String> listSelecionados, int totalDistance, int totalCost){
        map.setValues(listDisponiveis, listSelecionados, totalDistance, totalCost);
    }

    private Button createButton1(String imageName, int width, int height) {
        Image imageButton = new Image(getClass().getResourceAsStream("/resources/" + imageName), width, height, false, false);
        Button newButton = new Button("", new ImageView(imageButton));
        return newButton;
    }

    private Text createText() {
        Text newText = new Text("Sumário do seu Bilhete:");
        newText.setFill(Color.GREEN);
        newText.setFont(Font.font(25));
        return newText;
    }

    private GridPane createButtonsGrid() {
        GridPane newGrid = new GridPane();
        newGrid.setVgap(10);
        newGrid.setHgap(10);
        return newGrid;
    }

    private HBox createHBox() {
        HBox newBox = new HBox();
        newBox.setAlignment(Pos.CENTER);
        newBox.setSpacing(10);
        return newBox;
    }
}
