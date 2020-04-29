package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that will save the values of the application in .bin file.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class SerializableStrategy implements Strategy {

    /**
     * Empty constructor.
     */
    public SerializableStrategy() {
    }

    /**
     * Method that will save the current statistics values in the statistics.bin file.
     */
    public void save() {
        String filename = "statistics.bin";
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(new FileOutputStream(filename));
            os.writeObject(Statistics.getInstance().getList());
            os.close();
        } catch (IOException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Method that will load the statistics values from the statistics.bin file into the program.
     */
    public void load() {
        String filename = "statistics.bin";
        ObjectInputStream is = null;
        try {
            if (is != null) {
                is = new ObjectInputStream(new FileInputStream(filename));
                List<TicketSaver> s = null;
                try {
                    s = (List<TicketSaver>) is.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
                }
                Statistics.getInstance().loadList1(s);
                is.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
