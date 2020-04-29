/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Abstract class that will call the different methods to print the receipt and the invoice.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public abstract class PDFGenerator {
    
    /**
     * Empty constructor.
     */
    public PDFGenerator(){
    }
    
    /**
     * Calls the method that will create the PDF of the receipt.
     */
    public void printTicket(){
        generateTicket();
    }
    
    /**
     * Calls the method that will create the PDF of the invoice.
     */
    public void printInVoice(){
        generateInVoice();
    }
    
    /**
     * Abstract method that creates the PDF for the receipt;
     */
    public abstract void generateTicket();
    
    /**
     * Abstract method that creates the PDF for the invoice.
     */
    public abstract void generateInVoice();
}
