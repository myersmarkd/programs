package controller;

import inventoryprogram.Inventory;
import inventoryprogram.Part;
import inventoryprogram.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author myers
 */
public class ProductMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML
    private TextField productIdTextBox;

    @FXML
    private TextField productNameTextBox;

    @FXML
    private TextField productInvTextBox;

    @FXML
    private TextField productPriceTextBox;

    @FXML
    private TextField productMaxTextBox;

    @FXML
    private TextField productMinTextBox;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInvColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TableView<Part> associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> associatedPartsIdColumn;

    @FXML
    private TableColumn<Part, String> associatedPartsNameColumn;

    @FXML
    private TableColumn<Part, Integer> associatedPartsInvColumn;

    @FXML
    private TableColumn<Part, Double> associatedPartsPriceColumn;

    @FXML
    private TextField partsSearch;
    
    private Part selectedPart;
    
    @FXML
    private void productSaveBtnAction(ActionEvent event) throws IOException {
        MainMenuController.productCounter = MainMenuController.productCounter + 1;
        
        int id = MainMenuController.productCounter;
        String name = productNameTextBox.getText();
        double price = Double.parseDouble(productPriceTextBox.getText());
        int inv = Integer.parseInt(productInvTextBox.getText());
        int min = Integer.parseInt(productMinTextBox.getText());
        int max = Integer.parseInt(productMaxTextBox.getText());
        
            
        try {
            double addPrice = 0;
            
            for(Part part : associatedPartsList){
                addPrice += part.getPrice();
            }
            
            if(addPrice > price) {
                MainMenuController.productCounter = MainMenuController.productCounter - 1;
                throw new Exception();
            }
            
            try {
            if(associatedPartsList.size() != 0) {
                for(Part part : associatedPartsList) {
                    Product.addAssocPart(part);
                    Product.addListTracker(id);
                }
            }
            else {
                MainMenuController.productCounter = MainMenuController.productCounter - 1;
                throw new Exception();
            }
            
            Inventory.addProduct(new Product(id, name, price, inv, min, max));
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part add error");
                alert.setHeaderText("Part not selected");
                alert.setContentText("All products must have at least one part!");
                alert.showAndWait();    
            }

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pricing error");
            alert.setHeaderText("Incorrect pricing");
            alert.setContentText("The price of the parts cannot exceed the price of the product!");
            alert.showAndWait(); 
        }
} 

    
    @FXML
    private void productDeleteBtnAction(ActionEvent event) {
        selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Part not selected");
            alert.setContentText("You must select a part before you can delete it!");
            alert.showAndWait();
        }
        else {
            associatedPartsList.remove(selectedPart);
        }
    }
    

    @FXML
    private void productCancelBtnAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void productAddBtnAction(ActionEvent event) {
        
        Part part = partsTable.getSelectionModel().getSelectedItem();
        
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part add error");
            alert.setHeaderText("Part not selected");
            alert.setContentText("You must select a part before you can add it!");
            alert.showAndWait();
        }
        else {
            associatedPartsList.add(part);
            associatedPartsTable.setItems(associatedPartsList);
        }
    }

    @FXML
    private void productSearchBtnAction(ActionEvent event) {
        String value = partsSearch.getText();
        Part part = null;
        
        if (MainMenuController.isInteger(value)) {
            part = Inventory.lookupPart(Integer.parseInt(value));
            if (part != null) {
                partsTable.requestFocus();
                partsTable.getSelectionModel().select(part);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Error");
                alert.setHeaderText("Part not found");
                alert.setContentText("The search term entered does not match any part!");
                alert.showAndWait();
            }
        }
        else {
            ObservableList<Part> stringSearch = Inventory.lookupPart(value);
            if (stringSearch != null) {
                for (Part search : Inventory.getAllParts()) 
                {
                    if (search.getName().contains(value)) 
                    {
                        partsTable.requestFocus();
                        partsTable.getSelectionModel().select(search);
                    }
                }
            }
            else 
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Error");
                alert.setHeaderText("Part not found");
                alert.setContentText("The search term entered does not match any part!");
                alert.showAndWait();
            }
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        partsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatedPartsTable.setItems(associatedPartsList);
        associatedPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    } 
}
