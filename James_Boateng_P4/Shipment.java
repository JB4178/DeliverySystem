import java.util.*;
/**
 * Write a description of class Shipment here.
 *
 * @author (James Kofi Boateng)
 * @version (12/05/2022)
 */
public class Shipment
{
    // instance variables 
    Random random;
    int size;
    int id;
    public Warehouse destination;
    public Warehouse source;
    private boolean picked;
    
    public Shipment(int id, Warehouse source, Warehouse destination){
        random = new Random();
        size = Math.abs(random.nextInt(4)+1);
        this.id = id;
        picked = false;
        this.source = source;
        source.addShipment(this);
        this.destination = destination;
    }
    
    /**
     * Returns the size
     */
    public int getSize(){
        return size;
    }
    
    /**
     * Returns the ID
     */
    public int getId(){
        return id;
    }
    
    /**
     * Returns true if the shipment has been picked up
     */
    public boolean picked(){
        return picked;
    }
    
    /**
     * Set picked
     */
    public void setPicked(boolean picked){
        this.picked = picked;
    }
}
