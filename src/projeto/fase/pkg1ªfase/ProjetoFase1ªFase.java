package projeto.fase.pkg1ªfase;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.Map;
import java.util.Scanner;
import javafx.Login;
import javafx.Start;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Conection;
import model.TypeConection;
import model.CourseManager;
import model.CourseManager;
import model.Generator;
import model.Point;
import model.Statistics;
import model.TicketSaver;
import model.User;
import model.UserDAO;
import model.UserDAOOneJson;

/**
 * Main class. Will start the login screen and after that will create the Start class to start the creation of a ticket.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class ProjetoFase1ªFase extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {    
        Stage stage1 = new Stage(); 
        User user = new User();
        Login login = new Login(stage1, user);
        Scene scene1 = new Scene(login, login.getWidth(), login.getHeight());
        stage1.setTitle("Login");
        Image image = new Image("/resources/icon.jpg");
        stage1.getIcons().add(image);
        stage1.setScene(scene1);
        stage1.showAndWait();
        
        Start start = new Start(primaryStage, true, user);
        Scene scene = new Scene(start, start.getWidth(), start.getHeight());
        primaryStage.setTitle("Parque Biológico - Bilheteira");
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}