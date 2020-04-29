package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This class handles the user's .json file.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public final class UserDAOOneJson implements UserDAO {
    private static UserDAOOneJson instance = new UserDAOOneJson();
    private static final String fileName = "users.json";

    private UserDAOOneJson() {}

    /**
     * Returns the current instance of this class.
     * @return Current instance.
     */
    public static UserDAOOneJson getInstance(){
        return instance;
    }
    
    private HashSet<User> selectAll() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            Gson gson = new GsonBuilder().create();
            HashSet<User> list = gson.fromJson(br,new TypeToken<HashSet<User>>(){}.getType());
            return list;
        } catch (IOException ex) {
            Logger.getLogger(UserDAOOneJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashSet<>();
    }

    /**
     * Tries to load a user that has the same parameters as the ones given by parameter.
     * @param username User's username.
     * @param password User's password.
     * @param nif User's NIF.
     * @return If is null it couldn't find the user and it returns the User value if it finds it.
     */
    public User loadUser(String username, String password, int nif) {
        HashSet<User> list = selectAll();
        if(list == null) list = new HashSet<User>();
        for (User t : list) {
            if (t.getUsername().equalsIgnoreCase(username) && t.getPassword().equalsIgnoreCase(password)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Saves the user given by parameter into the .json file.
     * @param t User to save
     */
    @Override
    public void saveUser(User t) {
        FileWriter writer = null;
        try {
            Gson gson = new GsonBuilder().create();
            HashSet<User> list = selectAll();
            if(list == null) list = new HashSet<User>();
            User currentUser = loadUser(t.getUsername(), t.getPassword(), t.getNif());
            if(currentUser == null){
                list.add(t);
            }
            writer = new FileWriter(fileName);
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(UserDAOOneJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method will first try call the load method with the user given by parameter and if it return's as null, it will know
     * that the user is new to the application and save to the .json file.
     * @param t User to save
     */
    public void saveUser1(User t) {
        FileWriter writer = null;
        try {
            Gson gson = new GsonBuilder().create();
            HashSet<User> list = selectAll();
            if(list == null) list = new HashSet<User>();
            User currentUser = loadUser(t.getUsername(), t.getPassword(), t.getNif());
            if(currentUser != null){
                Iterator<User> i = list.iterator();
                while (i.hasNext()) {
                    User o = i.next();
                    if(o.getUsername().equalsIgnoreCase(t.getUsername())){
                        i.remove();
                    }
                }
                list.add(t);
            }
            writer = new FileWriter(fileName);
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(UserDAOOneJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}