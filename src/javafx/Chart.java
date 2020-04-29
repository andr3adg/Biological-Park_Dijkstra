/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Class that will create a screen with a Pie Chart with the percentage of bike ridden tickets and not bike ridden tickets.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Chart extends StackPane{
    
    /**
     * Creates the screen with the values it was given.
     * @param data1 Percentage of not bike ridden tickets.
     * @param data2 Percentage of bike ridden tickets.
     */
    public Chart(double data1, double data2) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
            new PieChart.Data("Trajeto Pedestre: " + data1 + "%", data1),
            new PieChart.Data("Trajeto Bicicleta: " + data2 + "%", data2));
        
        PieChart pChart = new PieChart(pieData);
        pChart.setTitle("Estatísticas: Tipo de Percurso");

        
        getChildren().add(pChart);
    }
        
}
