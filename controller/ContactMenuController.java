/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import information.Contact;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import utility.DBConnection;
import java.sql.*;
import java.util.Locale;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * The ContactMenuController will display all of the contacts in the database.
 *
 * @author myers
 */
public class ContactMenuController implements Initializable {
    
    @FXML
    private TableView<Contact> contactTable;

    @FXML
    private TableColumn<Contact, Integer> idColumn;

    @FXML
    private TableColumn<Contact, String> nameColumn;

    @FXML
    private TableColumn<Contact, String> emailColumn;

    @FXML
    private Button backButton;

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
     * The contactTable is populated with the information of the contacts in the
     * database.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
             
        contactTable.setItems(Contact.getContactList());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));
    }    
    
}
