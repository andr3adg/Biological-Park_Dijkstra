/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * Class that allows to set and retrive the Memento values.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Caretaker {
    ArrayList<Memento> savedPaths = new ArrayList<Memento>();
    
    /**
     * Adds a memento to the Saved Paths.
     * @param memento Memento to add
     */
    public void addMemento(Memento memento){
        savedPaths.add(memento);
    }
    
    /**
     * Retrives the Memento from the Saved Paths list by it's index.
     * @param index Position to retrieve.
     * @return Memento of given position.
     */
    public Memento getMemento(int index){
        return savedPaths.get(index);
    }
}
