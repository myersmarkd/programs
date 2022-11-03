/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import information.Appointment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.*;
import java.util.Locale;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.Alerts;

/**
 * FXML Controller class
 * 
 * The AllAppointmentController will display all of the appointments in the database
 * regardless of which user it is assigned to.
 *
 * @author myers
 */
public class AllAppointmentsController implements Initializable {
    
    @FXML
    private TableView<Appointment> allAppointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> apptColumn;

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
    private TableColumn<Appointment, Integer> customerColumn;

    @FXML
    private TableColumn<Appointment, Integer> contactColumn;

    @FXML
    private TableColumn<Appointment, Integer> userColumn;

    @FXML
    private Label allAppointmentsLabel;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button viewSelectedButton;
    
    @FXML
    private Button customerMenuButton;

    private static Appointment selectedAppointment;
    
    public static Appointment getSelectedAppointment()
    {
        return selectedAppointment;
    }

    /**
     * An event on this button will open the main menu.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void mainMenuButtonAction(ActionEvent event) throws IOException {
                
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
     * An event on this button will open the customer menu.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void customerMenuButtonAction(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * An event on this button will display the information of the appointment in
     * a different screen making all fields easy to read.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void viewSelectedButtonAction(ActionEvent event) throws IOException {
        selectedAppointment = allAppointmentsTable.getSelectionModel().getSelectedItem();
        
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
     * Initializes the controller class.
     * 
     * The table is populated with appointments.  If a user is coming to this page
     * from the customer menu with a selected customer, it will show all of the
     * appointments for that customer.  Otherwise, it will display all of the
     * appointments in the system regardless of customer or user.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Check to see if a customer has been selected
        if (CustomerMenuController.isCustomer == true)
        {   //Add all appointments for selected customer to table
            allAppointmentsLabel.setText("All Appointments for Customer " + 
                    CustomerMenuController.getSelectedCustomer().getCustomerID());
            allAppointmentsTable.setItems(Appointment.getApptListByCustomer());
            apptColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            userColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
            
            CustomerMenuController.isCustomer = false;
        }
        else
        {   //Add all appointments to table
            allAppointmentsTable.setItems(Appointment.getApptList());
            apptColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            userColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        }
    }    
    
}
