package model;

import graph.DiWeightedGraph;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import model.TypeConection;

/**
 * Class that will read the .dat file and will insert the point's and conection's to the graph for later use.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class Generator {
    private DiWeightedGraph<Point, Conection> graph;
    private CourseManager course;
    private String mapName;
    private int totalConections;
    private int totalPoints;
    User user;

    /**
     * Creates a variable of Generator
     * @param user User that will be using the application.
     * @param mapName Name of the file
     * @throws FileNotFoundException If the file does not exist.
     * @throws IOException If the file doesn not exist.
     */
    public Generator(String mapName, User user) throws FileNotFoundException, IOException{
        this.course = CourseManager.getInstance();
        course.setUser(user);
        this.mapName = mapName;
        readMap();
    }
    
    private void readMap() throws FileNotFoundException, IOException{
        
        FileInputStream fileStream = new FileInputStream(mapName);
        BufferedReader buff = new BufferedReader(new InputStreamReader(fileStream));
        
        String strLine;
        String[] variableData;
        boolean aux = true;
        boolean aux2 = true;
        
        while((strLine = buff.readLine()) != null){
            
            if(strLine.contains(",")){
                variableData = strLine.split(",");
                if (aux){
                    course.addPoint(new Point(getInt(variableData[0]), variableData[1].substring(1)));
                } else {             
                    int ponto1 = getInt(variableData[3].substring(1));
                    int ponto2 = getInt(variableData[4].substring(1));
                    course.addConection(ponto1, ponto2, new Conection(getInt(variableData[0]), variableData[2].substring(1), 
                            getInt(variableData[6].substring(1)), getInt(variableData[7].substring(1)), Boolean.valueOf(variableData[5].substring(1)),
                            TypeConection.valueOf(variableData[1].substring(1))));  
                
                }
                
            } else if (totalPoints == 0) {
                totalPoints = Character.getNumericValue(strLine.charAt(0));
                
                
            } else if (totalConections == 0) {
                String aux1 = "" + Character.getNumericValue(strLine.charAt(0)) + Character.getNumericValue(strLine.charAt(1));
                totalConections = Integer.parseInt(aux1);
                aux = false;
                
                
            }              
            
        }
        
        buff.close();
    }
    
    /**
     * Returns the graph variable.
     * @return Graph variable.
     */
    public CourseManager getCourse(){
        return course;
    }
    
    /**
     * Returns an Integer variable of the given String variable.
     * @param conteudo String to convert
     * @return Conversion result.
     */
    private int getInt(String conteudo) {
        return Integer.parseInt(conteudo);
    }
    
}