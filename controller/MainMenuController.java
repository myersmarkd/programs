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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import utility.DBConnection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utility.Alerts;
import utility.DBPopulateLists;
import utility.DBSQLCalls;

/**
 * FXML Controller class
 * 
 * The MainMenuController provides a table of all of the upcoming appointments for
 * the logged in user.  If the user has an appointment within 15 minutes, a message
 * is displayed on the interface providing appointment details.  The user can filter
 * the appointment table by viewing appointments within the upcoming week, the upcoming
 * month, or all appointments, which is the default.  The user can add, update, 
 * or delete appointments.  The user also has the option to view the customer
 * menu, or to view the contact menu.  They also have buttons for the user to log off
 * or to exit the program.
 *
 * @author myers
 */
public class MainMenuController implements Initializable {
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> apptIDColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> startColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> endColumn;

    @FXML
    private TableColumn<Appointment, Integer> customerIDColumn;

    @FXML
    private TableColumn<Appointment, Integer> contactColumn;

    @FXML
    private Label myAppointmentsLabel;

    @FXML
    private RadioButton weeklyRadio;

    @FXML
    private ToggleGroup radioGroup;

    @FXML
    private RadioButton monthlyRadio;
    
    @FXML
    private RadioButton allRadio;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private Button deleteAppointmentButton;
    
    @FXML
    private Button viewAllButton;

    @FXML
    private Label appointmentLabel;

    @FXML
    private Button viewCustomersButton;

    @FXML
    private Button viewContactsButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label viewLabel;
    
    @FXML
    private Label nextAppointmentLabel;
    
    @FXML
    private Button viewSelectedButton;
    
    @FXML
    private Button logOffButton;
    
    @FXML
    private Button reportsButton;
    
    private static Appointment selectedAppointment;
    
    public static Appointment getSelectedAppointment()
    {
        return selectedAppointment;
    }
    
    /**
     * An event on this button opens a new window for the user to add an appointment.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void addAppointmentButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * An event on this button will delete the selected appointment.  If nothing
     * has been selected, an appropriate alert is provided.
     * 
     * @param event 
     */
    @FXML
    void deleteAppointmentButtonAction(ActionEvent event) {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        
        if (selectedAppointment == null)
        {
            Alerts.selectionError();
        }
        else
        {
            if (Alerts.deleteConfirm())
            {
                DBSQLCalls.deleteAppointment(selectedAppointment.getApptID());
            
                Appointment.deleteAppointment(selectedAppointment);
                
                Alerts.deleteSuccess();
            }
        }
    }
    
    /**
     * An event on this button will open a new window displaying all of the appointments
     * in the database regardless of user.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void viewAllButtonAction(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/AllAppointments.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    /**
     * An event on this button will display information of the selected appointment
     * in an easy to read window.  If nothing has been selected, and appropriate
     * alert is provided.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void viewSelectedButtonAction(ActionEvent event) throws IOException {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        
        if (selectedAppointment == null)
        {
            Alerts.selectionError();
        }
        else
        {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAppointment.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * An event on this button closes the connection to the database and terminates
     * the program.
     * 
     * @param event 
     */
    @FXML
    void exitButtonAction(ActionEvent event) {
        //Close the DB connection
        DBConnection.closeConnection();
        
        //Close the program
        System.exit(0);
    }
    
    /**
     * An event on this button will take the user back to the login screen.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void logOffButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        
        if (Locale.getDefault().getLanguage().equals("fr"))
        {
            stage.setTitle("S'identifier");
        }
        else
        {
            stage.setTitle("Login");
        }
        
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    /**
     * An event on this button will display all of the user's appointments for
     * the upcoming week.
     * 
     * @param event 
     */
    @FXML
    void weeklyRadioAction(ActionEvent event) {
        ObservableList<Appointment> weekly = FXCollections.observableArrayList();
        
        for (Appointment appt : Appointment.getApptListByUser())
        {
            Timestamp start = appt.getStartDateTime();
            LocalDateTime localStart = start.toLocalDateTime();
            if (localStart.isBefore(LocalDateTime.now().plusWeeks(1)))
            {
                weekly.add(appt);
            }
        }
        
        appointmentTable.setItems(weekly);
    }

