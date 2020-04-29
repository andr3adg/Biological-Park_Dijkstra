/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class that represents the Vertices of the graph.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Point {
    private int id;
    private String name;
    
    /**
     * Creates a variable of a Point of Interest
     * @param id Point of Interest ID
     * @param name Name of the Point Of Interest
     */
    public Point(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the Point of Interest ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the Point of Interest name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * If the parameter ID is above 0, it changes the Point of Interest ID
     * @param id New ID
     */
    public void setId(int id) {
        if(id > 0 ){
            this.id = id;
        }
    }

    /**
     * If the parameter String isn't null, it changes the Point of Interest name
     * @param name New name.
     */
    public void setName(String name) {
        if(name != null){
            this.name = name;
        }
    }
    
    @Override
    public String toString(){
        return this.getName();
    }
}