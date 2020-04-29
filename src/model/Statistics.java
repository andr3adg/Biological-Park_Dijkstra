package model;

import graph.Vertex;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.BarGraphic;
import javafx.Chart;
import javafx.AverageCost;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This class will handle the statistics side of the applciation.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public final class Statistics implements Serializable {

    private static Statistics instance = new Statistics();
    private List<TicketSaver> lista;

    private Statistics() {
        lista = new ArrayList<>();
    }

    /**
     * Returns the instance of the current Statistics class.
     * @return Current instance.
     */
    public static Statistics getInstance() {
        return instance;
    }

    /**
     * This method will load the list it was given by parameter into the list of statistics inside this class.
     * @param list List to add into the class list.
     */
    public void loadList(List<Statistics> list) {
        lista.clear();
        for (Statistics s : list) {
            List<TicketSaver> list1 = s.getList();
            for (TicketSaver t : list1) {
                lista.add(t);
            }
        }
    }

    /**
     * This method will load the list it was given by parameter into the list of tickets inside this class.
     * @param list List to add into the class list.
     */
    public void loadList1(List<TicketSaver> list) {
        lista.clear();
        for (TicketSaver t : list) {
            lista.add(t);
        }
    }

    /**
     * Returns the list of tickets inside this class.
     * @return List of tickets.
     */
    public List<TicketSaver> getList() {
        return lista;
    }

    /**
     * Adds a ticket into the list and it will initialize this list if necessary.
     * @param ticket Ticket to add.
     */
    public void addTicket(TicketSaver ticket) {
        if (lista == null) {
            lista = new ArrayList<>();
        }
        lista.add(ticket);
    }

    /**
     * This method will create the pop-up screen that will show the average cost of trips.
     */
    public void getAverageCost() {
        if (lista.size() != 0) {
            int cost = 0;
            for (TicketSaver aux : lista) {
                cost += aux.getCost();
            }

            Logging.getInstance().saveStatistics();

            Stage stage = new Stage();
            AverageCost distance = new AverageCost(round(cost / lista.size(), 2));
            Scene scene1 = new Scene(distance, distance.getWidth(), distance.getHeight());
            stage.setTitle("Custo Médio");
            Image image = new Image("/resources/icon.jpg");
            stage.getIcons().add(image);
            stage.setScene(scene1);
            stage.showAndWait();
        }
    }

    /**
     * This method will create the pop-up screen that show the graphic bar with the amount of times each point was visited.
     */
    public void mostVisitedPoints() {
        if (lista.size() != 0) {
            List<String> availablePoints = new ArrayList<>();
            for (Vertex<Point> p : CourseManager.getInstance().getGraph().vertices()) {
                availablePoints.add(p.element().getName());
            }

            HashMap<String, Integer> counters = new HashMap<String, Integer>();

            for (String s : availablePoints) {
                int counter = 0;
                for (TicketSaver t : lista) {
                    counter += t.countExistingPoints(s);
                }
                if (counter != 0) {
                    counters.put(s, counter);
                }
            }

            Logging.getInstance().saveStatistics();

            if (!counters.isEmpty()) {
                Stage stage = new Stage();
                BarGraphic bar = new BarGraphic(counters);
                Scene scene1 = new Scene(bar, bar.getWidth(), bar.getHeight());
                stage.setTitle("Gráfico de Barras");
                Image image = new Image("/resources/icon.jpg");
                stage.getIcons().add(image);
                stage.setScene(scene1);
                stage.showAndWait();
            }
        }
    }

    /**
     * This method will create a pop-up screen that will show a Pie Chart with the percentage of bike ridden tickets and not
     * bike ridden tickets.
     */
    public void pieChart() {
        if (lista.size() != 0) {
            int counterBike = 0;
            int counterNotBike = 0;
            for (TicketSaver t : lista) {
                if (t.getBike()) {
                    counterBike++;
                } else {
                    counterNotBike++;
                }
            }
            double percentageBike = 0;
            double percentageNotBike = 0;

            percentageBike = (counterBike * 100) / lista.size();
            percentageNotBike = (counterNotBike * 100) / lista.size();

            Logging.getInstance().saveStatistics();

            Stage stage = new Stage();
            Chart pieChart = new Chart(percentageNotBike, percentageBike);
            Scene scene1 = new Scene(pieChart, pieChart.getWidth(), pieChart.getHeight());
            stage.setTitle("Gráfico de Pizza");
            Image image = new Image("/resources/icon.jpg");
            stage.getIcons().add(image);
            stage.setScene(scene1);
            stage.showAndWait();
        }
    }

    private static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * This will save the currrent statistics values into a .json file.
     */
    public void save() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("myfile.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (prop.getProperty("serializable").equalsIgnoreCase("true")) {
            SerializableStrategy serializable = new SerializableStrategy();
            serializable.save();
        } else if (prop.getProperty("sql").equalsIgnoreCase("true")) {
            SQLStrategy sql = new SQLStrategy(this);
            sql.save();
        }
    }

    /**
     * This will load the statistics values from a .json file.
     */
    public void load() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("myfile.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (prop.getProperty("serializable").equalsIgnoreCase("true")) {
            SerializableStrategy serializable = new SerializableStrategy();
            serializable.load();
        } else if (prop.getProperty("sql").equalsIgnoreCase("true")) {
            SQLStrategy sql = new SQLStrategy(this);
            sql.load();
        }
    }
}
