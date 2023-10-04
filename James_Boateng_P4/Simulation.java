import java.util.*;
import java.io.*;

/**
 * Write a description of class Simulation here.
 *
 * @author (James Kofi Boateng)
 * @version (12/05/2022)
 */
public class Simulation
{
    // instance variables - replace the example below with your own
    private int x;
    int maxTrucks;
    int maxShipments;
    int maxWarehouses;
    Random random;
    public List<Truck> trucks;
    public List<Warehouse> warehouses;
    List<Shipment> shipments;
    public static int numDeliveries = 0;
    /**
     * Constructor for objects of class Simulation
     */
    public Simulation()
    {
        this.trucks = new ArrayList<>();

        this.warehouses = new ArrayList<>();

        this.shipments = new ArrayList<>();

        random = new Random();
    }

    /**
     * Generates shipments
     */
    public void generateShipments(){
        // Generate a random number of shipments from 1 to maxShipments+1
        int numShipments = Math.abs(random.nextInt(maxShipments)+1);

        // Generate shipments
        for (int i = 0; i < numShipments; i++) {
            // Generate a random size for the shipment (1, 2, or 3)

            // Generate a random warehouse and truck for the shipment
            Warehouse warehouse = getRandomWarehouse(null);
            Warehouse destination = getRandomWarehouse(warehouse);
            Truck truck = getRandomTruck();
            // Create a new shipment with the generated size and warehouse
            Shipment shipment = new Shipment(i, warehouse, destination);

            // Add the shipment to the warehouse's list of shipments
            //    warehouse.addShipment(shipment);
            truck.addToManifest(shipment);
            shipments.add(shipment);
        }
    }

    /**
     * Generates the trucks
     */
    public void generateTrucks(){
        int numTrucks = random.nextInt(maxTrucks)+1;

        for(int i = 0; i< numTrucks; i++){
            Truck truck = new Truck(i);
            trucks.add(truck);
        }
    }

    /**
     * Generate the warehouses
     */
    public void generateWarehouses(){
        //Add two to make sure the minimum number of warehouses is 2
        int numWarehouses = random.nextInt(maxWarehouses)+2 ;

        for(int i = 0; i<numWarehouses; i++){
            Warehouse warehouse = new Warehouse();
            warehouses.add(warehouse);
        }
    }

    /**
     * Gets a random truck from the list of truck
     */
    public Truck getRandomTruck(){
        // Generate a random index for the truck (between 0 and the number of warehouses - 1)
        int index = random.nextInt(trucks.size());

        // Return the truck at the generated index
        return trucks.get(index); 
    }

    /**
     * Set maxElements
     */
    public void setElements(int maxTrucks, int maxShipments, int maxWarehouses){
        this.maxTrucks = maxTrucks;
        this.maxShipments = maxShipments;
        this.maxWarehouses = maxWarehouses;
    }

    /**
     * Gets a random warehouse
     */
    // Method to get a random warehouse from the list of warehouses
    public Warehouse getRandomWarehouse(Warehouse warehouse) {
        int index = -1;
        if(warehouse == null){
            // Generate a random index for the warehouse (between 0 and the number of warehouses - 1)
            index = random.nextInt(warehouses.size());

            // Return the warehouse at the generated index
            return warehouses.get(index);
        }else{
            index = random.nextInt(warehouses.size());
            while(warehouses.get(index).equals(warehouse)){
                index = random.nextInt(warehouses.size());
            }
            return warehouses.get(index);
        }
    }

    public static void main(String[] args){
        //Generate all elements
        Simulation simulation = new Simulation();
        int maxT;
        int maxS;
        int maxW; 
        try{
            File config = new File("dataFile.txt");
            Scanner scnr = new Scanner(config);
            maxT = scnr.nextInt();
            maxS = scnr.nextInt();
            maxW = scnr.nextInt();
            scnr.close();
            simulation.setElements(maxT, maxS,maxW);
        }catch(IOException e){
            System.err.println("Error in configuring simulation");
        }
        
        simulation.generateTrucks();
        simulation.generateWarehouses();
        simulation.generateShipments();
        Clock clock = new Clock(simulation.trucks, simulation.warehouses);
        int countDone = 0;
        while(true){
            clock.tick();       
            countDone = 0;
            for(int i= 0; i<simulation.trucks.size(); i++){
                if(simulation.trucks.get(i).getStatus() == Status.DONE)countDone++;
            }

            if(countDone >=simulation.trucks.size()){
                System.out.println("Time taken: "+clock.getTime()+" hours");
                break;
            }
        }

    }

}
