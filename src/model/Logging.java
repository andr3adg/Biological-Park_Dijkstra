/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.itextpdf.text.Paragraph;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class the will proovide the register the activity of the user durint his use of the application.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public final class Logging {
    private static Logging instance = new Logging();
    private String date;

    private Logging() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        date = localDateTime.format(formatter1);
    }

    /**
     * Returns the instance of the current Logging class.
     * @return Current instance.
     */
    public static Logging getInstance() {
        return instance;
    }

    /**
     * Creates a piece of text to inform that a ticket was emitted at a certain time by a certain user.
     */
    public void saveEmission() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("myfile.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            if (prop.getProperty("emissao").equalsIgnoreCase("true")) {

                FileWriter fw = null;
                try {
                    fw = new FileWriter("logging.txt", true);
                    fw.write("Emissão de bilhete");
                    LocalDateTime localDateTime = LocalDateTime.now();//For reference
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String formattedString1 = localDateTime.format(formatter1);
                    fw.write("\nDate: " + formattedString1);
                    fw.write("\nAUTHOR: " + CourseManager.getInstance().getUser().getUsername() + "\n\n");
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Creates a piece of text to inform that the statistics were consulted at a certain time by a certain user.
     */
    public void saveStatistics() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("myfile.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            if (prop.getProperty("estatistica").equalsIgnoreCase("true")) {

                FileWriter fw = null;
                try {
                    fw = new FileWriter("logging.txt", true);
                    fw.write("Consulta estatística");
                    LocalDateTime localDateTime = LocalDateTime.now();//For reference
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String formattedString1 = localDateTime.format(formatter1);
                    fw.write("\nDate: " + formattedString1);
                    fw.write("\nAUTHOR: " + CourseManager.getInstance().getUser().getUsername() + "\n\n");
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Creates a piece of text to inform that a a ticket was created at a certain time by a certain user.
     */
    public void saveCalculate() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("myfile.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            if (prop.getProperty("calculo").equalsIgnoreCase("true")) {

                FileWriter fw = null;
                try {
                    fw = new FileWriter("logging.txt", true);
                    fw.write("Cálculo de percurso");
                    LocalDateTime localDateTime = LocalDateTime.now();//For reference
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String formattedString1 = localDateTime.format(formatter1);
                    fw.write("\nDate: " + formattedString1);
                    fw.write("\nAUTHOR: " + CourseManager.getInstance().getUser().getUsername() + "\n\n");
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
