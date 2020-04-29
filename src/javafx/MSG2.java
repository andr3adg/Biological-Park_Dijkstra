package javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class that creates a Pop-Up Screen to inform the user that he can't finalize the ticket whitout inserting at least 1 point to visit without counting the Entrance.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class MSG2 extends BorderPane{
    private VBox vBox;
    private Text text;
    private ImageView imageView;
    private Image image;
       
    /**
     * Screen creation
     */
    public MSG2 (){
        setWidth(600);
        setHeight(300);
        setPadding(new Insets(15, 15, 15, 15));
        vBox = createVBox();
        //Error Message
        text = createText("Tem que inserir 1 ponto para visitar");
        
        imageView = new ImageView();
        //Danger sign Image
        image = new Image(getClass().getResourceAsStream("/resources/perigo.jpg"), 60, 60, false, false); //tens que escolher uma, guardar numa pasta no projeto e por ai o diretorio
        imageView.setImage(image);
        
        vBox.getChildren().add(text);
        vBox.getChildren().add(imageView);
        
        setCenter(vBox);
    }
    
    private Text createText(String texto){
        Text newText = new Text(texto);
        newText.setFill(Color.GREEN);
        newText.setFont(Font.font (20));
        return newText;
    }
    
    private VBox createVBox(){
        VBox newBox = new VBox();
        newBox.setAlignment(Pos.CENTER);
        newBox.setSpacing(10);
        return newBox;
    }
}