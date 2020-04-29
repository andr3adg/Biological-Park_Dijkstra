/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

/**
 * Error class for edges
 * @author brunomnsilva
 */
public class InvalidEdgeException extends RuntimeException {

    /**
     * Display Message
     */
    public InvalidEdgeException() {
        super("The edge is invalid or does not belong to this graph.");
    }

    /**
     * Message Output
     * @param string Error message
     */
    public InvalidEdgeException(String string) {
        super(string);
    }
    
}
