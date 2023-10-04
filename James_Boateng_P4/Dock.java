
/**
 * Write a description of class Dock here.
 *
 * @author (James Kofi Boateng)
 * @version (12/05/2022)
 */
public class Dock
{
    private boolean occupied;
    private Truck truck;

    public Dock() {
        occupied = false;
    }

    // Set occupied status
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    // Check occupied status
    public boolean isOccupied() {
        if(truck != null){
            occupied = true;

        }else{
            occupied = false;

        }
        return occupied;
    }

    // Method to get the truck currently using the loading dock
    public Truck getTruck() {
        return this.truck;
    }

    // Method to set the truck currently using the loading dock
    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
