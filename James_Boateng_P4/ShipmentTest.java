

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class ShipmentTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ShipmentTest
{
    Shipment test;
    /**
     * Default constructor for test class ShipmentTest
     */
    public ShipmentTest()
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
       
    }

    /**
     * Tests the size
     */
    @Test
    public void test_size(){
        assertEquals(1,test.getSize());
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
