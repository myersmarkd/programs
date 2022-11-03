/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import information.Appointment;
import information.Contact;
import information.Customer;
import information.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import utility.Alerts;
import utility.DBSQLCalls;

/**
 * FXML Controller class
 *
 * The UpdateAppointmentController allows the user to update the information of
 * an existing appointment.  All boxes are populated with the information based 
 * on the selection the user made on the main menu.
 * 
 * @author myers
 */
public class UpdateAppointmentController implements Initializable {
    
    @FXML
    private Label updateAppointmentLabel;
    
    @FXML
    private Label idLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label contactLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private TextField idTextBox;

    @FXML
    private TextField titleTextBox;

    @FXML
    private TextField descriptionTextBox;

    @FXML
    private TextField locationTextBox;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private Label startDateLabel;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private Label endDateLabel;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField typeTextBox;

    @FXML
    private Label customerIDLabel;

    @FXML
    private ComboBox<Customer> customerIDComboBox;

    @FXML
    private Label userLabel;

    @FXML
    private ComboBox<User> userComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;
    
    /**
     * The cancel button action with close the window.  If any information has been
     * entered, an alert will display asking for confirmation if the user actually
     * wants to cancel and close the window or not.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {
        String title = titleTextBox.getText();
        String description = descriptionTextBox.getText();
        String location = locationTextBox.getText();
        String type = typeTextBox.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        Contact contact = contactComboBox.getSelectionModel().getSelectedItem();
        Customer customerID = customerIDComboBox.getSelectionModel().getSelectedItem();
        User user = userComboBox.getSelectionModel().getSelectedItem();

        if (title.isBlank() == false || description.isBlank() == false || location.isBlank() == false || 
                type.isBlank() == false || startDate != null || startTime != null || endDate != null ||
                endTime != null || contact != null || customerID != null || user != null)
        {
            if (Alerts.cancelConfirm())
            {
                //Close window and go back to main menu
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
        }
        else
        {
            //Close window and go back to main menu
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
    }

    /**
     * The save button action gets the information from the GUI and checks to
     * first make sure that all boxes have been filled out.  Next, it checks for
     * appointment overlap based on other appointments scheduled for the customer.
     * Next, it checks to make sure that the times and dates entered are in the
     * correct order with the start date and time happening before the end date
     * and time.  Finally, should it pass all of those checks, it will send the
     * information to the database for storage.  The window will close, and the
     * updated information will be displayed for the user.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {
        //Retrieve information from the user input
        int apptID = Integer.parseInt(idTextBox.getText());
        String title = titleTextBox.getText();
        String description = descriptionTextBox.getText();
        String location = locationTextBox.getText();
        String type = typeTextBox.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        Contact contact = contactComboBox.getSelectionModel().getSelectedItem();
        Customer customerID = customerIDComboBox.getSelectionModel().getSelectedItem();
        User user = userComboBox.getSelectionModel().getSelectedItem();
        
        //Check to make sure for is filled out in it's entirety
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() ||
                startDate == null || startTime == null || endDate == null || endTime == null ||
                     contact == null || customerID == null || user == null)
        {   
            //Provide alerts in French or English if entries are incomplete
            Alerts.incompleteAlert();
        }
        else if (Appointment.checkOverLap(startDate, startTime, endDate, endTime, customerID.getCustomerID()))
        {  
            //Provide alert in French or English if there is an overlap
            Alerts.overlapAlert();
        }
        else
        {   
            //Convert form start time and date into timestamp for DB addition
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            Timestamp startTimestamp = Timestamp.valueOf(startDateTime);

            //Convert form end time and date into timestamp for DB addition
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

            //Check to ensure end date occurs after start date
            if (endTimestamp.before(startTimestamp) || endTimestamp.equals(startTimestamp))
            {
                Alerts.timeErrorAlert();
            }
            else
            {   //Send information to the DB
                DBSQLCalls.updateAppointment(apptID, title, description, location, type, startTimestamp, 
                        endTimestamp, customerID.getCustomerID(), user.getUserID(), contact.getContactID());
                
                //Close current window and open the main mein
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
        }
    }

    /**
     * Initializes the controller class.
     *
     * Business opens at 8:00 AM EST and closes at 10:00 PM EST.  Converts
     * opening and closing hours to local times to prevent scheduling 
     * appointments outside of business hours.  Local hours are populated 
     * in the combo boxes for user selection.  All text boxes and combo boxes
     * are populated based on the selection the user made on the update screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Set start and end times for combo boxes on form
        LocalTime businessOpen = LocalTime.of(8, 0);
        ZoneId easternZone = ZoneId.of("America/New_York");
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), businessOpen);
        ZonedDateTime easternZonedTime = startDateTime.atZone(easternZone);
        ZonedDateTime localZonedTime = easternZonedTime.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localDateTime = localZonedTime.toLocalDateTime();
        LocalTime start = localDateTime.toLocalTime();
        
        LocalTime businessClose = LocalTime.of(22, 0);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), businessClose);
        easternZonedTime = endDateTime.atZone(easternZone);
        localZonedTime = easternZonedTime.withZoneSameInstant(ZoneId.systemDefault());
        localDateTime = localZonedTime.toLocalDateTime();
        LocalTime end = localDateTime.toLocalTime();
        
        //Add times every 30 minutes to start and end time combo boxes
        while (start.isBefore(end.plusSeconds(1)))
        {
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
        
        //Add information to combo boxes
        contactComboBox.setItems(Contact.getContactList());
        customerIDComboBox.setItems(Customer.getCustomerList());
        userComboBox.setItems(User.getUserList());
        
        idTextBox.setText(Integer.toString(MainMenuController.getSelectedAppointment().getApptID()));
        titleTextBox.setText(MainMenuController.getSelectedAppointment().getTitle());
        descriptionTextBox.setText(MainMenuController.getSelectedAppointment().getDescription());
        locationTextBox.setText(MainMenuController.getSelectedAppointment().getLocation());
        typeTextBox.setText(MainMenuController.getSelectedAppointment().getType());
        
        Timestamp startTimestamp = MainMenuController.getSelectedAppointment().getStartDateTime();
        LocalDateTime startDateTimestamp = startTimestamp.toLocalDateTime();
        LocalDate startDate = startDateTimestamp.toLocalDate();
        LocalTime startTime = startDateTimestamp.toLocalTime();
        startDatePicker.setValue(startDate);
        startTimeComboBox.setValue(startTime);
        
        Timestamp endTimestamp = MainMenuController.getSelectedAppointment().getEndDateTime();
        LocalDateTime endDateTimestamp = endTimestamp.toLocalDateTime();
        LocalDate endDate = endDateTimestamp.toLocalDate();
        LocalTime endTime = endDateTimestamp.toLocalTime();
        endDatePicker.setValue(endDate);
        endTimeComboBox.setValue(endTime);
        
        for (Contact contact : Contact.getContactList())
        {
            if (contact.getContactID() == MainMenuController.getSelectedAppointment().getContactID())
            {
                contactComboBox.setValue(contact);
            }
        }
        
        for (Customer customer : Customer.getCustomerList())
        {
            if (customer.getCustomerID() == MainMenuController.getSelectedAppointment().getCustomerID())
            {
                customerIDComboBox.setValue(customer);
            }
        }
        
        for (User user : User.getUserList())
        {
            if (user.getUserID() == MainMenuController.getSelectedAppointment().getUserID())
            {
                userComboBox.setValue(user);
            }
        }
        
        if (Locale.getDefault().getLanguage().equals("fr"))
        {
            rb = ResourceBundle.getBundle("utility/French", Locale.getDefault());
            updateAppointmentLabel.setText(rb.getString("Update Appointment"));
            idLabel.setText(rb.getString("ID"));
            titleLabel.setText(rb.getString("Title"));
            descriptionLabel.setText(rb.getString("Description"));
            locationLabel.setText(rb.getString("Location"));
            typeLabel.setText(rb.getString("Type"));
            startDateLabel.setText(rb.getString("Start Date/Time"));
            endDateLabel.setText(rb.getString("End Date/Time"));
            contactLabel.setText(rb.getString("Contact"));
            customerIDLabel.setText(rb.getString("Customer ID"));
            userLabel.setText(rb.getString("User"));
            saveButton.setText(rb.getString("Save"));
            cancelButton.setText(rb.getString("Cancel"));
            
        }
    }    
    
}
