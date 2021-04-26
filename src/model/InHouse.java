
package model;



/**
 * The InHouse class is a form of Part that inherits its majority from the
 * Part class.
 * @author sjpaige
 */
public class InHouse extends Part {
    
    /**
     * A constructor for the InHouse class that uses the superclass constructor.
     * @param id 
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max); //superclass contructor for majority
        this.machineId = machineId; //sets the InHouse specific value 
    }
    
    private int machineId; //represents the machine that created the Part in house

    /**
     * A getter method for the machineId field.
     * @return the machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * A setter method for the machineId field.
     * @param machineId a value to set to the machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    
}
