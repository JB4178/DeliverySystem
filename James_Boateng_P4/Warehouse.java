import java.util.*;
import java.io.*;

/**
 * Write a description of class Warehouse here.
 *
 * @author (James Boateng)
 * @version (11/28/2022)
 */
public class Warehouse implements Schedule
{
    // instance variables - replace the example below with your own
    final int maxRange = 100;
    Random random;
    double xPos;
    double yPos;
    int docNum; 
    private Queue<Truck> trucks;
    private List<Shipment> shipments;
    private List<Dock> docks;

    public Warehouse(){
        docks = new ArrayList();
        shipments = new ArrayList();
        docks = new ArrayList();
        trucks = new LinkedList<Truck>();
        random = new Random();
        xPos = Math.abs(random.nextInt(maxRange)+1);
        yPos = Math.abs(random.nextInt(maxRange)+1);
        docNum = random.nextInt(3)+1;
        docks = new ArrayList();

        for(int i = 0; i< docNum; i++){
            docks.add(new Dock());
        }
    }

    /**
     * Sets position for testing
     */
    public void setPos(double x, double y){
        xPos = x;
        yPos = y;
    }
    
    /**
     * returns the x position
     */
    public double getX(){
        return xPos;
    }

    /**
     * Load shipment to truck
     */
    public void loadShipment(Truck truck) {
        trucks.add(truck);
        for(Dock dock:docks){
            if(dock.isOccupied()){
                if(!dock.getTruck().equals(truck)){
                    truck.setStage(Status.WAIT);
                }else{
                    truck.pickup(removeShipment(truck.getShipment().getId()));
                    truck.setStage(Status.MOVE);
                    break;
                }
            }else{
                dock.setTruck(truck);
                truck.setStage(Status.WAIT);
                break;
            }
        }
    }

    /**
     * Remove shipment from Warehouse
     */
    public Shipment removeShipment(int shipmentId){
        // Find the shipment with the given ID
        for (int i = 0; i < shipments.size(); i++) {
            if (shipments.get(i).id == shipmentId) {
                // Remove the shipment from the warehouse and return it
                return shipments.remove(i);
            }
        }
        return null;
    }

    /**
     * return the yPos
     */
    public double getY(){
        return yPos;
    }

    /** 
     * Take a shipment from a truck and stores in warehouse
     */
    public void unloadShipment(Truck truck){
        trucks.add(truck);
        for(Dock dock:docks){
            if(dock.isOccupied()){
                if(!dock.getTruck().equals(truck)){
                    truck.setStage(Status.WAIT);
                }else{
                    shipments.add(truck.dropOff());
                    break;
                }
            }else{
                dock.setTruck(truck);
                truck.setStage(Status.WAIT);
                break;
            }
        }
    }

    /**
     * Adds shipments to the warehouse at the beginning of the simulation
     */
    public void addShipment(Shipment shipment){
        shipments.add(shipment);
    }

    public void action(){
        // Update the loading docks to check for empty docks
        this.updateLoadingDocks();
    }

    /**
     * Updates the loading docks
     */
    public void updateLoadingDocks(){
        for(Dock dock: docks){
            if(!trucks.isEmpty()){
                if(!dock.isOccupied() && trucks.peek().getStatus() == Status.WAIT){
                    Truck temp = trucks.poll();
                    dock.setTruck(temp);
                    if(temp.getShipment().picked()){
                        temp.setStage(Status.UNLOAD);
                    }else{
                        temp.setStage(Status.PICK_UP);
                    }
                }else if(dock.isOccupied() && (dock.getTruck().getStatus().equals(Status.DONE)||dock.getTruck().getStatus().equals(Status.MOVE))){
                    trucks.poll();
                    dock.setTruck(null);
                }else if(dock.isOccupied() && dock.getTruck().getStatus().equals(Status.WAIT)){
                    if(dock.getTruck().getShipment().picked()){
                        dock.getTruck().setStage(Status.UNLOAD);
                    }else{
                        dock.getTruck().setStage(Status.PICK_UP);
                    }
                }
            }
        }
    }

    public void log_status() {
        try{
            // Open the log file for writing
            File file = new File("warehouse_log.txt");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write the current status of the warehouse to the log file
            bw.write("Warehouse");
            bw.write("Number of loading docks: " + docNum);
            bw.write("Current shipments: " + shipments);
            //   bw.write("Current shipments in transit: " + this.shipmentsInTransit);

            // Close the log file
            bw.close();
            fw.close();
        }catch(IOException e){
            System.err.println("Error in filewriting");
        }
    }
}
