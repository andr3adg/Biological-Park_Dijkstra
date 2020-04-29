/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import java.util.HashMap;
import java.util.Set;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Class that will create a screen with a bar graphic with the number of points visited
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class BarGraphic extends StackPane{
    private HashMap<String, Integer> counters;
    
    /**
     * Creates a bar graphic with the HashMap given by parameter. Each key it's a name is a point and with the number of times it was visited
     * as it's value.
     * @param map HashMap with the values to create the Bar Graphic
     */
    public BarGraphic(HashMap<String, Integer> map) {
        this.counters = map;
        CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Parque Biológico - Estatísticas");
        xAxis.setLabel("Ponto de Interesse");       
        yAxis.setLabel("Nr de Visitas");
        
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Número de visitas");
        Set<String> keys = counters.keySet();
        
        for(String key: keys){
            series1.getData().add(new XYChart.Data(key, counters.get(key)));
        }
        
        bc.getData().addAll(series1);  
        
        getChildren().add(bc);
    }
        
}
