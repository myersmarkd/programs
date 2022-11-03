/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import information.Country;
import information.Customer;
import information.Division;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.Alerts;
import utility.DBConnection;
import utility.DBSQLCalls;

/**
 * FXML Controller class
 * 
 * The UpdateCustomerController allows the user to update a customer's information
 * in the database.  The text boxes will be populated with information based on
 * the selection the customer made in the customer menu.
 *
 * @author myers
 */
public class UpdateCustomerController implements Initializable {
    
    @FXML
    private Label updateCustomerLabel;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> divisionComboBox;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField addressTextBox;

    @FXML
    private Label postalCodeLabel;

    @FXML
    private TextField postalCodeTextBox;

    @FXML
    private TextField phoneTextBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private TextField customerIDTextBox;
    
    private Country country;
    
    
    /**
     * The division combo box will be blank until a country is selected in the 
     * country combo box.  Upon that event, the program will retrieve the corresponding
     * divisions and populate the combo box.
     * 
     * @param event 
     */
    @FXML
    void countryComboBoxAction(ActionEvent event) {
        
        int countryID = 0;
        
        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        try (var divisions = DBConnection.getConnection().prepareStatement("SELECT * FROM first_level_divisions"))
        {   //Submit query to DB
            var divisionSet = divisions.executeQuery();
            //Loop through results of query        
            while (divisionSet.next())
            {
                //Retrieve data from information from DB
                String division = divisionSet.getString("Division");
                int divisionCountryID = divisionSet.getInt("Country_ID");
                
                for (Country con : Country.getCountryList())
                {   //Get country selection and compare to country list
                    if (countryComboBox.getSelectionModel().getSelectedItem().equals(con.getName()))
                    {
                        countryID = con.getID();
                    }

                }
                //Check that selected country ID matches country ID from DB
                if (divisionCountryID == countryID)
                {
                   //Add division to list
                   allDivisions.add(division);
                }

            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        //Sort divisions and add to combo box
        divisionComboBox.setItems(allDivisions.sorted());
    }

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
        String country = countryComboBox.getSelectionModel().getSelectedItem();
        String division = divisionComboBox.getSelectionModel().getSelectedItem();
        String name = nameTextBox.getText();
        String address = addressTextBox.getText();
        String postalCode = postalCodeTextBox.getText();
        String phone = phoneTextBox.getText();
        if (country != null || division != null || name.isBlank() == false || address.isBlank() == false || 
                postalCode.isBlank() == false || phone.isBlank() == false)
        {
            if (Alerts.cancelConfirm())
            {
                //Close window and go back to main menu
                Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        }
        else
        {
            //Close window and go back to main menu
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    /**
     * The save button action will get the information from the form and check
     * to ensure that nothing has been left blank.  If it fails, and alert will 
     * pop up.  If it succeeds, the program will supply the information to the 
     * database and close the form.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {
        //Get user information from form and assign to variables
        String division = divisionComboBox.getSelectionModel().getSelectedItem();
        String name = nameTextBox.getText();
        String address = addressTextBox.getText();
        String postalCode = postalCodeTextBox.getText();
        String phone = phoneTextBox.getText();
        
        //Check to make sure all user inputs are entered
        if ((division == null) || (name.isEmpty()) || (address.isEmpty())
                || (postalCode.isEmpty()) || (phone.isEmpty()))
        {   //Give user an alert if fields are left empty
            Alerts.incompleteAlert();
        }
        else
        {   //Retrieve division list for comparison to user input            
            for (Division div : Division.getDivisionList())
            {   //Check input for match to division list
                if (division.equals(div.getDivision()))
                {   
                    for (Customer customer : Customer.getCustomerList())
                    {
                        if (customer.getCustomerID() == CustomerMenuController.getSelectedCustomer().getCustomerID())
                        {    //Call method to add customer to DB
                            DBSQLCalls.updateCustomer(customer.getCustomerID(),name, address, 
                                    postalCode, phone, div.getDivisionID());
                            //Exit loop
                            break;
                        }
                    }
                    break;  
                }
            }
            
            //Close window and open customer menu
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }


    /**
     * Initializes the controller class.
     * 
     * The countryComboBox is populated with the available countries.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> allCountries = FXCollections.observableArrayList();
        try (var countries = DBConnection.getConnection().prepareStatement("SELECT * FROM countries"))
        {   //Assign data from DB to result set
            var countrySet = countries.executeQuery();
            //Loop through set to utilize data        
            while (countrySet.next())
            {
                //Assign data to variables
                String countryName = countrySet.getString("Country");
                int countryID = countrySet.getInt("Country_ID");
                //Assign name of country to observable list
                allCountries.add(countryName);
                //Call constructor for Country and add to observable list
                country = new Country(countryID, countryName);
                Country.addCountryToList(country);
                                
            }
        }
        catch (SQLException e)
        {   //Print exception should one occur
            System.out.println(e.getMessage());
        }
        //Add countries to combo box for user selection
        countryComboBox.setItems(allCountries);
        
        Customer updateCustomer = CustomerMenuController.getSelectedCustomer();
        
        for (Division division : Division.getDivisionList())
        {
            if (division.getDivision().equals(updateCustomer.getDivision()))
            {
                int updateDivisionCountryID = division.getCountryID();
                
                for (Country country : Country.getCountryList())
                {
                    if (updateDivisionCountryID == country.getID())
                    {
                        countryComboBox.setValue(country.getName());
                        countryComboBoxAction(new ActionEvent());
                    }
                }
            }
        }
        
        divisionComboBox.setValue(updateCustomer.getDivision());
        customerIDTextBox.setText(Integer.toString(updateCustomer.getCustomerID()));
        nameTextBox.setText(updateCustomer.getName());
        addressTextBox.setText(updateCustomer.getAddress());
        postalCodeTextBox.setText(updateCustomer.getPostalCode());
        phoneTextBox.setText(updateCustomer.getPhone());
    }    
    
}