    /**
     * An event on this button with display all of the user's appointments for 
     * the upcoming month.
     * 
     * @param event 
     */
    @FXML
    void monthlyRadioAction(ActionEvent event) {
        ObservableList<Appointment> monthly = FXCollections.observableArrayList();
        
        for (Appointment appt : Appointment.getApptListByUser())
        {
            Timestamp start = appt.getStartDateTime();
            LocalDateTime localStart = start.toLocalDateTime();
            if (localStart.isBefore(LocalDateTime.now().plusMonths(1)))
            {
                monthly.add(appt);
            }
        }
        
        appointmentTable.setItems(monthly);
    }
    
    /**
     * An event on this button will display all of the user's appointments
     * regardless of time.  This is the default setting.
     * 
     * @param event 
     */
    @FXML
    void allRadioAction(ActionEvent event) {
        appointmentTable.setItems(Appointment.getApptListByUser());
    }

    /**
     * An event on this button will check to ensure that the user has selected
     * an appointment from the table.  If not, an appropriate alert is provided.
     * Otherwise, a window is opened populated with all of the information based
     * on the selected appointment.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void updateAppointmentButtonAction(ActionEvent event) throws IOException {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        
        if (selectedAppointment == null)
        {
            Alerts.selectionError();
        }
        else
        {
            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            if (Locale.getDefault().getLanguage().equals("fr"))
            {
                stage.setTitle("Mettre à jour le rendez-vous");
            }
            else
            {
                stage.setTitle("Update Appointment");
            }
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    /**
     * An event on this button will open a window displaying all of the contacts
     * in the database and their corresponding information.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void viewContactsButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    /**
     * An event on this button will open the customer menu window.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void viewCustomersButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    /**
     * An event on this button will generate three reports.  Report one will display
     * the types of appointments that are scheduled within a month, and the number
     * of occurrences of those types.  Report 2 will display the contacts and the
     * appointments that they have scheduled.  Report 3 will display the customer
     * name and how many appointments they have scheduled.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void reportsButtonAction(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
     * 
     * The appointmentTable is populated with appointments for the logged in user.
     * A message is displayed if the user is within 15 minutes of an appointment.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Create a welcome message
        if (Locale.getDefault().getLanguage().equals("fr"))
        {
            welcomeLabel.setText("Bonjour, " + LoginController.username + "!");
        }
        else
        {
            welcomeLabel.setText("Hello, " + LoginController.username + "!");
        }
        Appointment.clearApptList();
        
        DBPopulateLists.populateAppointmentList();

        appointmentTable.setItems(Appointment.getApptListByUser());
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        
                
        for (Appointment appt : Appointment.getApptListByUser())
        {
            LocalDateTime nextAppt = appt.getStartDateTime().toLocalDateTime();
            
            if (nextAppt.isBefore(LocalDateTime.now().plusMinutes(15))
                    && (nextAppt.isAfter(LocalDateTime.now())))
            {
                LocalTime nextApptTime = nextAppt.toLocalTime();
                LocalDate nextApptDate = nextAppt.toLocalDate();
                
                String dateString = nextApptDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
                
                nextAppointmentLabel.setText("You have an upcoming appointment.\n" + "Appt ID: " 
                        + appt.getApptID() + "\tAppt Time: " + nextApptTime + "\tAppt Date: " + dateString);
                
                Alerts.upcomingAppt(nextApptTime, LoginController.upcomingApptAlert);
                
                LoginController.upcomingApptAlert = true;
                
                break;
            }
            else
            {
                nextAppointmentLabel.setText("You do not have any upcoming appointments.");
            }
        }
    }
}
