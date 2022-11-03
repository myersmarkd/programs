package controller;

import inventoryprogram.InHouse;
import inventoryprogram.Inventory;
import inventoryprogram.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author myers
 */
public class PartsMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private RadioButton inHouseBtn;
    
    @FXML
    private RadioButton outsourced;
    
    @FXML
    private TextField nameTextBox;
    
    @FXML
    private TextField invTextBox;
    
    @FXML
    private TextField priceTextBox;
    
    @FXML
    private TextField minTextBox;
        
    @FXML
    private TextField maxTextBox;
    
    @FXML
    private Label companyMachineLabel;
    
    @FXML
    private TextField companyMachineTextBox;
    
    @FXML
    private void partsCancelBtnAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    private void inHouseBtnAction(ActionEvent event) {
        companyMachineLabel.setText("Machine ID");
        companyMachineTextBox.setPromptText("Machine ID");
    }

    @FXML
    private void outsourcedBtnAction(ActionEvent event) {
        companyMachineLabel.setText("Company Name");
        companyMachineTextBox.setPromptText("Company Name");
    }

    @FXML
    private void partsSaveBtnAction(ActionEvent event) throws IOException {
        MainMenuController.partCounter = MainMenuController.partCounter + 1;
        
        int id = MainMenuController.partCounter;
        String name = nameTextBox.getText();
        double price = Double.parseDouble(priceTextBox.getText());
        int inv = Integer.parseInt(invTextBox.getText());
        int min = Integer.parseInt(minTextBox.getText());
        int max = Integer.parseInt(maxTextBox.getText());
        
        
        if (inHouseBtn.isSelected() == true) {
            int machineId = Integer.parseInt(companyMachineTextBox.getText());
            Inventory.addPart(new InHouse(id, name, price, inv, min, max, machineId));
        }
        else {
            String companyName = companyMachineTextBox.getText();
            Inventory.addPart(new Outsourced(id, name, price, inv, min, max, companyName));
        }
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
