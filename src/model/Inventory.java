
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This Inventory class stores Part objects and Product objects as well as 
 * granting access to both types through static methods.
 * @author sjpaige
 */
public class Inventory {
    
    //Both Parts and Products are stored using FXCollections.observableArrayList()
    private static ObservableList<Part> allParts  = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts  = FXCollections.observableArrayList();
   
    
    /**
     * This addPart method is used to add a part to the inventory.
     * @param newPart takes a part 
     */
    public static void addPart(Part newPart){
        allParts.add(newPart); //Stores it using ObservableList's add method
    }
    
    /**
     * This addProduct method is used to add a product to the inventory.
     * @param newProduct takes a product 
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct); //Stores it using ObservableList's add method
    }
    
    /**
     * This method will search for a specific part.
     * @param id an int that represents the id of the part
     * @return the part if the id matches
     */
    public static Part lookUpPart(int id){
        for(Part part : Inventory.getAllParts()){ //iterate through existing parts
            if(part.getId() == id){ //compare the id 
                return part; 
            }
        }
        return null; //defaults to null if not found
    }
    
    /**
     * This method will search for a specific product.
     * @param id an int that represents the id of the product
     * @return the product if the id matches
     */
    public static Product lookUpProduct(int id){
        for(Product product : Inventory.getAllProducts()){ //iterate through existing products
            if(product.getId() == id){ //compare the id 
                return product;
            }
        }
        return null; //defaults to null if not found
    }    
    
    /**
     * This method will search for a list of parts by their similarity to the 
     * search term.
     * @param partName a String that represents the name of the part
     * @return the parts if the name matches
     */
    public static ObservableList<Part> lookUpPart(String partName){
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        
        for(Part part : Inventory.getAllParts()){
            if(part.getName().contains(partName)){
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }    
    
    /**
     * This method will search for a list of products by their similarity to the 
     * search term.
     * @param productName a String that represents the name of the product
     * @return the products if the name matches
     */
    public static ObservableList<Product> lookUpProduct(String productName){
        ObservableList<Product> matchingParts = FXCollections.observableArrayList();
        
        for(Product product : Inventory.getAllProducts()){
            if(product.getName().contains(productName)){
                matchingParts.add(product);
            }
        }
        return matchingParts;
    } 
    
    /**
     * This method will update a certain part in the Inventory 
     * @param id an int representing the id of the part being replaced
     * @param selectedPart a part that will replace the original
     */
    public static void updatePart(int id, Part selectedPart){
                Inventory.deletePart(lookUpPart(id));
                Inventory.addPart(selectedPart);        
    }
    
    /**
     * This method will update a certain product in the inventory 
     * @param id an int representing the id of the product being replaced
     * @param selectedProduct a product that will replace the original
     */
    public static void updateProduct(int id, Product selectedProduct){
                Product product = lookUpProduct(id);
                Inventory.deleteProduct(product);
                Inventory.addProduct(selectedProduct);    
    }

    /**
     * This method will delete a certain part from the inventory.
     * @param selectedPart a part that will be deleted
     */
    public static void deletePart(Part selectedPart){
        Inventory.getAllParts().remove(selectedPart); //Uses ObservableList.remove()
    }
 
    /**
     * This method will delete a certain product from the inventory.
     * @param selectedProduct a product that will be deleted
     */
    public static void deleteProduct(Product selectedProduct){
        Inventory.getAllProducts().remove(selectedProduct); //Uses ObservableList.remove()
    }    

    
    /**
     * Getter method for the allParts values.
     * @return an ObservableList
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /**
     * Getter method for the allProducts values.
     * @return an ObservableList
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    

    
    
    
}


