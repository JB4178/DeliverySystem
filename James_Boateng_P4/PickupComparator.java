import java.util.*;

/**
 * Write a description of class PickupComparator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PickupComparator implements Comparator<Shipment>
{
    Truck truck;

    public PickupComparator(Truck truck){
        this.truck = truck;
    }

    public int compare(Shipment s1, Shipment s2){ 
        int distComp = Integer.compare(distance(s1.source), distance(s2.source));
        if (distComp != 0) {
            return distComp;
        }
        return Integer.compare(s2.id, s1.id);
    }

    public int distance(Warehouse warehouse){
        return (int)Math.sqrt(Math.pow(truck.getX()-warehouse.getX(), 2) + Math.pow((truck.getY() - warehouse.getY()), 2));
    }

}
