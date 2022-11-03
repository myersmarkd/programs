/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import information.Appointment;
import information.Contact;
import information.Customer;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.BiPredicate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * Reports are generated and displayed in a text area for the user to read.
 * The lambda expression intMatch compares two integers to see if they are equal.
 * Using the lambda expression cuts down on repetitive code by calling the same
 * expression from multiple methods.
 *
 * @author myers
 */
public class ReportsController implements Initializable {
    
    static BiPredicate<Integer, Integer> intMatch = (s, t) -> s == t;
    
    @FXML
    private TextArea reportTextArea;

    @FXML
    private Button backButton;

    /**
     * An event on this button will open the main menu.
     * 
     * @param event 
     */
    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage().equals("fr"))
        {
            stage.setTitle("Planificateur de rendez-vous");
        }
        else
        {
            stage.setTitle("Appointment Scheduler");
        }
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
     * 
     * Report one will display the types of appointments that are scheduled within
     * a month, and the number of occurrences of those types.  Report 2 will display
     * the contacts and the appointments that they have scheduled.  Report 3 will
     * display the customer name and how many appointments they have scheduled. 
     * Lambda intMatch is called to compare two integers.  Utilizing the lambda 
     * helps to reduce typos in duplicate code by referencing the correct code
     * from multiple lines of code.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        StringBuilder report1 = new StringBuilder();
        StringBuilder report2 = new StringBuilder();
        StringBuilder report3 = new StringBuilder();
        String reports = "";
        
        //Appointment types report
        report1.append("**Appointment Types in Upcoming Month**\n\n");
        
        int counter = 0;
        ArrayList<String> apptList = new ArrayList<>();
        
        for(Appointment appointment : Appointment.getApptList())
        {
            LocalDateTime nextAppt = appointment.getStartDateTime().toLocalDateTime();
            if(nextAppt.isBefore(LocalDateTime.now().plusMonths(1)))
            {
                if (apptList.contains(appointment.getType()))
                {
                    continue;
                }
                else
                {
                    for(Appointment appt : Appointment.getApptList())
                    {
                        if (appointment.getType().equals(appt.getType()))
                        {
                            counter = counter + 1;
                            apptList.add(appointment.getType());
                        }
                    }
                }
            
                report1.append("Type: " + appointment.getType() + "\t\tOccurrences: " +
                        counter + "\n");
            }
            
            counter = 0;
        }
        
        //Contact schedule report
        report2.append("\n**Contact Schedule Report**\n\n");
        
        for (Contact contact : Contact.getContactList())
        {
            report2.append("Contact: " + contact.getContactID() + ": " + contact.getContactName() + "\n");
            for (Appointment appointment : Appointment.getApptList())
            {
                if (intMatch.test(appointment.getContactID(), contact.getContactID()))
                {
                    LocalDateTime startDateTime = appointment.getStartDateTime().toLocalDateTime();
                    String formattedStart = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    LocalDateTime endDateTime = appointment.getEndDateTime().toLocalDateTime();
                    String formattedEnd = endDateTime.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm"));
                    report2.append("\tAppt ID: " + appointment.getApptID() + "\tTitle: " +
                            appointment.getTitle() + "\t\tType: " + appointment.getType() +
                            "\t\tDescription: " + appointment.getDescription() + "\n");
                    report2.append("\tStart: " + formattedStart + "\tEnd: " + formattedEnd + "\t\tCustomer: " +
                            appointment.getCustomerID() + "\n\n");
                }
            }
            System.out.println();
        }
        
        //Customer appointments report
        report3.append("**Customer Appointment Occurences**\n\n");
        System.out.println();
        
        counter = 0;
        
        for (Customer customer : Customer.getCustomerList())
        {
            for (Appointment appointment : Appointment.getApptList())
            {
                if (intMatch.test(customer.getCustomerID(), appointment.getCustomerID()))
                {
                    counter = counter + 1;
                }
            }
            report3.append("Customer: " + customer.getCustomerID() + ": " + customer.getName() +
                    "\t\tOccurrences: " + counter + "\n\n");
            
            counter = 0;
        }
        reports = report1.toString() + report2.toString() + report3.toString();
        reportTextArea.setText(reports);
    }    
    
}
