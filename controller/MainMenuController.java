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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author myers
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    public static int partCounter = 0;
    public static int productCounter = 0;
    
    @FXML
    private TextField partsSearch;
    
    @FXML
    private TextField productSearch;
    
    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    
    @FXML
    private TableColumn<Part, String> partNameColumn;
    
    @FXML
    private TableColumn<Part, Integer> partInvColumn;
    
    @FXML
    private TableColumn<Part, Integer> partPriceColumn;
    
    @FXML
    private void partsAddBtnAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/PartsMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    private void partsDeleteBtnAction(ActionEvent event) {
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        boolean deleteConfirm = false;
        
        if (selectedPart == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Part not selected");
            alert.setContentText("You must select a part before you can delete it!");
            alert.showAndWait();
        }
        else {
            deleteConfirm = Inventory.deletePart(selectedPart);
        }
        
        if (deleteConfirm == true) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Deleted Successfully");
            alert.setHeaderText("Part successfully deleted.");
            alert.setContentText("Your part was removed from inventory.");
            alert.showAndWait();
        }
    }

    @FXML
    private void partsModifyBtnAction(ActionEvent event) throws IOException {
        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Modify Error");
            alert.setHeaderText("Part not selected");
            alert.setContentText("You must select a part before you can modify it!");
            alert.showAndWait();
        }
        else {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartsMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void partsSearchButtonAction(ActionEvent event) {
        String value = partsSearch.getText();
        Part part = null;
        
        if (isInteger(value)) {
            part = Inventory.lookupPart(Integer.parseInt(value));
            if (part != null) {
                partsTable.requestFocus();
                partsTable.getSelectionModel().select(part);
            }
            else {
                Alert alert = new Alert(AlertType.INFORMATION);
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
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Search Error");
                alert.setHeaderText("Part not found");
                alert.setContentText("The search term entered does not match any part!");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private TableView<Product> productTable;
    
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    
    @FXML
    private TableColumn<Product, String> productNameColumn;
    
    @FXML
    private TableColumn<Product, Integer> productInvColumn;
    
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    void productAddBtnAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void productDeleteBtnAction(ActionEvent event) {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        boolean deleteConfirm = false;
        
        if (selectedProduct == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Product not selected");
            alert.setContentText("You must select a product before you can delete it!");
            alert.showAndWait();
        }
        else {
            deleteConfirm = Inventory.deleteProduct(selectedProduct);
        }
        
        if (deleteConfirm == true) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Deleted Successfully");
            alert.setHeaderText("Product successfully deleted.");
            alert.setContentText("Your product was removed from inventory.");
            alert.showAndWait();
        }

    }

    @FXML
    void productModifyBtnAction(ActionEvent event) throws IOException {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Modify Error");
            alert.setHeaderText("Product not selected");
            alert.setContentText("You must select a product before you can modify it!");
            alert.showAndWait();
        }
        else {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void productSearchButtonAction(ActionEvent event) {
        String value = productSearch.getText();
        Product product = null;
        
        if (isInteger(value)) {
            product = Inventory.lookupProduct(Integer.parseInt(value));
            if (product != null) {
                productTable.requestFocus();
                productTable.getSelectionModel().select(product);
            }
            else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Search Error");
                alert.setHeaderText("Product not found");
                alert.setContentText("The search term entered does not match any product!");
                alert.showAndWait();
            }
        }
        else {
            ObservableList<Product> stringSearch = Inventory.lookupProduct(value);
            if (stringSearch != null) {
                for (Product search : Inventory.getAllProducts()) 
                {
                    if (search.getName().contains(value)) 
                    {
                        productTable.requestFocus();
                        productTable.getSelectionModel().select(search);
                    }
                }
            }
            else 
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Search Error");
                alert.setHeaderText("Product not found");
                alert.setContentText("The search term entered does not match any product!");
                alert.showAndWait();
            }
        }

    }
    
    @FXML
    private void exitButtonAction(ActionEvent event) {
        System.exit(0);
    }
    
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static Part selectedPart;
    
    static Part getSelectedPart() {
        return selectedPart;
    }
    
    private static Product selectedProduct;
    
    static Product getSelectedProduct() {
        return selectedProduct;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        
        partsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}
