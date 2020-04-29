/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class that will create a screen to show the average cost of ticket cost that have already been created
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class AverageCost extends BorderPane{
    private VBox box;
    private Text msg, dist;
    
    /**
     * Creates a screen with the distance given
     * @param distance Average cost of distances
     */
    public AverageCost(double distance){
        setHeight(400);
        setWidth(300);
        box = createVBox();
        msg = createText("Estatísticas: Custo Médio");
        dist = createText(Double.toString(distance));
        box.getChildren().add(msg);
        box.getChildren().add(dist);
        
        setCenter(box);     
    }
    
    private VBox createVBox(){
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        return box;
    }
    
    private Text createText(String texto){
        Text newText = new Text(texto);
        newText.setFill(Color.GREEN);
        newText.setFont(Font.font (20));
        return newText;
    }
}
