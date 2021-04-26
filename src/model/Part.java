
package model;

/**
 * The Part abstract class sets the baseline for the InHouse and the Outsourced
 * classes which inherit from it.
 * 
 * @author sjpaige
 */
public abstract class Part {
    private int id; //a unique idenrificiation value
    private String name; //the name of the part
    private double price; //a price for the part
    private int stock; //the number of that part left in the inventory
    private int min; //the low end for the part's on hand
    private int max; //the max of the parts on hand
    
    /**
     * A constructor for the Part class.
     * @param id takes an int
     * @param name takes a String
     * @param price takes a double
     * @param stock takes an int
     * @param min takes an int
     * @param max takes an int
     */
    public Part(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * A getter method for the id field.
     * @return the id 
     */
    public int getId() {
        return id;
    }

    /**
     * A setter method for the id field.
     * @param id an int used in setting.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * A getter method for the name field.
     * @return the part name
     */
    public String getName() {
        return name;
    }

    /**
     * A setter method for the name field.
     * @param name a string used to set the name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * A setter method for the price field.
     * @param price a double value passed to the price
     */
    public void setPrice(double price){
        this.price = price;
    }
    
    /**
     * A getter method for the price field.
     * @return the price of the part
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * A getter method for the stock field.
     *
     * @return the number of parts in stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * A setter method for the stock field.
     * @param stock a value passed to the stock field
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * A getter for the min field.
     * @return the minimum parts in stock
     */
    public int getMin() {
        return min;
    }

    /**
     * A setter method for the min field.
     * @param min a value passed to the minimum parts
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *  A getter method for the max field.
     * @return the maximum stock of the part
     */
    public int getMax() {
        return max;
    }

    /**
     * a setter method for the max field.
     * @param max used to set the max parts
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}
