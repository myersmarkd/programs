/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import information.User;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.Alerts;
import utility.DBConnection;
import utility.DBPopulateLists;

/**
 * FXML Controller class
 * 
 * The opening screen of the program is the login screen.  The users local country
 * and local time zone are displayed.  If the appropriate username and password 
 * are not entered, and alert pops up.  All login attempts, both successful and 
 * unsuccessful, are logged in the "login_activity.txt" file.  The entered username,
 * the date and time in UTC, and whether they user was successful for logging in
 * are written to the file.
 *
 * @author myers
 */
public class LoginController implements Initializable {
    
    @FXML
    private Label headerLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField userNameTextBox;

    @FXML
    private PasswordField passwordTextBox;

    @FXML
    private Button submitButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label localCountryLabel;

    @FXML
    private Label localTimeZoneLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label timeZoneLabel;
    
    public static String username;
    
    public static int userid;
    
    public static boolean upcomingApptAlert = false;
    
    /**
     * An event on this button closes the connection to the database and terminates
     * the program.
     * 
     * @param event 
     */
    @FXML
    private void exitButtonAction(ActionEvent event) {
        //Close the DB connection
        DBConnection.closeConnection();
        
        //Close the program
        System.exit(0);
    }

    /**
     * An event on this button will get the information entered by the user and
     * compare to the data in the database.  If there is not a match, an appropriate
     * alert will be provided.  Otherwise the user is taken to the main menu.  The
     * login attempt is recorded regardless of success.  Several lists of information
     * are populated from the database on the event of a successful login.
     * 
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    @FXML
    private void submitButtonAction(ActionEvent event) throws SQLException, IOException {
        //Retrieve user information from DB
        try (var ps = DBConnection.getConnection().prepareStatement("SELECT * FROM users",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            var userSet = ps.executeQuery();
            boolean successful = false;
            //Check to if user list is empty to avoid duplicate entries
            if (User.getUserList().isEmpty())
            {   //Add user name and ID to list
                while (userSet.next())
                {   //Get user ID from DB
                    int userID = userSet.getInt("User_ID");

                    //Get username from DB to compare to user input
                    String userName = userSet.getString("User_Name");

                    //Populate user list
                    User user = new User(userID, userName);
                    User.addUserToList(user);
                }
            }
            //Restart userSet from the beginning
            userSet.beforeFirst();
            
            while (userSet.next())
            {   //Get username from DB to compare to user input
                String userName = userSet.getString("User_Name");
                
                //Get password from DB to compare to user input
                String password = userSet.getString("Password");
                
                //Get user id from DB to use to filter content
                int id = userSet.getInt("User_ID");
                                
                //Compare user input to user name and password in DB
                if ((userName.equals(userNameTextBox.getText())) && (password.equals(passwordTextBox.getText())))
                {
                    successful = true;
                    username = userName;
                    userid = id;
                    break;
                }
                
            }
            
            //Display error message if credentials are incorrect
            if (successful == false)
            {   //Call error method
                Alerts.loginFailure();
                
                //Write to file if login was successful with username and date of attempt
                try (var output = new PrintWriter(new BufferedWriter(new FileWriter("login_activity.txt", true))))
                {
                    output.println("Username: " + userNameTextBox.getText() + "\tSuccessful: " + successful +
                            "\tTime: " + Instant.now() + " UTC");
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {   //Write to file if login was successful with username and date of attempt
                try (var output = new PrintWriter(new BufferedWriter(new FileWriter("login_activity.txt", true))))
                {
                    output.println("Username: " + userNameTextBox.getText() + "\tSuccessful: " + successful +
                            "\tTime: " + Instant.now() + " UTC");
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                
                //Populate lists to load into GUI
                DBPopulateLists.populateContactList();
                DBPopulateLists.populateCustomerList();
                DBPopulateLists.populateCountryList();
                
                //Open main menu
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
     * The language of the program is changed based on the users Locale.  The local
     * country and the local time zone are displayed in the user interface.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        username = null;
        userid = 0;
        
        //Set country label to local country
        Locale locale = Locale.getDefault();
        countryLabel.setText(locale.getDisplayCountry());
        
        //Set time zone label to local time zone
        ZonedDateTime currentTime = ZonedDateTime.now();
        var zoneFormat = DateTimeFormatter.ofPattern("z");
        timeZoneLabel.setText(currentTime.format(zoneFormat));
        
        if (Locale.getDefault().getLanguage().equals("fr"))
        {
            rb = ResourceBundle.getBundle("utility/French", Locale.getDefault());
            headerLabel.setText(rb.getString("Appointment Scheduler Login"));
            userNameLabel.setText(rb.getString("User Name"));
            passwordLabel.setText(rb.getString("Password"));
            localCountryLabel.setText(rb.getString("Local Country"));
            localTimeZoneLabel.setText(rb.getString("Local Time Zone"));
            submitButton.setText(rb.getString("Submit"));
            exitButton.setText(rb.getString("Exit"));
            
        }
    }    
    
}
