package javafx;

import graph.Edge;
import graph.Vertex;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JRadioButton;
import model.Caretaker;
import model.Conection;
import model.CourseManager;
import model.Generator;
import model.Logging;
import model.Memento;
import model.Originator;
import model.Point;
import model.Statistics;
import model.TicketSaver;

/**
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Map extends BorderPane {

    private MenuBar menu;
    private Text header;
    private VBox boxTop;
    private VBox boxBottom;
    //List of the Points the User can visit from the Point he's in
    private ListView<String> listDisponiveis;
    //List of the Points the User plans to visit 
    private ListView<String> listSelecionados;
    private Button btnAdicionar;
    private Button btnRemover;
    private Button btnFinalizar;
    private Button btnBack;
    private GridPane grid;
    private Label labelPreco;
    private Label labelDistancia;
    private TextField textPreco;
    private TextField textDistancia;
    private RadioButton radioPreco;
    private RadioButton radioDistancia;

    Stage stage;
    private boolean bike;
    private boolean redo;
    Caretaker caretaker;
    Originator originator;

    /**
     * This class shows the Menu where the User can choose the points he plans
     * to visit.
     *
     * @param stage Stage that displays the Scene where the menu is displayed
     * @param bike Boolean variable to know if the user is using a bike or not
     * @param redo Boolean variable to know if this creen is being created for
     * its first time, or if its being created after using a Back button
     */
    public Map(Stage stage, boolean bike, boolean redo) {
        this.redo = redo;
        this.setWidth(1280);
        this.setHeight(768);
        this.bike = bike;
        this.stage = stage;
        //Caretaker to save a Memento after finishing the ticket
        this.caretaker = new Caretaker();
        originator = new Originator();

        header = createText();
        boxTop = createVBox();
        boxBottom = createVBox();
        //Button to choose if the user wants to make its path based on the lowest cost
        radioPreco = new RadioButton("Preço");
        //Button to choose if the user wants to make its path based on the lowest distance
        radioDistancia = new RadioButton("Distância");

        listDisponiveis = new ListView<String>();
        listSelecionados = new ListView<String>();

        listDisponiveis.getItems().clear();
        listSelecionados.getItems().clear();
        startup();

        //Button to go back to last screen
        btnBack = createButton1("back.png");
        btnBack.setOnAction(e -> {
            backButton();
        });

        btnAdicionar = createButton("Adicionar");
        btnAdicionar.setOnAction(e -> {
            addPoint();
        });

        btnRemover = createButton("Remover");
        btnRemover.setOnAction(e -> {
            removePoint();
        });

        btnFinalizar = createButton("Finalizar");
        btnFinalizar.setOnAction(e -> {
            finalize();
        });

        MenuBar menu = createMenuBar();
        labelPreco = new Label("Preço:");
        labelPreco.setMinWidth(70);
        labelDistancia = new Label("Distancia:");
        labelDistancia.setMinWidth(70);
        radioPreco = new RadioButton("Preço");
        radioPreco.setMaxWidth(70);
        radioDistancia = new RadioButton("Distância");
        radioDistancia.setMaxWidth(70);
        textPreco = new TextField("0");
        textDistancia = new TextField("0");

        grid = createGrid();
        boxTop.getChildren().add(menu);
        boxTop.getChildren().add(header);
        boxBottom.getChildren().add(grid);

        setLeft(btnBack);
        setTop(boxTop);
        setBottom(boxBottom);
    }

    private void backButton() {
        Path path = new Path(stage);
        Scene scene = new Scene(path, path.getWidth(), path.getHeight());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Method to create the code for the button that adds the points the user
     * want to visit.
     */
    public void addPoint() {
        if (!listSelecionados.getItems().contains(listDisponiveis.getSelectionModel().getSelectedItem())) {
            if (bike == true) {
                String name = listDisponiveis.getSelectionModel().getSelectedItem();
                Vertex<Point> pointToAdd = findVertexByName(name);

                boolean canCross = false;
                for (Edge<Conection, Point> edge : CourseManager.getInstance().getGraph().incidentEdges(pointToAdd)) {
                    if (edge.element().isNavigability()) {
                        canCross = true;
                    }
                }

                if (canCross == true) {
                    listSelecionados.getItems().add(listDisponiveis.getSelectionModel().getSelectedItem());
                    listDisponiveis.getItems().remove(listDisponiveis.getSelectionModel().getSelectedItem());

                    List<Point> aux1 = new ArrayList<>();
                    List<Point> aux2 = new ArrayList<>();
                    textDistancia.setText(Integer.toString(distance(aux1, aux2)));

                    List<Point> aux3 = new ArrayList<>();
                    List<Point> aux4 = new ArrayList<>();
                    textPreco.setText(Integer.toString(cost(aux3, aux4)));
                } else {
                    popUpErrorPath();
                }
            } else {
                listSelecionados.getItems().add(listDisponiveis.getSelectionModel().getSelectedItem());
                listDisponiveis.getItems().remove(listDisponiveis.getSelectionModel().getSelectedItem());

                List<Point> aux1 = new ArrayList<>();
                List<Point> aux2 = new ArrayList<>();
                textDistancia.setText(Integer.toString(distance(aux1, aux2)));

                List<Point> aux3 = new ArrayList<>();
                List<Point> aux4 = new ArrayList<>();
                textPreco.setText(Integer.toString(cost(aux3, aux4)));
            }
        }
    }

    /**
     * Method to create the code for the button that removes points from the
     * list of points the user want to visit.
     */
    public void removePoint() {
        listDisponiveis.getItems().add(listSelecionados.getSelectionModel().getSelectedItem());
        listSelecionados.getItems().remove(listSelecionados.getSelectionModel().getSelectedItem());
        List<Point> aux1 = new ArrayList<>();
        List<Point> aux2 = new ArrayList<>();
        textDistancia.setText(Integer.toString(distance(aux1, aux2)));

        List<Point> aux3 = new ArrayList<>();
        List<Point> aux4 = new ArrayList<>();
        textPreco.setText(Integer.toString(cost(aux3, aux4)));
    }

    /*
     * Method that creates the code for the button that will finalize the process of chossing the points for the final path output.
     */
    public void finalize() {
        List<Point> finalDistancePath1 = new ArrayList<>();
        List<Point> finalDistancePath2 = new ArrayList<>();
        List<Point> finalCostPath1 = new ArrayList<>();
        List<Point> finalCostPath2 = new ArrayList<>();

        //Updates the distances now counting with values of the return trip
        distance(finalDistancePath1, finalDistancePath2);
        cost(finalCostPath1, finalCostPath2);

        //Saves the time of finalization to the logging.txt
        Logging.getInstance().saveCalculate();

        //Memento to save the lists of the ticket, in case the user wants to return to this screen
        originator.setListDisponiveis(listDisponiveis);
        originator.setListSelecionados(listSelecionados);
        originator.setTotalDistance(Integer.parseInt(textDistancia.getText()));
        originator.setTotalCost(Integer.parseInt(textPreco.getText()));
        caretaker.addMemento(originator.storeInMemento());

        if (!checkAmountOfPoints()) {
            popUpErrorPoints();
        } else if (radioDistancia.isSelected() && checkAmountOfPoints()) {
            //Creates ticket with the path the user wants to visit using the distance
            createTicketScreen(finalDistancePath1, finalDistancePath2, bike, 0, caretaker);
        } else if (radioPreco.isSelected() && checkAmountOfPoints()) {
            //Creates ticket with the path the user wants to visit using the cost
            createTicketScreen(finalCostPath1, finalCostPath2, bike, 1, caretaker);
        }
    }

    private void createTicketScreen(List<Point> list1, List<Point> list2, boolean bike, int criteria, Caretaker caretaker) {
        Ticket ticket = new Ticket(stage, list1, list2, bike, criteria, caretaker);
        Scene scene = new Scene(ticket, ticket.getWidth(), ticket.getHeight());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private boolean checkAmountOfPoints() {
        if (listSelecionados.getItems().size() >= 1) {
            return true;
        }
        return false;
    }

    /**
     * Inserts the name of all the possible points that the user can visit into
     * the left-side ListView.
     */
    public void startup() {
        if (redo == false) {
            for (Vertex<Point> p : CourseManager.getInstance().getGraph().vertices()) {
                if (!p.element().getName().equalsIgnoreCase("Entrada") && !listDisponiveis.getItems().contains(p.element().getName())) {
                    listDisponiveis.getItems().add(p.element().getName());
                }
            }
        }
    }

    /**
     * Method that will create a pop-up screen if the user is trying to insert a
     * point that he can't visit due to navigabilit.
     */
    public void popUpErrorPath() {
        MSG1 warning = new MSG1();
        Scene scene = new Scene(warning, warning.getWidth(), warning.getHeight());
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.show();
    }

    /**
     * Method that will create a pop-up screen if the user didn't insert at
     * least one point to visit.
     */
    public void popUpErrorPoints() {
        //Warning Message if the user as less than 3 points
        MSG2 warning = new MSG2();
        Scene scene = new Scene(warning, warning.getWidth(), warning.getHeight());
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.show();
    }

    /**
     * Changes the TextField with the given value
     *
     * @param distance New Distance
     */
    public void setDistance(int distance) {
        textDistancia.setText(Integer.toString(distance));
    }

    /**
     * Changes the TextField with the given value
     *
     * @param cost New Cost
     */
    public void setCost(int cost) {
        textPreco.setText(Integer.toString(cost));
    }

    /**
     * Changes the list of avaiable points to a new list
     *
     * @param newListDisponiveis New List
     */
    public void setListDisponiveis(ListView<String> newListDisponiveis) {
        for (int i = 0; i < newListDisponiveis.getItems().size(); i++) {
            listDisponiveis.getItems().add(newListDisponiveis.getItems().get(i));
        }
    }

    /**
     * Changes the list of selected points to a new list
     *
     * @param newListSelecionados New List
     */
    public void setListSelecionados(ListView<String> newListSelecionados) {
        for (int i = 0; i < newListSelecionados.getItems().size(); i++) {
            listSelecionados.getItems().add(newListSelecionados.getItems().get(i));
        }
    }

    private Button createButton1(String imageName) {
        Image imageButton = new Image(getClass().getResourceAsStream("/resources/" + imageName), 20, 20, false, false);
        Button newButton = new Button("", new ImageView(imageButton));
        return newButton;
    }

    private Edge<Conection, Point> getEdge(String name) {
        Edge<Conection, Point> returnEdge = null;

        String lastPoint = listSelecionados.getItems().get(listSelecionados.getItems().size() - 1);
        for (Edge<Conection, Point> edge : CourseManager.getInstance().getGraph().edges()) {
            if (edge.vertices()[0].element().getName().equalsIgnoreCase(lastPoint) && edge.vertices()[1].element().getName().equalsIgnoreCase(name)) {
                returnEdge = edge;
            }
        }

        return returnEdge;
    }

    private int distance(List<Point> outwardPath, List<Point> returnPath) {
        ArrayList<String> pointsToVisit = new ArrayList<>();
        for (int i = 0; i < listSelecionados.getItems().size(); i++) {
            pointsToVisit.add(listSelecionados.getItems().get(i));
        }

        int totalDistance = 0;
        outwardPath.add(CourseManager.getInstance().checkPoint(1).element());

        List<Point> path1 = new ArrayList<>();
        Point lastPoint = null;
        int size = pointsToVisit.size();
        for (int j = 0; j < size; j++) {
            path1.clear();
            if (!pointsToVisit.isEmpty()) {
                if (j == 0) {
                    totalDistance += CourseManager.getInstance().minimumCostPath(CourseManager.getInstance().checkPoint(1), pointToVisitDistance(pointsToVisit, lastPoint), path1, 0, bike);
                    lastPoint = path1.get(path1.size() - 1);
                    for (int k = 0; k < pointsToVisit.size(); k++) {
                        for (int l = 0; l < path1.size(); l++) {
                            if (pointsToVisit.get(k).equalsIgnoreCase(path1.get(l).getName())) {
                                pointsToVisit.remove(k);
                            }
                        }
                    }
                } else {
                    totalDistance += CourseManager.getInstance().minimumCostPath(CourseManager.getInstance().checkPoint(lastPoint.getId()), pointToVisitDistance(pointsToVisit, lastPoint), path1, 0, bike);
                    lastPoint = path1.get(path1.size() - 1);
                    for (int k = 0; k < pointsToVisit.size(); k++) {
                        for (int l = 0; l < path1.size(); l++) {
                            if (pointsToVisit.get(k).equalsIgnoreCase(path1.get(l).getName())) {
                                pointsToVisit.remove(k);
                            }
                        }
                    }
                }
                for (int m = 0; m < path1.size(); m++) {
                    if (m != 0) {
                        outwardPath.add(path1.get(m));
                    }
                }
            }
        }
        totalDistance += CourseManager.getInstance().minimumCostPath(CourseManager.getInstance().checkPoint(lastPoint.getId()), CourseManager.getInstance().checkPoint(1), path1, 0, bike);
        for (Point p : path1) {
            returnPath.add(p);
        }
        return totalDistance;
    }

    private Vertex<Point> pointToVisitDistance(ArrayList<String> pointsToVisit, Point lastPoint) {
        if (lastPoint == null) {
            return findVertexByName(pointsToVisit.get(findShortestPath(CourseManager.getInstance().checkPoint(1).element(), pointsToVisit)));
        } else {
            return findVertexByName(pointsToVisit.get(findShortestPath(lastPoint, pointsToVisit)));
        }
    }

    private int cost(List<Point> outwardPath, List<Point> returnPath) {
        ArrayList<String> pointsToVisit = new ArrayList<>();
        for (int i = 0; i < listSelecionados.getItems().size(); i++) {
            pointsToVisit.add(listSelecionados.getItems().get(i));
        }

        int totalCost = 0;
        outwardPath.add(CourseManager.getInstance().checkPoint(1).element());

        List<Point> path1 = new ArrayList<>();
        Point lastPoint = null;
        int size = pointsToVisit.size();
        for (int j = 0; j < size; j++) {
            path1.clear();
            if (!pointsToVisit.isEmpty()) {
                if (j == 0) {
                    totalCost += CourseManager.getInstance().minimumCostPath(CourseManager.getInstance().checkPoint(1), pointToVisitCost(pointsToVisit, lastPoint), path1, 1, bike);
                    lastPoint = path1.get(path1.size() - 1);
                    for (int k = 0; k < pointsToVisit.size(); k++) {
                        for (int l = 0; l < path1.size(); l++) {
                            if (pointsToVisit.get(k).equalsIgnoreCase(path1.get(l).getName())) {
                                pointsToVisit.remove(k);
                            }
                        }
                    }
                } else {
                    totalCost += CourseManager.getInstance().minimumCostPath(CourseManager.getInstance().checkPoint(lastPoint.getId()), pointToVisitCost(pointsToVisit, lastPoint), path1, 1, bike);
                    lastPoint = path1.get(path1.size() - 1);
                    for (int l = 0; l < path1.size(); l++) {
                        if (/*pointsToVisit.get(k).equalsIgnoreCase(path1.get(l).getName())*/pointsToVisit.contains(path1.get(l).getName())) {
                            pointsToVisit.remove(path1.get(l).getName());
                        }
                    }
                }
                for (int m = 0; m < path1.size(); m++) {
                    if (m != 0) {
                        outwardPath.add(path1.get(m));
                    }
                }
            }
        }
        totalCost += CourseManager.getInstance().minimumCostPath(CourseManager.getInstance().checkPoint(lastPoint.getId()), CourseManager.getInstance().checkPoint(1), path1, 1, bike);
        for (Point p : path1) {
            returnPath.add(p);
        }
        return totalCost;
    }

    private Vertex<Point> pointToVisitCost(ArrayList<String> pointsToVisit, Point lastPoint) {
        if (lastPoint == null) {
            return findVertexByName(pointsToVisit.get(findCheapestPath(CourseManager.getInstance().checkPoint(1).element(), pointsToVisit)));
        } else {
            return findVertexByName(pointsToVisit.get(findCheapestPath(lastPoint, pointsToVisit)));
        }
    }

    /**
     * Method that outputs the position of the closest path in the list given by
     * parameter
     *
     * @param beggining Point that the user is currently in.
     * @param list List of points to visit.
     * @return Position of the closest point.
     */
    public int findShortestPath(Point beggining, ArrayList<String> list) {
        int distance = 0;
        int returnPosition = 0;
        List<Point> path1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            path1.clear();
            int auxDistance = 0;
            auxDistance += CourseManager.getInstance().minimumCostPath(CourseManager.getInstance().checkPoint(beggining.getId()), findVertexByName(list.get(i)), path1, 0, bike);
            if (i == 0 || auxDistance < distance) {
                distance = auxDistance;
                returnPosition = i;
            }
        }
        return returnPosition;
    }

    /**
     * Method that outputs the position of the cheapest path in the list given
     * by parameter
     *
     * @param beggining Point that the user is currently in.
     * @param list List of points to visit.
     * @return Position of the cheapest point.
     */
    public int findCheapestPath(Point beggining, ArrayList<String> list) {
        int distance = 0;
        int returnPosition = 0;
        List<Point> path1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            path1.clear();
            int auxDistance = 0;
            auxDistance += CourseManager.getInstance().minimumCostPath(CourseManager.getInstance().checkPoint(beggining.getId()), findVertexByName(list.get(i)), path1, 1, bike);
            if (i == 0 || auxDistance < distance) {
                distance = auxDistance;
                returnPosition = i;
            }
        }
        return returnPosition;
    }

    private Vertex<Point> findVertexByName(String name) {
        Vertex<Point> aux = null;
        for (Vertex<Point> v : CourseManager.getInstance().getGraph().vertices()) {
            if (v.element().getName().equalsIgnoreCase(name)) {
                aux = v;
            }
        }
        return aux;
    }

    /**
     * Method that wil reset all fields of the screen. It will call all the
     * necessary Set methods.
     *
     * @param listDisponiveis ListView of avaible points to visit to replace the
     * old one.
     * @param listSelecionados ListView of points that the user want's to visit
     * to replace the old one.
     * @param totalDistance TotalDistance to replace the older one.
     * @param totalCost TotalCost to replace the older one.
     */
    public void setValues(ListView<String> listDisponiveis, ListView<String> listSelecionados, int totalDistance, int totalCost) {
        setListDisponiveis(listDisponiveis);
        setListSelecionados(listSelecionados);
        setDistance(totalDistance);
        setCost(totalCost);
    }

    private Button createButton(String texto) {
        Button btn = new Button(texto);
        btn.getStyleClass().add("button1");
        btn.setPadding(new Insets(15, 15, 15, 15));
        btn.setMinWidth(200);
        btn.setMinHeight(75);
        return btn;
    }

    private Text createText() {
        Text newText = new Text("Personalize o seu percurso:");
        newText.setFill(Color.GREEN);
        newText.setFont(Font.font(25));
        return newText;
    }

    private VBox createVBox() {
        VBox newBox = new VBox();
        newBox.setAlignment(Pos.CENTER);
        newBox.setSpacing(10);
        return newBox;
    }

    private HBox createHBox() {
        HBox newBox = new HBox();
        newBox.setAlignment(Pos.CENTER);
        newBox.setSpacing(10);
        return newBox;
    }

    private GridPane createGrid() {
        GridPane newGrid = new GridPane();
        newGrid.setAlignment(Pos.CENTER);
        newGrid.setPadding(new Insets(0, 0, 25, 0));
        newGrid.setVgap(30);
        newGrid.setHgap(20);
        VBox newVBox = createVBox();
        HBox newHBox1 = createHBox();
        HBox newHBox2 = createHBox();

        newHBox1.getChildren().add(labelDistancia);
        newHBox1.getChildren().add(textDistancia);
        newHBox2.getChildren().add(labelPreco);
        newHBox2.getChildren().add(textPreco);

        newVBox.getChildren().add(newHBox1);
        newVBox.getChildren().add(newHBox2);
        newVBox.getChildren().add(radioDistancia);
        newVBox.getChildren().add(radioPreco);

        newGrid.add(listDisponiveis, 0, 0);
        newGrid.add(listSelecionados, 1, 0);
        newGrid.add(newVBox, 2, 0);
        newGrid.add(btnAdicionar, 0, 1);
        newGrid.add(btnRemover, 1, 1);
        newGrid.add(btnFinalizar, 2, 1);
        return newGrid;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuStatistics = new Menu("Estatísticas");
        MenuItem menuItem1 = new MenuItem("Gráfico de Barras");
        MenuItem menuItem2 = new MenuItem("Média de Custo");
        MenuItem menuItem3 = new MenuItem("Gráfico de Pizza");
        menuItem1.setOnAction(e -> {
            Statistics.getInstance().load();
            Statistics.getInstance().mostVisitedPoints();
        });
        menuItem2.setOnAction(e -> {
            Statistics.getInstance().load();
            Statistics.getInstance().getAverageCost();
        });
        menuItem3.setOnAction(e -> {
            Statistics.getInstance().load();
            Statistics.getInstance().pieChart();
        });
        menuStatistics.getItems().addAll(menuItem1, menuItem2, menuItem3);
        menuBar.getMenus().addAll(menuStatistics);
        return menuBar;
    }
}
