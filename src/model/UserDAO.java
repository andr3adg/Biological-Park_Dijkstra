/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This interface class has the methods to save and load a user.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public interface UserDAO {

    /**
     * Saves the user given by parameter into the .json file.
     * @param t User to save
     */
    public void saveUser(User t);

    /**
     * Tries to load a user that has the same parameters as the ones given by parameter.
     * @param username User's username.
     * @param password User's password.
     * @param nif User's NIF.
     * @return If is null it couldn't find the user and it returns the User value if it finds it.
     */
    public User loadUser(String username, String password, int nif);
}
