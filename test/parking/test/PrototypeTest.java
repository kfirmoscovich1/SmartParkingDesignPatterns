package parking.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.core.Car;
import parking.core.Motorcycle;
import parking.core.Vehicle;
import parking.patterns.prototype.VehicleCloner;

/**
 * Tests for the Prototype pattern implementation.
 */
public class PrototypeTest {
    
    /**
     * Tests cloning a Car object.
     */
    @Test
    public void testCloneCar() {
        Car original = new Car("ABC123", "John Doe", false, "Blue");
        Car clone = original.clone();
        
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertEquals(original.getLicensePlate(), clone.getLicensePlate());
        assertEquals(original.getOwnerName(), clone.getOwnerName());
        assertEquals(original.isDisabled(), clone.isDisabled());
        assertEquals(original.getColor(), clone.getColor());
        assertEquals(original.getHourlyRate(), clone.getHourlyRate());
    }
    
    /**
     * Tests cloning a Motorcycle object.
     */
    @Test
    public void testCloneMotorcycle() {
        Motorcycle original = new Motorcycle("XYZ789", "Jane Smith", false, "Red");
        Motorcycle clone = original.clone();
        
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertEquals(original.getLicensePlate(), clone.getLicensePlate());
        assertEquals(original.getOwnerName(), clone.getOwnerName());
        assertEquals(original.isDisabled(), clone.isDisabled());
        assertEquals(original.getColor(), clone.getColor());
    }
    
    /**
     * Tests that VehicleCloner.cloneVehicle works correctly.
     */
    @Test
    public void testVehicleClonerWithCar() {
        Car original = new Car("TEST123", "Test Owner", true, "Green");
        Vehicle clone = VehicleCloner.cloneVehicle(original);
        
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertTrue(clone instanceof Car);
        assertEquals("TEST123", clone.getLicensePlate());
        assertEquals("Test Owner", clone.getOwnerName());
        assertTrue(clone.isDisabled());
    }
    
    /**
     * Tests that VehicleCloner.cloneVehicle works with Motorcycle.
     */
    @Test
    public void testVehicleClonerWithMotorcycle() {
        Motorcycle original = new Motorcycle("MOTO001", "Biker Bob", false, "Black");
        Vehicle clone = VehicleCloner.cloneVehicle(original);
        
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertTrue(clone instanceof Motorcycle);
        assertEquals("MOTO001", clone.getLicensePlate());
    }
    
    /**
     * Tests that VehicleCloner handles null input.
     */
    @Test
    public void testVehicleClonerWithNull() {
        Vehicle clone = VehicleCloner.cloneVehicle(null);
        assertNull(clone);
    }
    
    /**
     * Tests that modifying clone doesn't affect original.
     */
    @Test
    public void testCloneIndependence() {
        Car original = new Car("ORIG001", "Original Owner", false, "White");
        Car clone = original.clone();
        
        // Modify the clone
        clone.setLicensePlate("CLONE001");
        clone.setOwnerName("Clone Owner");
        clone.setDisabled(true);
        
        // Verify original is unchanged
        assertEquals("ORIG001", original.getLicensePlate());
        assertEquals("Original Owner", original.getOwnerName());
        assertFalse(original.isDisabled());
    }
    
    /**
     * Tests cloning a disabled vehicle preserves disabled status.
     */
    @Test
    public void testCloneDisabledVehicle() {
        Car original = new Car("DIS001", "Disabled Owner", true, "Yellow");
        Car clone = original.clone();
        
        assertTrue(clone.isDisabled());
        assertEquals(8.0, clone.getHourlyRate()); // Disabled car rate
    }
}
