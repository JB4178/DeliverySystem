

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TruckTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TruckTest
{
    Truck test;
    /**
     * Default constructor for test class TruckTest
     */
    public TruckTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        test = new Truck(2);
    }

    /**
     * Tests the printPos() method
     */
    @Test
    public void test_printPos(){
        assertEquals("Pos:",test.printPos());
    }
    
    /**
     * Tests the getSpeed() method
     */
    @Test
    public void test_getSpeed(){
        assertEquals(2, test.getSpeed());
    }
    
    /**
     * Tests the move method
     */
    @Test
    public void test_move(){
        Truck truck = new Truck(1);
        Warehouse source = new Warehouse();
        Warehouse destination = new Warehouse();
        truck.setPos(3,4);
        source.setPos(5,4);
        destination.setPos(1,4);
        Shipment shipment = new Shipment(1, source, destination);
        truck.addToManifest(shipment);
        truck.setSpeed(2);
        
        for(int i = 0; i<3; i++){
            truck.action();
        }
        
        assertEquals(Status.WAIT, truck.getStatus());
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
