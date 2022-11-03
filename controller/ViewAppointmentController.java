/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import information.Contact;
import information.Customer;
import information.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 * 
 * The ViewAppointmentController displays a selected appointment in an easy to read
 * window.
 *
 * @author myers
 */
public class ViewAppointmentController implements Initializable {
    
    @FXML
    private Label apptIDLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Label endLabel;

    @FXML
    private Label customerLabel;

    @FXML
    private Label contactLabel;

    @FXML
    private Label apptDisplayLabel;

    @FXML
    private Label titleDisplayLabel;

    @FXML
    private Label descriptionDisplayLabel;

    @FXML
    private Label typeDisplayLabel;

    @FXML
    private Label locationDisplayLabel;

    @FXML
    private Label startDateDisplayLabel;

    @FXML
    private Label startTimeDisplayLabel;

    @FXML
    private Label endDateDisplayLabel;

    @FXML
    private Label endTimeDisplayLabel;

    @FXML
    private Label customerDisplayLabel;

    @FXML
    private Label contactDisplayLabel;
    
    @FXML
    private Label userLabel;

    @FXML
    private Label userDisplayLabel;

    @FXML
    private Button backButton;

    /**
     * An event on this button closes the window.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void backButtonAction(ActionEvent event) throws IOException {

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    /**
     * Initializes the controller class.
     * 
     * Labels are populated with information based on what appointment the user
     * selected.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        if (AllAppointmentsController.getSelectedAppointment() != null)
        {
            apptDisplayLabel.setText(Integer.toString(AllAppointmentsController.getSelectedAppointment().getApptID()));
            titleDisplayLabel.setText(AllAppointmentsController.getSelectedAppointment().getTitle());
            descriptionDisplayLabel.setText(AllAppointmentsController.getSelectedAppointment().getDescription());
            typeDisplayLabel.setText(AllAppointmentsController.getSelectedAppointment().getType());
            locationDisplayLabel.setText(AllAppointmentsController.getSelectedAppointment().getLocation());
            
            LocalDateTime startDateTime = AllAppointmentsController.getSelectedAppointment().getStartDateTime().toLocalDateTime();
            LocalDateTime endDateTime = AllAppointmentsController.getSelectedAppointment().getEndDateTime().toLocalDateTime();
            LocalDate startDate = startDateTime.toLocalDate();
            LocalDate endDate = endDateTime.toLocalDate();
            LocalTime startTime = startDateTime.toLocalTime();
            LocalTime endTime = endDateTime.toLocalTime();
            
            startDateDisplayLabel.setText(startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            endDateDisplayLabel.setText(endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            startTimeDisplayLabel.setText(startTime.toString());
            endTimeDisplayLabel.setText(endTime.toString());
            
            for (Customer customer : Customer.getCustomerList())
            {
                if (customer.getCustomerID() == AllAppointmentsController.getSelectedAppointment().getCustomerID())
                {
                    customerDisplayLabel.setText(customer.getCustomerID() + ": " + customer.getName());
                    break;
                }
            }
            
            for (Contact contact : Contact.getContactList())
            {
                if (contact.getContactID() == AllAppointmentsController.getSelectedAppointment().getContactID())
                {
                    contactDisplayLabel.setText(contact.getContactID() + ": " + contact.getContactName());
                    break;
                }
            }
            
            for (User user : User.getUserList())
            {
                if (user.getUserID() == AllAppointmentsController.getSelectedAppointment().getUserID())
                {
                    userDisplayLabel.setText(user.getUserID() + ": " + user.getUserName());
                    break;
                }
            }
        }
        else
        {
            apptDisplayLabel.setText(Integer.toString(MainMenuController.getSelectedAppointment().getApptID()));
            titleDisplayLabel.setText(MainMenuController.getSelectedAppointment().getTitle());
            descriptionDisplayLabel.setText(MainMenuController.getSelectedAppointment().getDescription());
            typeDisplayLabel.setText(MainMenuController.getSelectedAppointment().getType());
            locationDisplayLabel.setText(MainMenuController.getSelectedAppointment().getLocation());
            
            LocalDateTime startDateTime = MainMenuController.getSelectedAppointment().getStartDateTime().toLocalDateTime();
            LocalDateTime endDateTime = MainMenuController.getSelectedAppointment().getEndDateTime().toLocalDateTime();
            LocalDate startDate = startDateTime.toLocalDate();
            LocalDate endDate = endDateTime.toLocalDate();
            LocalTime startTime = startDateTime.toLocalTime();
            LocalTime endTime = endDateTime.toLocalTime();
            
            startDateDisplayLabel.setText(startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            endDateDisplayLabel.setText(endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            startTimeDisplayLabel.setText(startTime.toString());
            endTimeDisplayLabel.setText(endTime.toString());
            
            for (Customer customer : Customer.getCustomerList())
            {
                if (customer.getCustomerID() == MainMenuController.getSelectedAppointment().getCustomerID())
                {
                    customerDisplayLabel.setText(customer.getCustomerID() + ": " + customer.getName());
                    break;
                }
            }
            
            for (Contact contact : Contact.getContactList())
            {
                if (contact.getContactID() == MainMenuController.getSelectedAppointment().getContactID())
                {
                    contactDisplayLabel.setText(contact.getContactID() + ": " + contact.getContactName());
                    break;
                }
            }
            
            for (User user : User.getUserList())
            {
                if (user.getUserID() == MainMenuController.getSelectedAppointment().getUserID())
                {
                    userDisplayLabel.setText(user.getUserID() + ": " + user.getUserName());
                    break;
                }
            }
        }
        
    }    
    
}
