/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class that represents the Edges of the DiWeightedGraph. They're conectors between two Points.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Conection {
    private int id;
    private String name;
    private final TypeConection type;
    private double cost, distance;
    private final boolean navigability;

    /**
     * Creates a Conection
     * @param id Conection ID
     * @param name Conection Name
     * @param cost Conection Cost
     * @param distance Conection Distance
     * @param navigability If true it can be accesible by bike
     * @param type Bridge or Path
     */
    public Conection(int id, String name, double cost, double distance, boolean navigability, TypeConection type) {
        if(id >= 0){
            this.id = id;
        }
        if(name != null){
            this.name = name;
        }
        if(cost >= 0){
            this.cost = cost;
        }
        if(distance >= 0){
            this.distance = distance;
        }
        this.navigability = navigability;
        this.type = type;
    }

    /**
     * Returns the Conection ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the Conection Conection
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Conection cost
     * @return cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Returns the Conection Distance
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns tur if it can be accesible by bike
     * @return navigability
     */
    public boolean isNavigability() {
        return navigability;
    }

    /**
     * Returns the Conection Type(Bridge or Path)
     * @return type
     */
    public TypeConection getType() {
        return type;
    }

    /**
     * If id is above 0, the Conection ID is changed
     * @param id New ID
     */
    public void setId(int id) {
        if(id > 0){
            this.id = id;
        }
    }

    /**
     * If the name given by parameter is not null, it changes the Conection name
     * @param name New name
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
    
    /**
     * Ouputs the information of the conection to the graph drawing.
     * @return Conection Information.
     */
    public String toString1(){
        return "ID: " + getId() + "; Name: " + getName() + "; Type: " + getType() + "; Distance: " + getDistance() + "; Cost: "
                + getCost() + "; Bike Access: " + navigability;
    }
}