package javafx;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.BorderFactory;
import model.CourseManager;
import model.Generator;
import model.Statistics;
import model.TicketSaver;

/**
 * Class that will create a screen that will allow the user to choose if he wants to use a bike or not.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Path extends BorderPane{
    
    private Text header;
    private MenuBar menu;
    private Button btnPedestre;
    private Button btnBicicleta;
    private Button btnBack;
    private VBox boxTop;
    private VBox boxCenter;
    private GridPane gridPane;
    Stage stage;
    private Generator generator;
    
    /**
     * Screen creation.
     * @param stage Stage to show the screen 
     */
    public Path(Stage stage){ 
        this.setWidth(500);
        this.setHeight(500);
        this.stage = stage;
        this.generator = generator;
        
        menu = createMenuBar();
        
        //Button to go back to last screen
        btnBack = createButton1("back.png");
        btnBack.setOnAction(e-> {
            Start start = null;
            try {
                start = new Start(stage,  false, CourseManager.getInstance().getUser());
            } catch (IOException ex) {
                Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(start, start.getWidth(), start.getHeight());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        });
        
        //Button to go by foot
        btnPedestre = createButton("Trajeto Pedestre");
        btnPedestre.setOnAction(e-> {
            Map map = new Map(stage, false, false);
            Scene scene = new Scene(map, map.getWidth(), map.getHeight());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        });
        
        //Button to go by bike
        btnBicicleta = createButton("Trajeto de Bicicleta");  
        btnBicicleta.setOnAction(e-> {
            Map map = new Map(stage, true, false);
            Scene scene = new Scene(map, map.getWidth(), map.getHeight());
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
        boxTop.getChildren().add(btnBack); 
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
    
    private Button createButton1(String imageName){
        Image imageButton = new Image(getClass().getResourceAsStream("/resources/"+imageName), 20, 20, false, false);
        Button newButton = new Button("", new ImageView(imageButton));
        return newButton;
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
        newGrid.add(btnPedestre, 0, 0);
        newGrid.add(btnBicicleta, 0, 1);
        return newGrid;
    }
    
    private VBox createVBox(){
        VBox newBox = new VBox();
        newBox.setAlignment(Pos.CENTER);
        return newBox;
    }
    
    private Text createText(){
        Text newText = new Text("Escolha o seu tipo de deslocação:");
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
