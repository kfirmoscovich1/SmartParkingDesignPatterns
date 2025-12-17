package parking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.core.Vehicle;
import parking.patterns.builder.ParkingReport;
import parking.patterns.facade.ParkingSystemFacade;
import parking.patterns.factory.VehicleType;
import parking.management.SubscriptionType;

/**
 * Tests for the Facade pattern implementation.
 */
public class FacadeTest {
    
    private ParkingSystemFacade facade;
    
    @BeforeEach
    public void setUp() {
        facade = new ParkingSystemFacade();
    }
    
    /**
     * Tests creating a car through the facade.
     */
    @Test
    public void testCreateCarVehicle() {
        Vehicle vehicle = facade.createVehicle(VehicleType.CAR, "ABC123", "John Doe", false, "Blue");
        
        assertNotNull(vehicle);
        assertEquals("ABC123", vehicle.getLicensePlate());
        assertEquals("John Doe", vehicle.getOwnerName());
        assertEquals("Blue", vehicle.getColor());
        assertFalse(vehicle.isDisabled());
    }
    
    /**
     * Tests creating a motorcycle through the facade.
     */
    @Test
    public void testCreateMotorcycleVehicle() {
        Vehicle vehicle = facade.createVehicle(VehicleType.MOTORCYCLE, "XYZ789", "Jane Smith", false, "Red");
        
        assertNotNull(vehicle);
        assertEquals("XYZ789", vehicle.getLicensePlate());
        assertEquals("Jane Smith", vehicle.getOwnerName());
        assertEquals("Red", vehicle.getColor());
    }
    
    /**
     * Tests creating a vehicle with default color.
     */
    @Test
    public void testCreateVehicleDefaultColor() {
        Vehicle vehicle = facade.createVehicle(VehicleType.CAR, "DEF456", "Test User", false);
        
        assertNotNull(vehicle);
        assertEquals("Unknown", vehicle.getColor());
    }
    
    /**
     * Tests parking a vehicle through the facade.
     */
    @Test
    public void testParkVehicle() {
        Vehicle vehicle = facade.createVehicle(VehicleType.CAR, "PARK001", "Parker", false, "White");
        boolean success = facade.parkVehicle(vehicle);
        
        assertTrue(success);
    }
    
    /**
     * Tests that occupancy increases after parking.
     */
    @Test
    public void testOccupancyIncreasesAfterParking() {
        double initialOccupancy = facade.getOccupancyPercentage();
        
        Vehicle vehicle = facade.createVehicle(VehicleType.CAR, "OCC001", "Test", false, "Black");
        facade.parkVehicle(vehicle);
        
        double newOccupancy = facade.getOccupancyPercentage();
        assertTrue(newOccupancy >= initialOccupancy);
    }
    
    /**
     * Tests removing a vehicle through the facade.
     */
    @Test
    public void testRemoveVehicle() {
        Vehicle vehicle = facade.createVehicle(VehicleType.CAR, "REM001", "Remover", false, "Gray");
        facade.parkVehicle(vehicle);
        
        double payment = facade.removeVehicle("REM001");
        
        assertTrue(payment >= 0);
    }
    
    /**
     * Tests removing a non-existent vehicle.
     */
    @Test
    public void testRemoveNonExistentVehicle() {
        double payment = facade.removeVehicle("NOTEXIST");
        assertEquals(-1, payment);
    }
    
    /**
     * Tests creating a standard subscription.
     */
    @Test
    public void testCreateStandardSubscription() {
        String subscriptionId = facade.createSubscription("SUB001", "Subscriber", 6);
        
        assertNotNull(subscriptionId);
        assertFalse(subscriptionId.isEmpty());
    }
    
    /**
     * Tests creating a premium subscription.
     */
    @Test
    public void testCreatePremiumSubscription() {
        String subscriptionId = facade.createSubscription("PREM001", "Premium User", 12, SubscriptionType.PREMIUM);
        
        assertNotNull(subscriptionId);
        assertFalse(subscriptionId.isEmpty());
    }
    
    /**
     * Tests parking a subscriber vehicle.
     */
    @Test
    public void testParkSubscriberVehicle() {
        String subscriptionId = facade.createSubscription("SUBCAR001", "Sub Owner", 12);
        Vehicle vehicle = facade.createVehicle(VehicleType.CAR, "SUBCAR001", "Sub Owner", false, "Silver");
        
        boolean success = facade.parkSubscriberVehicle(vehicle, subscriptionId);
        
        assertTrue(success);
    }
    
    /**
     * Tests parking with invalid subscription fails.
     */
    @Test
    public void testParkWithInvalidSubscription() {
        Vehicle vehicle = facade.createVehicle(VehicleType.CAR, "INVALID001", "Test", false, "Black");
        
        boolean success = facade.parkSubscriberVehicle(vehicle, "INVALID_SUB_ID");
        
        assertFalse(success);
    }
    
    /**
     * Tests getting available spots.
     */
    @Test
    public void testGetAvailableSpots() {
        int availableSpots = facade.getAvailableSpots();
        assertTrue(availableSpots >= 0);
    }
    
    /**
     * Tests generating a daily report.
     */
    @Test
    public void testGenerateDailyReport() {
        ParkingReport report = facade.generateDailyReport();
        
        assertNotNull(report);
    }
    
    /**
     * Tests subscriber removal returns 0 payment.
     */
    @Test
    public void testSubscriberRemovalNoPayment() {
        String subscriptionId = facade.createSubscription("FREE001", "Free Parker", 12);
        Vehicle vehicle = facade.createVehicle(VehicleType.CAR, "FREE001", "Free Parker", false, "Gold");
        facade.parkSubscriberVehicle(vehicle, subscriptionId);
        
        double payment = facade.removeVehicle("FREE001");
        
        assertEquals(0, payment);
    }
}
