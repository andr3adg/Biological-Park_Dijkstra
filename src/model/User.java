/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * This class represents the user using the application.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class User implements Serializable{
    private String username;
    private String password;
    private int nif;
    private List<TicketSaver> ticketList;

    /**
     * Creates the user class by requesting an username, a password and a NIF(not manadatory)
     * @param username Username.
     * @param password Password.
     * @param nif NIF.
     * @throws IOException If the file to later save the User doesn't exist.
     */
    public User(String username, String password, int nif) throws IOException {
        this.username = username;
        this.password = password;
        this.nif = nif;
        this.ticketList = new ArrayList<>();
    }
    
    /**
     * Constructor that will start all the variables as empty.
     */
    public User(){
        username = "";
        password = "";
        nif = 0;
        this.ticketList = new ArrayList<>();
    }
    
    /**
     * Adds a ticket to the user list of tickets.
     * @param t Ticket to add.
     */
    public void addTicket(TicketSaver t){
        ticketList.add(t);
    }

    /**
     * Returns the user's username.
     * @return Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password.
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's NIF.
     * @return NIF.
     */
    public int getNif() {
        return nif;
    }

    /**
     * Returns the list of tickets that the user ahs created.
     * @return User's ticket list.
     */
    public List<TicketSaver> getTicketList() {
        return ticketList;
    }

    /**
     * Changes the user's username to the one given by the parameter.
     * @param username New username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Changes the user's password to the one given by the parameter.
     * @param password New password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Changes the user's NIF to the one given by the parameter.
     * @param nif New NIF.
     */
    public void setNif(int nif) {
        this.nif = nif;
    }

    /**
     * Changes the user's list of tickets to the one given by the parameter.
     * @param ticketList New list of tickets.
     */
    public void setTicketList(List<TicketSaver> ticketList) {
        this.ticketList = ticketList;
    }
    
    /**
     * Changes all of the user's information.
     * @param username New username.
     * @param password New password.
     * @param nif New NIF.
     * @param list New list of tickets.
     */
    public void setInformation(String username, String password, int nif, List<TicketSaver> list){
        setUsername(username);
        setPassword(password);
        setNif(nif);
        if(!list.isEmpty()){
            for(TicketSaver t: list){
                this.ticketList.add(t);
            }
        }
    }
}
