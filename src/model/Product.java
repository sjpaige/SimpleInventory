
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class Product defines the structure of the products in the 
 * inventory system.
 * 
 * @author sjpaige
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();; //parts that make up a product
    private int id; //the unique id of this Product in the inventory system
    private String name; //name of this product
    private double price; //the price of this product
    private int stock; //how much there is in the inventory of this product
    private int min; //the minimum allowed stock
    private int max; //maxiumum stock allowed
    
    /**
     * A constructor for the Product Class that initializes the values and
     * creates an instance of a Product.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        //use the setters to set all the class values
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
        
    }

    /**
     * A getter method for the id field.
     * @return an int 
     */
    public int getId() {
        return id;
    }

    /**
     * A setter method for the id field.
     * @param id takes an int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * A getter method for the name field.
     * @return a String value 
     */
    public String getName() {
        return name;
    }

    /**
     * A setter method for the name field.
     * @param name takes a String value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter method for the price field.
     * @return a double of the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * A setter method for the price field
     * @param price takes a double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * A getter method for the stock field.
     * @return a int value
     */
    public int getStock() {
        return stock;
    }

    /**
     * A setter for the stock field.
     * @param stock takes an int value
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * A getter method for the min field.
     * @return a int value
     */
    public int getMin() {
        return min;
    }

    /**
     * A setter method for the min field.
     * @param min takes an int value
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * A getter method for the max field.
     * @return a int value
     */
    public int getMax() {
        return max;
    }

    /**
     * A setter for the max field.
     * @param max takes an int value
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * Adds a part to the associatedParts ObservableList, and does so
     * by using the ObservableList.add() method.
     * @param part takes a Part Object
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    /**
     * Deletes a part from the associatedParts ObservableList using 
     * the ObseravableList.remove() method which is passed the parameter part
     * and the Part class method getId().
     * @param part takes a Part class Object
     */
    public void deleteAssociatedPart(Part part){
        associatedParts.remove(part.getId());
    }
    
    /**
     * Getter for the associatedParts field.
     * @return an ObeservableList of type Part
     */
    public ObservableList<Part> getAllAssociatedParts(){
        
        return associatedParts;
    }
    
}
