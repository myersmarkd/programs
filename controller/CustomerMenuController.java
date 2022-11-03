/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import information.Appointment;
import information.Customer;
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
import java.util.Iterator;
import java.util.Locale;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.Alerts;
import utility.DBPopulateLists;
import utility.DBSQLCalls;

/**
 * FXML Controller class
 * 
 * The CustomerMenuController allows a user to add, update, or delete a customer.
 * The user also has the option to view all appointments in the system, or they 
 * can select a specific customer and view all of the appointments for that specific
 * customer.
 *
 * @author myers
 */
public class CustomerMenuController implements Initializable {

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    
    @FXML
    private TableColumn<Customer, String> stateProvinceColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private Button backButton;
    
    @FXML
    private Button addCustomerButton;

    @FXML
    private Button updateCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Label customerLabel;

    @FXML
    private Button viewSelectedApptButton;

    @FXML
    private Button viewAllApptButton;
    
    public static boolean isCustomer = false;
    
    private static Customer selectedCustomer;
    
    public static Customer getSelectedCustomer()
    {
        return selectedCustomer;
    }

    /**
     * An event on this button will open the window for a user to add a customer.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void addCustomerButtonAction(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage().equals("fr"))
        {
            stage.setTitle("Ajouter un client");
        }
        else
        {
            stage.setTitle("Add Customer");
        }
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    /**
     * An event on this button will check to make sure that the user has selected
     * a customer in the table.  If not, an appropriate alert is provided.  Otherwise,
     * an iterator goes through the list of the customers appointments and deletes
     * the appointments assigned to the customer being deleted.  After the appointments
     * are deleted from the database, the customer is then deleted from the database.
     * @param event 
     */
    @FXML
    void deleteCustomerButtonAction(ActionEvent event) {
        //Get the selected customer from the table
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        //Check to make sure a customer has been selected, otherwise give an alert
        if (selectedCustomer == null) 
        {
            Alerts.selectionError();
        }
        else 
        {
            if (Alerts.deleteConfirm())
            {
                for (Iterator<Appointment> iterator = Appointment.getApptList().iterator(); iterator.hasNext();)
                {
                    Appointment appointment = iterator.next();
                    if (appointment.getCustomerID() == selectedCustomer.getCustomerID())
                    {
                        DBSQLCalls.deleteAppointment(appointment.getApptID());

                        iterator.remove();
                    }
                }
                //Call to delete a customer from the DB
                DBSQLCalls.deleteCustomer(selectedCustomer.getCustomerID());
                //Call to delete a customer from the customer list
                Customer.deleteCustomer(selectedCustomer);
                
                Alerts.deleteSuccess();
            }
        }
    }

    /**
     * An event on this button will check to ensure that the user has selected a
     * customer to update.  If not, an appropriate alert is provided.  Otherwise,
     * the update customer window opens populated with information based on the 
     * customer that was selected.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void updateCustomerButtonAction(ActionEvent event) throws IOException {
        
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        
        if (selectedCustomer == null)
        {
            Alerts.selectionError();
        }
        else
        {
            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            if (Locale.getDefault().getLanguage().equals("fr"))
            {
                stage.setTitle("Mettre à jour le client");
            }
            else
            {
                stage.setTitle("Update Customer");
            }
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    /**
     * An event on this button with open a window for the user to view all of the
     * appointments in the database.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void viewAllApptButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AllAppointments.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * An event on this button will check to make sure that the user has selected
     * a customer.  If not, an appropriate alert is provided.  Otherwise, a window
     * opens with a table displaying all of the appointments assigned to the selected
     * customer.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void viewSelectedApptButtonAction(ActionEvent event) throws IOException {
        
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        
        if (selectedCustomer == null)
        {
            Alerts.selectionError();
        }
        else
        {
            isCustomer = true;
            Parent root = FXMLLoader.load(getClass().getResource("/view/AllAppointments.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    /**
     * An event on this button will return the user to the main menu.
     * 
     * @param event
     * @throws IOException 
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
     * A list of the customers in the database are populated in the table.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Clear customer list to update table from DB information to avoid duplicates
        Customer.clearCustomerList();
        
        //Get updated customer information from DB
        DBPopulateLists.populateCustomerList();        

        //Retrieve information from customer observable list and add to table
        customerTable.setItems(Customer.getCustomerList());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        stateProvinceColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }    
    
}
