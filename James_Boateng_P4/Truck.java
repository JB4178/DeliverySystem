import java.util.Random;   
import java.util.*;
import java.io.*;

/**
 * Write a description of class Truck here.
 *
 * @author (James Boateng)
 * @version (11/28/2022)
 */
public class Truck implements Schedule
{
    // instance variables - replace the example below with your own
    final int maxRange = 100;
    int numDeliveries = 0;
    Status stage;
    Random random;
    double xPos;
    double yPos;
    int maxCap;
    int id;
    int speed;
    private Stack<Shipment> cargo;
    private List<Shipment> manifest;
    Shipment currShip;
    boolean isReady;

    public Truck(int id)
    {
        // initialise instance variables
        this.id = id;
        manifest = new ArrayList();
        cargo = new Stack();
        stage = Status.MOVE;
        numDeliveries = 0;
        random = new Random();
        xPos = Math.abs(random.nextInt(maxRange)+1);
        yPos = Math.abs(random.nextInt(maxRange)+1);
        maxCap = 1+ random.nextInt(5);
        isReady = false;
        //Set speed
        for(int i = 1; i<6; i++){
            if(i == maxCap){
                speed = 6-i;
            }
        }
    }

    /**
     * Sets speed for testing
     */
    public void setSpeed(int c){
        speed = c;
    }

    /**
     * Sets the position for testing
     */
    public void setPos(double x, double y){
        xPos = x;
        yPos = y;
    }

    /**
     * Prints the position of the truck
     */
    public String printPos(){
        return "Pos:" + xPos+","+yPos;
    }

    /**
     * Returns the speed
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * Returns current shipment
     */
    public Shipment getShipment(){
        return currShip;
    }

    /**
     * Moves the truck in a direction
     */
    public void move(){
        /*
        int slope;
        int dirX =1;
        int dirY = 1;
        if(!currShip.picked()){
        slope = (currShip.source.getY()-yPos)/(currShip.source.getX()-xPos);
        if(xPos>currShip.source.getX()){
        dirX = -1;
        }
        if(yPos>currShip.source.getY()){
        dirY = -1;
        }
        }else{
        slope = (currShip.destination.getY()-yPos)/(currShip.destination.getX()-xPos);
        if(xPos>currShip.destination.getX()){
        dirX = -1;
        }
        if(yPos>currShip.destination.getY()){
        dirY = -1;
        }
        }

        double theta = Math.atan(slope);
        int xDist = dirX*speed*(int)Math.cos(theta);
        int yDist = dirY*speed*(int)Math.sin(theta);
         */
        double polar = 0.0;
        if(!currShip.picked()){
            double dx = currShip.source.getX() - xPos;
            double dy = currShip.source.getY() - yPos;
            polar =  Math.atan2(dy, dx);
        }else{
            double dx = currShip.destination.getX() - xPos;
            double dy = currShip.destination.getY() - yPos;
            polar =  Math.atan2(dy, dx);
        }
        
        double xDist  = speed * Math.cos(polar);
        double yDist = (speed * Math.sin(polar));
        
        //Update the position of x and 
        xPos = xPos + xDist;
        yPos = yPos + yDist;

    }

    /**
     * Returns the x value
     */
    public double getX(){
        return xPos;
    }

    /**
     * Returns the y value
     */
    public double getY(){
        return yPos;
    }

    /**
     * Pick up method
     */
    public void pickup(Shipment item){
        //Check if cap is full then add in FIFO order
        if(cargo.size()<maxCap){
            cargo.push(item);
        }
    }

    /**
     * Drops off the shipment at destination
     */
    public Shipment dropOff(){
        return cargo.pop();
    }

    public void action(){
        // Check the current stage of the truck
        switch (stage) {
                // If the current stage is "pickup", attempt to pick up a shipment
            case PICK_UP:
                // TODO: Determine the next pickup for the truck and pick up the shipment
                currShip.source.loadShipment(this);
                if(!stage.equals(Status.WAIT)){
                    currShip.setPicked(true);
                    updateShipmentPriority();
                    if(currShip!=null){
                        stage = Status.MOVE;
                    }else{
                        stage = Status.DONE;
                    }
                }
                break;
                // If the current stage is "move", move the truck towards its destination
            case MOVE:
                updateShipmentPriority();
                if(currShip!= null){
                    move();
                    if (distance(currShip.destination) < ((maxRange*20)/100) && currShip.picked()) {
                        stage = Status.UNLOAD;
                    }else if(distance(currShip.source) < ((maxRange*20)/100) && !currShip.picked()){
                        stage = Status.PICK_UP;
                    }
                }else{
                    stage = Status.DONE;
                }
                break;
                // If the current stage is "unload", unload the shipment at the destination warehouse
            case UNLOAD:
                currShip.destination.unloadShipment(this);
                if(!stage.equals(Status.WAIT)){
                    numDeliveries++;
                    updateShipmentPriority();
                    if(currShip!=null){
                        stage = Status.MOVE;
                    }else{
                        stage = Status.DONE;
                    }
                }
                break;
                // If the current stage is "wait" and done, do not perform any action
            case WAIT:
            case DONE:
                break;

        }
        log_status();
    }

    /**
     * Gets the next destination
     */
    public Shipment getNextPickup() {
        // Find the shipments that have not been picked up by the truck
        List<Shipment> eligibleShipments = new ArrayList<>();
        PickupComparator pickComp = new PickupComparator(this);
        for (Shipment shipment : manifest) {
            if (!shipment.picked()) {
                eligibleShipments.add(shipment);
            }
        }

        // Sort the eligible shipments by distance and ID
        Collections.sort(eligibleShipments, pickComp);

        // Return the first shipment in the sorted list
        if(eligibleShipments.size()>0){
            return eligibleShipments.get(0);
        }else{
            return null;
        }
    }

    /**
     * Calculates the distance to a warehouse
     */
    public double distance(Warehouse destination){
        return Math.sqrt(Math.pow(xPos-destination.getX(), 2) + Math.pow((yPos - destination.getY()), 2));
    }

    /**
     * Sets the stage
     * @param the current stage
     */
    public void setStage(Status stage){
        this.stage = stage;
    }

    /**
     * Determines the priority of shipments and deliveries
     */
    public void updateShipmentPriority(){
        if(!cargo.isEmpty()){
            Shipment pick = getNextPickup();
            if(pick!= null){
                if(distance(pick.source) > distance(cargo.peek().destination)){
                    currShip = cargo.peek();
                }else{
                    currShip = pick;
                }
            }else{
                currShip = cargo.peek();
            }
        }else{
            currShip = getNextPickup();
        }
    }

    /**
     * Returns the status
     */
    public Status getStatus(){
        return stage;
    }

    public void log_status() {
        try{
            // Open the log file for writing
            File file = new File("truck_log.csv");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
           // PrintWriter pw = new PrintWriter("truck_log.csv");
            // Write the current status of the truck to the log file
            fw.write("ID,Loadsize,CurrentX,CurrentY,CurrentSpeed,CurrentStage\n");
            fw.write(id+","+maxCap +","+xPos + "," + yPos+","+ speed+","+stage+"\n");
            // Close the log file
            fw.close();
            fw.close();
        }catch(IOException e){
            System.err.println("Error with filewriting");
        }
    }

    /**
     * Adds shipment to manifest at beginning of simulation
     */
    public void addToManifest(Shipment shipment){
        manifest.add(shipment);
    }
}
