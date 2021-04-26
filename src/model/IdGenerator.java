
package model;

/**
 * This class IdGenerator is used only to track and get new IDs for both parts
 * and products.
 * @author sjpaige
 */
public class IdGenerator {
    
    //protected private values
    private static int idPartGen=0; 
    private static int idProductGen=0;
    
    /**
     * This method getNewPartID() increments the id so that a new Part can have a
     * new ID.
     * @return an integer ID for a part
     */
    public static int getNewPartID(){
        return idPartGen++; //increment on request
    }
    
    /**
     * This method getNewProductID() increments the id so that a new Product can have a
     * new ID.
     * @return an integer ID for a part
     */
    public static int getNewProductID(){
        return idProductGen++; //increment on request
    }
}
