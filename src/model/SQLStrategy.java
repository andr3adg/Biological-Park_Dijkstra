/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class that will save the values of the application in a SQL Database.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class SQLStrategy implements Strategy{
    private Statistics statistic;
    
    /**
     * Creates the class.
     * @param s Statistics to save.
     */
    public SQLStrategy(Statistics s) {
        this.statistic = s;
    }

    /**
     * Method not implemented.
     */
    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Method not implemented.
     */
    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
