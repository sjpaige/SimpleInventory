package model;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Errors class handles both the Exceptions in the Inventory System
 * as well as the Alert messages that use the Exceptions.
 * @author sjpaige
 */
public class Errors {
    
    /**
     * This waringPopUp() method is used to create a confirmation alert throughout the 
     * Inventory System.
     * @param title a String value to name the window
     * @param header a String value to name the header
     * @param content a String value with a message 
     * @return an Optional of type ButtonType to allow decisions to be made using alert
     */
    public static Optional<ButtonType> warningPopUp(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //confirmation used to warn cancelling
        alert.setHeaderText(header);        
        alert.setTitle(title);
        alert.setContentText(content);
        return alert.showAndWait();
    }
    
    /**
     * This errorPopUp() method is used to show an error message using an
     * alert.
     * @param title a String value to name the window
     * @param header a String value to name the header
     * @param content a String value with a message 
     */
    public static void errorPopUp(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);        
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * This nested Class of Exception MinMaxException throws an error
     * for when the min is greater than the max or the max is less than the min.
     */
    public static class MinMaxException extends Exception {

        /**
         * A constructor for MinMaxException that invokes the Exception super()
         * constructor.
         */
        public MinMaxException() {
            super("Min cannot exceed Max.");
        }
    }

    /**
     * This nested Class of Exception InventoryMinException throws an error
     * when the inventory value is less than min value.
     */
    public static class InventoryMinException extends Exception {

        /**
         * A constructor for InventoryMinException that invokes the Exception super()
         * constructor.
         */
        public InventoryMinException() {
            super("Inv cannot be less than Min.");
        }
    }

    /**
     * This nested Class of Exception InventoryMaxException throws an error
     * when the inventory value is greater than max value.
     */
    public static class InventoryMaxException extends Exception {

        /**
         * A constructor for InventoryMaxException that invokes the Exception super()
         * constructor.
         */
        public InventoryMaxException() {
            super("Inv cannot exceed Max.");
        }
    }

    /**
     * This nested Class of Exception NameEmptyException throws an error
     * when the name field shows up empty.
     */
    public static class NameEmptyException extends Exception {

        /**
         * A constructor for NameEmptyException that invokes the Exception super()
         * constructor.
         */
        public NameEmptyException() {
            super("Product must have a name.");
        }
    }

    /**
     * This nested Class of Exception MissingPartsException throws an error
     * when there are no parts attached to a product.
     */
    public static class MissingPartsException extends Exception{

        /**
         * A constructor for MissingPartsException that invokes the Exception super()
         * constructor.
         */
        public MissingPartsException() {
            super("This Product must have at least 1 Part.");
        }
    }
    
    /**
     * This nested Class of Exception PriceException throws an error
     * when the sum cost of the parts is greater than the price of the product.
     */
    public static class PriceException extends Exception{

        /**
         * A constructor for PriceException that invokes the Exception super()
         * constructor.
         */
        public PriceException(){
            super("Price must be larger than sum of part costs.");
        }
    }

}
