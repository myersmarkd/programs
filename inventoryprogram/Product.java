package inventoryprogram;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author myers
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private static ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    private static ObservableList<Integer> partsListTracker = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Product(){
        
    }
    
    public Product(int id, String name, double price, int stock, int min, int max){
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
    }
    
    public void setId(int idInput) {
        this.id = idInput;
    }
    
    public void setName(String nameInput) {
        this.name = nameInput;
    }

    public void setPrice(double priceInput) {
        this.price = priceInput;
    }
    
    public void setStock(int stockInput) {
        this.stock = stockInput;
    }

    public void setMin(int minInput) {
        this.min = minInput;
    }

    public void setMax(int maxInput) {
        this.max = maxInput;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    
    public static void addAssocPart(Part part) {
        associatedPartsList.add(part);
    }
    
    public static void deleteAssocPart(Part selectedPart) {
        associatedPartsList.remove(selectedPart);
    }
    public static ObservableList<Part> getAllAssocParts() {
        return associatedPartsList;
    }

    public static void addListTracker(int id) {
        partsListTracker.add(id);
    }
    
    public static void deleteListTracker(int index) {
        partsListTracker.remove(partsListTracker.get(index));
    }
    
    public static ObservableList<Integer> getListTracker() {
        return partsListTracker;
    }
}
