/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.ListView;

/**
 * Class to save and retrive the previous values of certain elements.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Memento {
    private ListView<String> listDisponiveis;
    private ListView<String> listSelecionados;
    private int totalDistance;
    private int totalCost;

    /**
     * Saves the values given by parameter.
     * @param listDisponiveis List of points avaiable to visit.
     * @param listSelecionados List of points that the user want to visit.
     * @param totalDistance Total Distance.
     * @param totalCost Total Cost.
     */
    public Memento(ListView<String> listDisponiveis, ListView<String> listSelecionados, int totalDistance, int totalCost) {
        this.listDisponiveis = listDisponiveis;
        this.listSelecionados = listSelecionados;
        this.totalDistance = totalDistance;
        this.totalCost = totalCost;
    }

    /**
     * Returns the list of points that the user can visit.
     * @return List of Points.
     */
    public ListView<String> getListDisponiveis() {
        return listDisponiveis;
    }

    /**
     * Returns the list of points the the user want to visit.
     * @return List of points.
     */
    public ListView<String> getListSelecionados() {
        return listSelecionados;
    }

    /**
     * Returns the total distance to replace the old one.
     * @return Total distance.
     */
    public int getTotalDistance() {
        return totalDistance;
    }

    /**
     * Returns the total cost to replace the old one.
     * @return Total cost.
     */
    public int getTotalCost() {
        return totalCost;
    }
}
