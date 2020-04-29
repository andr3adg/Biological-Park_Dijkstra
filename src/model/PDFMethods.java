package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import graph.Edge;
import graph.Vertex;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class that contains the methods to create the PDF for the receipt and the invoice.
 * @author Duarte Gonçalves 170221028
 * @author André Gonçalves 170221015
 */
public class PDFMethods extends PDFGenerator{
    private List<Point> pathPoints1;
    private List<Point> pathPoints2;
    private int NIF;
    private int distance;
    private int cost;
    private boolean bike;
    
    /**
     * Initializes the class with the values that it needs to create the receipt and the invoice.
     * @param pathPoints1 Outward Path.
     * @param pathPoints2 Return Path.
     * @param nif NIF of the user.
     * @param distance Distance of the ticket.
     * @param cost Cost of the ticket.
     * @param bike Boolean variable to know if the user is riding a bike or not.
     */
    public PDFMethods(List<Point> pathPoints1, List<Point> pathPoints2, int nif, int distance, int cost, boolean bike){
        this.pathPoints1 = pathPoints1;
        this.pathPoints2 = pathPoints2;
        this.NIF = nif;
        this.distance = distance;
        this.cost = cost;
        this.bike = bike;
    }
    
    /**
     * Generates the PDF for the receipt.
     */
    @Override
    public void generateTicket(){
        Logging.getInstance().saveEmission();
        Document document = new Document();
        String filename = "";
        try {
            LocalDate localDate = LocalDate.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            String formattedString = localDate.format(formatter);
            filename = "" + NIF + "_" + formattedString + "_bilhete.pdf";
            File file = new File("C:\\Users\\Duarte Gonçalves\\Desktop\\Projeto 2ª Fase\\" + filename); 
            file.delete();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph("***************************************************************************************************************"));
            LocalDateTime localDateTime = LocalDateTime.now();//For reference
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString1 = localDateTime.format(formatter1);
            document.add(new Paragraph(" Time of print: " + formattedString1));
            
            if(bike){
                document.add(new Paragraph("Type of path: Bike"));
            } else {
                document.add(new Paragraph("Type of path: By foot"));
            }
            
            document.add(new Paragraph("---- Outward Voyage ----"));
            for(int i = 0; i < pathPoints1.size() - 1; i++){
                document.add(new Paragraph("  " + pathPoints1.get(i).getName()));
                Vertex<Point> aux = CourseManager.getInstance().checkPoint( pathPoints1.get(i).getId());
                for(Edge<Conection, Point> edge: CourseManager.getInstance().getGraph().ascendentEdges(aux)){
                    if(pathPoints1.get(i + 1).getName().equalsIgnoreCase(edge.vertices()[1].element().getName())){
                        document.add(new Paragraph("  " + edge.element().toString1()));
                    }
                }
            }
            document.add(new Paragraph("---- Return Voyage ----"));
            for(int i = 0; i < pathPoints2.size() - 1; i++){
                document.add(new Paragraph("  " + pathPoints2.get(i).getName()));
                Vertex<Point> aux = CourseManager.getInstance().checkPoint(pathPoints2.get(i).getId());
                for(Edge<Conection, Point> edge: CourseManager.getInstance().getGraph().ascendentEdges(aux)){
                    if(pathPoints2.get(i + 1).getName().equalsIgnoreCase(edge.vertices()[1].element().getName())){
                        document.add(new Paragraph("  " + edge.element().toString1()));
                    }
                }
            }
            document.add(new Paragraph("***************************************************************************************************************"));
            
        } catch (FileNotFoundException | DocumentException ex) {
          //System.out.println("Error:" + ex);
            
        }finally{
            document.close();
        }
        
        try {
            Desktop.getDesktop().open(new File(filename));
        } catch (IOException ex) {
            System.out.println("Error:" + ex);
        }
    }
    
    /**
     * Generates the PDF for the Invoice.
     */
    @Override
    public void generateInVoice(){
        Document document = new Document();
        String filename = "";
        try {
            LocalDate localDate = LocalDate.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            String formattedString = localDate.format(formatter);
            filename = "" + NIF + "_" + formattedString + "_fatura.pdf";
            File file = new File("C:\\Users\\Duarte Gonçalves\\Desktop\\Projeto 2ª Fase\\" + filename); 
            file.delete();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph("***************************************************************************************************************"));
            
            if(NIF != 0){
                document.add(new Paragraph(" NIF: " + NIF));
            } else {
                document.add(new Paragraph(" NIF: Non-existing"));
            }
            document.add(new Paragraph(" "));
            
            LocalDateTime localDateTime = LocalDateTime.now();//For reference
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString1 = localDateTime.format(formatter1);
            document.add(new Paragraph(" Time of print: " + formattedString1));
            document.add(new Paragraph(" Distance: " + distance));
            document.add(new Paragraph(" Cost: " + cost));
            document.add(new Paragraph("***************************************************************************************************************"));
            
        } catch (FileNotFoundException | DocumentException ex) {
          System.out.println("Error:" + ex);
            
        }finally{
            document.close();
        }
        
        try {
            Desktop.getDesktop().open(new File(filename));
        } catch (IOException ex) {
            //System.out.println("Error:" + ex);
        }
    }
}
