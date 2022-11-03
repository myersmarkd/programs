/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195program;

import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.DBConnection;

/**
 * The appointment scheduler program's main class.  The main method is the beginning
 * of the code.
 * 
 * @author myers
 */
public class C195Program extends Application {

    /**
     * The main method launches the GUI and establishes a connection to the database.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Locale.setDefault(new Locale("fr", "FR"));
        
        DBConnection.startConnection();
        launch(args);
    }

    /**
     * The start method opens the login screen for the user.
     * 
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
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
    }
    
}
