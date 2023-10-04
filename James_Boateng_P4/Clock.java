import java.util.List;

/**
 * Write a description of class Clock here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Clock
{
    // The current time of the clock (in hours)
  private int time;

  // The list of trucks in the simulation
  private List<Truck> trucks;
  // The list of warehouses in the simulation
  private List<Warehouse> warehouses;

  // Constructor for the Clock class
  public Clock(List<Truck> trucks, List<Warehouse> warehouses) {
    this.trucks = trucks;
    this.warehouses = warehouses;
    time = 0;
  }

  // Method to tick the clock by one hour
  public void tick() {
    // Increment the time by one hour
    time++;

    // Perform the action for each truck
    for (Truck truck : trucks) {
      truck.action();
    }

    // Perform the action for each warehouse
    for (Warehouse warehouse : warehouses) {
      warehouse.action();
    }
  }

  // Method to get the current time of the clock
  public int getTime() {
    return time;
  }
}
