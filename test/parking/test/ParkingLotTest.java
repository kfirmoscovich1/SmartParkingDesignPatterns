package parking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.core.Car;
import parking.core.Motorcycle;
import parking.core.ParkingSession;
import parking.management.ParkingLot;
import parking.reports.ParkingStatistics;

/**
 * Tests for the ParkingLot management functionality.
 */
public class ParkingLotTest {

    private ParkingLot parkingLot;

    @BeforeEach
    public void setUp() {
        parkingLot = ParkingLot.getInstance();
        parkingLot.reset(); // Reset for clean test state
        parkingLot.initialize(new ParkingStatistics()); // Initialize with statistics
    }    /**
     * Test basic parking operations.
     */
    @Test
    public void testBasicParkingOperations() {
        assertNotNull(parkingLot);
        assertTrue(parkingLot.getAvailableSpots() > 0);

        // Create a test vehicle
        Car testCar = new Car("TEST123", "Test Owner", false, "Blue");

        // Test parking
        ParkingSession session = parkingLot.parkVehicle(testCar);
        assertNotNull(session);
        assertTrue(session.isActive());

        // Test removing vehicle
        ParkingSession removedSession = parkingLot.removeVehicle("TEST123");
        assertNotNull(removedSession);
        assertFalse(removedSession.isActive());
    }    /**
     * Test disabled vehicle parking priority.
     */
    @Test
    public void testDisabledVehiclePriority() {
        Car disabledCar = new Car("DISABLED1", "Disabled Owner", true, "White");
        Car regularCar = new Car("REGULAR1", "Regular Owner", false, "Red");

        ParkingSession disabledSession = parkingLot.parkVehicle(disabledCar);
        ParkingSession regularSession = parkingLot.parkVehicle(regularCar);

        assertNotNull(disabledSession);
        assertNotNull(regularSession);

        // Disabled vehicle should get a disabled spot
        assertTrue(disabledSession.getParkingSpot().isDisabledSpot());
    }    /**
     * Test motorcycle parking.
     */
    @Test
    public void testMotorcycleParking() {
        Motorcycle motorcycle = new Motorcycle("BIKE123", "Rider", false, "Black");

        ParkingSession session = parkingLot.parkVehicle(motorcycle);
        assertNotNull(session);
        assertTrue(session.isActive());
        assertEquals(motorcycle, session.getVehicle());
    }    /**
     * Test parking lot capacity.
     */
    @Test
    public void testParkingLotCapacity() {
        int initialAvailableSpots = parkingLot.getAvailableSpots();
        assertTrue(initialAvailableSpots > 0);

        Car testCar = new Car("CAPACITY1", "Test Owner", false, "Green");
        parkingLot.parkVehicle(testCar);

        assertEquals(initialAvailableSpots - 1, parkingLot.getAvailableSpots());
    }    /**
     * Test duplicate vehicle parking prevention.
     */
    @Test
    public void testDuplicateVehiclePrevention() {
        Car testCar = new Car("DUPLICATE1", "Test Owner", false, "Yellow");

        // First parking should succeed
        assertTrue(parkingLot.parkVehicle(testCar, false));

        // Second parking of same vehicle should fail
        assertFalse(parkingLot.parkVehicle(testCar, false));
    }    /**
     * Test occupancy percentage calculation.
     */
    @Test
    public void testOccupancyPercentage() {
        double initialOccupancy = parkingLot.getOccupancyPercentage();

        Car testCar = new Car("OCCUPANCY1", "Test Owner", false, "Purple");
        parkingLot.parkVehicle(testCar);

        double newOccupancy = parkingLot.getOccupancyPercentage();
        assertTrue(newOccupancy > initialOccupancy);
    }
}