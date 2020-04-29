/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.ListView;

/**
 * Changes the values of the variables inside of the memento.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Originator {
    private ListView<String> listDisponiveis;
    private ListView<String> listSelecionados;
    private int totalDistance;
    private int totalCost;

    /**
     * Changes the list of the points that the user can visit
     * @param listDisponiveis New list of points to replace the old one.
     */
    public void setListDisponiveis(ListView<String> listDisponiveis) {
        this.listDisponiveis = listDisponiveis;
    }

    /**
     * Changes the list of points that the user wants to visit.
     * @param listSelecionados New list of points to replace the old one.
     */
    public void setListSelecionados(ListView<String> listSelecionados) {
        this.listSelecionados = listSelecionados;
    }

    /**
     * Changes the value of the total distance.
     * @param totalDistance New total distance to replace the old one.
     */
    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * Changes the value of the total cost.
     * @param totalCost New total cost to replace the old one.
     */
    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
    
    /**
     * Stores the values of this class as a new Memento
     * @return New Memento.
     */
    public Memento storeInMemento(){
        return new Memento(listDisponiveis, listSelecionados, totalDistance, totalCost);
    }
    
    /**
     * Returns the list of avaiable points to visit from the given memento.
     * @param memento Memento to retrive the list form.
     * @return List of avaiable points to visit.
     */
    public ListView<String> restoreListFromMemento1(Memento memento){
        listDisponiveis = memento.getListDisponiveis();
        return listDisponiveis;
    }
    
    /**
     * Returns the list of points that the user want to visit from the given memento.
     * @param memento Memento to retrieve the list from.
     * @return List of points that the user wants to visit.
     */
    public ListView<String> restoreListFromMemento2(Memento memento){
        listSelecionados = memento.getListSelecionados();
        return listSelecionados;
    }
    
    /**
     * Returns the total distance from a given memento.
     * @param memento Memento to retrieve the value from.
     * @return Total Distance.
     */
    public int restoreTotalDistance(Memento memento){
        totalDistance = memento.getTotalDistance();
        return totalDistance;
    }
    
    /**
     * Returns the total cost from a given memento.
     * @param memento Memento to retrieve the value from.
     * @return Total Cost.
     */
    public int restoreTotalCost(Memento memento){
        totalCost = memento.getTotalCost();
        return totalCost;
    }
}
