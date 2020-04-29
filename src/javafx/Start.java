package javafx;

import graph.Edge;
import graph.Vertex;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Conection;
import model.Generator;
import model.Point;
import model.Statistics;
import model.TicketSaver;
import model.User;
import projeto.fase.pkg1ªfase.GraphDraw;

/**
 * Screen that will let the user choose if he wants to choose the path by himself or if he wants a premade one.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Start extends BorderPane{
    private Generator generator;
    private boolean check;
    
    private MenuBar menu;
    private Text header, header1;
    private Button btnPersonalizado, btnPreDefinido, btnAdicionar, btnRemover, btnFinalizar;
    private VBox boxTop, boxCenter, boxTop1, boxBottom;
    private GridPane gridPane, grid;
    private ListView<String> listDisponiveis, listSelecionados;
    private Label labelPreco, labelDistancia;
    private TextField textPreco, textDistancia;
    private Stage stage;
    Statistics saver;
    User user;
    
    /**
     * It will read the map text, to populate the graph, and it will then draw the graph
     * @param stage Stage where the screen will be shown
     * @param check Boolean variable to check it the graphs needs to be drawn or not
     * @param user User that will be using the program.
     * @throws IOException If the file doesn't exist.
     */
    public Start(Stage stage, boolean check, User user) throws IOException{         
        this.user = user;
        this.check = check;
        //Reads the map .dat file
        readMap();
        this.stage = stage;
        //Creates the screen
        createStartScreen(); 
        if(check == true){
           //Creates the graph drawing
           GraphDraw draw = new GraphDraw();
        }
    }
    
    private void readMap(){
        Properties prop = new Properties();
	InputStream input = null;
        try {
		input = new FileInputStream("myfile.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
                if(prop.getProperty("mapa0.dat").equalsIgnoreCase("true")){
                   if(check == true){
                        this.generator = new Generator("mapa0.dat", user);
                    }
                } else if(prop.getProperty("mapa1.dat").equalsIgnoreCase("true")){
                   if(check == true){
                        this.generator = new Generator("mapa1.dat", user);
                    }
                } else if(prop.getProperty("mapa2.dat").equalsIgnoreCase("true")){
                   if(check == true){
                        this.generator = new Generator("mapa2.dat", user);
                    }
                } else if(prop.getProperty("mapa3.dat").equalsIgnoreCase("true")){
                   if(check == true){
                        this.generator = new Generator("mapa3.dat", user);
                    }
                }
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
	}
    }
    
    private void createStartScreen(){
        setWidth(500);
        setHeight(500);
        
        menu = createMenuBar();
        
        //Button to create a personalized path
        btnPersonalizado = createButton("Trajeto Personalizado");
        btnPersonalizado.setOnAction(e-> {
            Path path = new Path(stage);
            Scene scene = new Scene(path, path.getWidth(), path.getHeight());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        });
        
        //Button to create a pre-made path
        btnPreDefinido = createButton("Trajeto Pré - Definido");  
        btnPreDefinido.setOnAction(e-> {
            Path path = new Path(stage);
            Scene scene = new Scene(path, path.getWidth(), path.getHeight());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        });
        
        gridPane = createButtonsGrid();
        
        boxCenter = createVBox();
        boxCenter.getChildren().add(gridPane);
        
        boxTop = createVBox();        
        header = createText();
        boxTop.getChildren().add(menu);
        boxTop.getChildren().add(header);
        
        setCenter(boxCenter);  
        setTop(boxTop);
        
        StackPane left = new StackPane();
        left.setPrefWidth(150);
        setLeft(left);
        StackPane right = new StackPane();
        right.setPrefWidth(100);
        setRight(right);
    }
    
    private Button createButton(String texto){
        Button btn = new Button(texto);
        btn.getStyleClass().add("button1");
        btn.setPadding(new Insets(15, 15, 15, 15));
        btn.setMinWidth(200);
        btn.setMinHeight(75);
        return btn;
    }
    
    private GridPane createButtonsGrid(){
        GridPane newGrid = new GridPane();
        newGrid.setVgap(30);
        newGrid.setHgap(20);
        newGrid.add(btnPersonalizado, 0, 0);
        newGrid.add(btnPreDefinido, 0, 1);
        return newGrid;
    }
    
    private VBox createVBox(){
        VBox newBox = new VBox();
        newBox.setAlignment(Pos.CENTER);
        newBox.setSpacing(10);
        return newBox;
    }
    
    private Text createText(){
        Text newText = new Text("Escolha o seu tipo de trajeto:");
        newText.setFill(Color.GREEN);
        newText.setFont(Font.font (25));
        return newText;
    }
    
    private MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();
        Menu menuStatistics = new Menu("Estatísticas");
        MenuItem menuItem1 = new MenuItem("Gráfico de Barras");
        MenuItem menuItem2 = new MenuItem("Média de Custo");
        MenuItem menuItem3 = new MenuItem("Gráfico de Pizza");
        menuItem1.setOnAction(e-> {
            Statistics.getInstance().load();
            Statistics.getInstance().mostVisitedPoints();
        });
        menuItem2.setOnAction(e-> {
            //Statistics.getInstance().load(StatisticsDAOOneJson.getInstance().loadStatistics());
            Statistics.getInstance().load();
            Statistics.getInstance().getAverageCost();
        });
        menuItem3.setOnAction(e-> {
            //Statistics.getInstance().load(StatisticsDAOOneJson.getInstance().loadStatistics());
            Statistics.getInstance().load();
            Statistics.getInstance().pieChart();
        });
        menuStatistics.getItems().addAll(menuItem1, menuItem2, menuItem3);
        menuBar.getMenus().addAll(menuStatistics);
        return menuBar;
    }
}
