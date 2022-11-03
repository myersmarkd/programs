/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * The Alerts class provides several methods to display appropriate alerts.
 * Lambda expression french calls a predicate to determine if the language is
 * set to French or not.  Using a lambda expression cuts down on unneeded code
 * referencing the same expression from multiple methods.
 * 
 * @author myers
 */
public class Alerts {
    
    static Predicate<String> french = s -> Locale.getDefault().getLanguage().equals(s);
    
    /**
     * A confirmation alert box will appear for the user.  Clicking "OK" will confirm
     * the user's desire to cancel their entry.  Clicking "Cancel" will close the
     * alert and keep the user at their current screen.
     * 
     * @return 
     */
    public static boolean cancelConfirm()
    {
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to cancel?",
            ok, cancel);
        
        alert.setTitle("Cancel Confirmation");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.orElse(ok) == ok)
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * A confirmation alert box will appear for the user.  Clicking "OK" will confirm
     * the user's desire to delete their entry.  Clicking "Cancel" will close the
     * alert and keep the user at their current screen.
     * 
     * @return 
     */
    public static boolean deleteConfirm()
    {
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the selected?",
            ok, cancel);
        
        alert.setTitle("Delete Confirmation");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.orElse(ok) == ok)
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * An alert will appear confirming the user was successful in deleting something.
     * Lambda expression french is called to determine the language used by the
     * system default.  Utilizing the lambda helps to reduce typos in duplicate
     * code by referencing the correct code from multiple methods.
     */
    public static void deleteSuccess()
    {
        if (french.test("fr"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Entrée supprimée");
            alert.setHeaderText("Supprimé avec succès");
            alert.setContentText("Votre sélection a été supprimée avec succès.");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Entry Deleted");
            alert.setHeaderText("Deleted successfully");
            alert.setContentText("Your selection has been deleted successfully.");
            alert.showAndWait();
        }
    }
    
    /**
     * An alert will appear telling the user they have not completed filling out
     * the form.  Lambda expression french is called to determine the language used by the
     * system default.  Utilizing the lambda helps to reduce typos in duplicate
     * code by referencing the correct code from multiple methods.
     */
    public static void incompleteAlert()
    {
        if (french.test("fr"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Entrée incomplète");
            alert.setHeaderText("Données manquantes");
            alert.setContentText("Veuillez remplir tous les champs pour continuer.");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input incomplete");
            alert.setHeaderText("Data is missing");
            alert.setContentText("Please complete all fields to continue.");
            alert.showAndWait();
        } 
    }
    
    /**
     * An alert will appear telling the user their login attempt failed.  Lambda 
     * expression french is called to determine the language used by the
     * system default.  Utilizing the lambda helps to reduce typos in duplicate
     * code by referencing the correct code from multiple methods.
     */
    public static void loginFailure()
    {   System.out.println(french.test("fr"));
        if (french.test("fr"))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nom d'utilisateur / Mot de passe incorrect");
            alert.setHeaderText("Accès refusé");
            alert.setContentText("Le nom d'utilisateur et / ou le mot de passe ne sont pas corrects");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect User Name/Password");
            alert.setHeaderText("Access Denied");
            alert.setContentText("The user name and/or password are not correct");
            alert.showAndWait();
        }
    }
    
    /**
     * An alert will appear telling the user that proposed times overlap an existing
     * appointment.  Lambda expression french is called to determine the language
     * used by the system default.  Utilizing the lambda helps to reduce typos in
     * duplicate code by referencing the correct code from multiple methods.
     */
    public static void overlapAlert()
    {
        if (french.test("fr"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chevauchement de rendez-vous");
            alert.setHeaderText("Erreur de planification");
            alert.setContentText("Rendez-vous qui se chevauchent pour ce client.");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Overlap");
            alert.setHeaderText("Scheduling Error");
            alert.setContentText("Overlapping appointments for this customer.");
            alert.showAndWait();
        }
    }
    
    /**
     * An alert will appear telling the user the did not select anything.  Lambda
     * expression french is called to determine the language used by the
     * system default.  Utilizing the lambda helps to reduce typos in duplicate
     * code by referencing the correct code from multiple methods.
     */
    public static void selectionError()
    {
        if (french.test("fr"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("Rien n'est sélectionné");
            alert.setContentText("Vous devez d'abord sélectionner quelque chose.");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing is selected");
            alert.setContentText("You must select something first.");
            alert.showAndWait();
        }
    }
    
    /**
     * An alert will appear telling the user that the proposed time does not work.
     * Lambda expression french is called to determine the language used by the
     * system default.  Utilizing the lambda helps to reduce typos in duplicate
     * code by referencing the correct code from multiple methods.
     */
    public static void timeErrorAlert()
    {
        if (french.test("fr"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Date / heure incorrecte");
            alert.setHeaderText("Erreur de date / heure");
            alert.setContentText("La date / heure de début doit être antérieure à la date / heure de fin.");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Date/Time Incorrect");
            alert.setHeaderText("Date/Time Error");
            alert.setContentText("The start date/time must be before the end date/time.");
            alert.showAndWait();
        } 
    }
    
    /**
     * An alert will appear telling the user that they have X amount of minutes
     * until their next appointment.  Lambda expression french is called to determine
     * the language used by the system default.  Utilizing the lambda helps to
     * reduce typos in duplicate code by referencing the correct code from multiple
     * methods.
     * 
     * @param time 
     */
    public static void upcomingAppt(LocalTime time, boolean ranAlready)
    {
        if (ranAlready != true)
        {
            LocalTime now = LocalTime.now();
            long timeDifference = ChronoUnit.MINUTES.between(now, time);
            if (french.test("fr"))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerte de rendez-vous");
                alert.setHeaderText("Rendez-vous à venir");
                alert.setContentText("Vous avez un rendez-vous dans " + (timeDifference + 1) + " minute(s)!");
                alert.show();
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setAlwaysOnTop(true);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Alert");
                alert.setHeaderText("Upcoming Appointment");
                alert.setContentText("You have an appointment in " + (timeDifference + 1) + " minute(s)!");
                alert.show();
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setAlwaysOnTop(true);
            }
        }
    }
    
}
