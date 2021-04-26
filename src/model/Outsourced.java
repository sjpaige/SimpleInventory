
package model;

/**
 * The Outsourced class is a form of Part that inherits its majority from the
 * Part class.
 * @author sjpaige
 */
public class Outsourced extends Part{
    
    /**
     * A constructor for the Outsourced class that uses the superclass constructor.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max); //superclass contructor for majority
        this.companyName = companyName; //sets the Outsourced class specific value 
    }
    
    private String companyName; //names the company that created the Part 

    /**
     * A getter method for the companyName field.
     * @return the company's name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * A setter method for the companyName field.
     * @param companyName a value to set to the companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
}
