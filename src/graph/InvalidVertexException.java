/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

/**
 * Error message class for vertices
 * @author brunomnsilva
 */
public class InvalidVertexException extends RuntimeException {

    /**
     * Display message
     */
    public InvalidVertexException() {
        super("The vertex is invalid or does not belong to this graph.");
    }

    /**
     * Output message
     * @param string Error message
     */
    public InvalidVertexException(String string) {
        super(string);
    }
    
}
