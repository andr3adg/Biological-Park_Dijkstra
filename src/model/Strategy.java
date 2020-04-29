/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This class contains the method to save and load statistics.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public interface Strategy {

    /**
     * This will save the currrent statistics values into a .json file.
     */
    public void save();

    /**
     * This will load the statistics values from a .json file.
     */
    public void load();
}
