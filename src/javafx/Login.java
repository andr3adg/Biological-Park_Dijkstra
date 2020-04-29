package javafx;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CourseManager;
import model.User;
import model.UserDAO;
import model.UserDAOOneJson;

/**
 * Class that creates the interface for the user to login, by inserting its username, password and NIF code(if wanted)
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Login extends BorderPane {

    private VBox box;
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;
    private Text textMSG;
    private Label labelUser;
    private TextField textUser;
    private Label labelPass;
    private PasswordField textPass;
    private Label labelNIF;
    private TextField textNIF;
    private Button buttonInsert;
    Stage stage;
    User user;

    /**
     * Creates the screen with fields for data insertion.
     * @param stage New Stage to open as a pop-up
     * @param user User varable to then store the value of the user after the login
     */
    public Login(Stage stage, User user) {
        setWidth(500);
        setHeight(500);
        this.stage = stage;
        this.user = user;

        setPadding(new Insets(15, 15, 15, 15));

        box = createVBox();
        hBox1 = createHBox();
        hBox2 = createHBox();
        hBox3 = createHBox();

        textMSG = createText();
        labelUser = createLabel("Nome:");
        //TextField to insert user's username
        textUser = createTextField();
        labelPass = createLabel("Palavra-Passe:");
        //PasswordField to insert user's password
        textPass = createPasswordField();
        labelNIF = createLabel("NIF:");
        //Non-Mandotory TextField to insert user's NIF
        textNIF = createTextField();

        hBox1.getChildren().add(labelUser);
        hBox1.getChildren().add(textUser);
        hBox2.getChildren().add(labelPass);
        hBox2.getChildren().add(textPass);
        hBox3.getChildren().add(labelNIF);
        hBox3.getChildren().add(textNIF);

        box.getChildren().add(hBox1);
        box.getChildren().add(hBox2);
        box.getChildren().add(hBox3);
        buttonInsert = createButton("Inserir");
        buttonInsert.setOnAction(e -> {
            login();
        });
        box.getChildren().add(buttonInsert);

        setTop(textMSG);
        setAlignment(textMSG, Pos.CENTER);
        setCenter(box);
    }

    private void login() {
        //Output from username TextField
        String username = textUser.getText();

        int nif = 0;
        //Output from NIF TextField. If the output is an empty String it returns the NIF as 0 and it assums the suer doesnt want NIF in its receipt
        if (textNIF.getText().equals("")) {
            nif = 0;
        } else {
            nif = Integer.parseInt(textNIF.getText());
        }
        //Output from Password PasswordField
        String password = textPass.getText();

        //Tries to load the user from the JSON File
        User currentUser = UserDAOOneJson.getInstance().loadUser(username, password, nif);
        User currentUser1 = null;

        if (currentUser == null) {
            try {
                UserDAOOneJson.getInstance().saveUser(new User(username, password, nif));
                currentUser1 = UserDAOOneJson.getInstance().loadUser(username, password, nif);
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Sets the Information on the Current User for future use
        if (currentUser == null) {
            user.setInformation(username, password, nif, currentUser1.getTicketList());
        } else {
            user.setInformation(username, password, nif, currentUser.getTicketList());
        }
        stage.close();
    }

    private Button createButton(String texto) {
        Button btn = new Button(texto);
        btn.getStyleClass().add("button1");
        btn.setPadding(new Insets(15, 15, 15, 15));
        btn.setMinWidth(150);
        btn.setMinHeight(75);
        return btn;
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
        newBox.setSpacing(5);
        return newBox;
    }

    private Text createText() {
        Text newText = new Text("Login (NIF Opcional):");
        newText.setFill(Color.GREEN);
        newText.setFont(Font.font(22));
        return newText;
    }

    private TextField createTextField() {
        TextField aux = new TextField();
        aux.setMinWidth(150);
        return aux;
    }

    private PasswordField createPasswordField() {
        PasswordField aux = new PasswordField();
        aux.setMinWidth(150);
        return aux;
    }

    private Label createLabel(String text) {
        Label aux = new Label(text);
        aux.setMinWidth(75);
        return aux;
    }
}
