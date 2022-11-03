package inventoryprogram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author myers
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partId) {
        for(Part part : getAllParts()){
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId) {
        for(Product product : getAllProducts()){
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partSearchList = FXCollections.observableArrayList();
        for(Part part : getAllParts()){
            if (part.getName().contains(partName)) {
                partSearchList.add(part);
                return partSearchList;
            }
        }
        return null;
    }
    
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productSearchList = FXCollections.observableArrayList();
        for(Product product : getAllProducts()){
            if (product.getName().contains(productName)) {
                productSearchList.add(product);
                return productSearchList;
            }
        }
        return null;
    }
    
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }
    
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }
    
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
