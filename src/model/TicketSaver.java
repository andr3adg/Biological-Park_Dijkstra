package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ListView;

/**
 * This class is the representaion of a ticket. It contains the list of points to visit, distance, cost and navigability.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class TicketSaver implements Serializable{
    private List<String> list = new ArrayList<>();
    private double distance;
    private double cost;
    private boolean bike;
    private String date;
    
    /**
     * Creates the class by inserting the outward path and return path into one list, and inserting the other given values into the
     * variables.
     * @param lista1 Outward Path.
     * @param lista2 Return Path.
     * @param distance Distance of the ticket.
     * @param cost Cost of the ticket.
     * @param bike Navigability of the ticket.
     */
    public TicketSaver(List<String> lista1, List<String> lista2, double distance, double cost, boolean bike){
        for (String aux: lista1){
           list.add(aux);
        }     
        for (String aux: lista2){
           list.add(aux);
        }
        LocalDateTime localDateTime = LocalDateTime.now();//For reference
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        date = localDateTime.format(formatter1);
        this.distance = distance;
        this.cost = cost;
        this.bike = bike;
    }
    
    /**
     * Returns the navigability of the ticket.
     * @return Navigability.
     */
    public boolean getBike(){
        return bike;
    }
        
    /**
     * This method will count the times a point with the name given by parameter was visited in this ticket.
     * @param name Name of the point.
     * @return Amount of points the point was visited.
     */
    public int countExistingPoints(String name){
        int counter = 0;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equalsIgnoreCase(name)){
                counter++;
            }
        }
        return counter;
    }

    /**
     * List of points.
     * @return List of points.
     */
    public List<String> getList() {
        return list;
    }

    /**
     * Returns the cost of the ticket.
     * @return Cost of the ticket.
     */
    public double getCost() {
        return cost;
    }
}
