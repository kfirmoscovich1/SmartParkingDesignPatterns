package parking.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.exceptions.*;

/**
 * Tests for the custom exception classes.
 */
public class ExceptionTest {
    
    /**
     * Tests ParkingException with message.
     */
    @Test
    public void testParkingExceptionMessage() {
        ParkingException exception = new ParkingException("Test error");
        assertEquals("Test error", exception.getMessage());
    }
    
    /**
     * Tests ParkingException with message and cause.
     */
    @Test
    public void testParkingExceptionWithCause() {
        Exception cause = new RuntimeException("Original error");
        ParkingException exception = new ParkingException("Wrapped error", cause);
        
        assertEquals("Wrapped error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
    
    /**
     * Tests VehicleNotFoundException.
     */
    @Test
    public void testVehicleNotFoundException() {
        VehicleNotFoundException exception = new VehicleNotFoundException("ABC123");
        
        assertEquals("Vehicle not found: ABC123", exception.getMessage());
        assertEquals("ABC123", exception.getLicensePlate());
    }
    
    /**
     * Tests ParkingFullException default message.
     */
    @Test
    public void testParkingFullExceptionDefault() {
        ParkingFullException exception = new ParkingFullException();
        assertEquals("Parking lot is full. No available spots.", exception.getMessage());
    }
    
    /**
     * Tests ParkingFullException custom message.
     */
    @Test
    public void testParkingFullExceptionCustomMessage() {
        ParkingFullException exception = new ParkingFullException("No disabled spots available");
        assertEquals("No disabled spots available", exception.getMessage());
    }
    
    /**
     * Tests InvalidSubscriptionException.
     */
    @Test
    public void testInvalidSubscriptionException() {
        InvalidSubscriptionException exception = new InvalidSubscriptionException("SUB123");
        
        assertEquals("Invalid or expired subscription: SUB123", exception.getMessage());
        assertEquals("SUB123", exception.getSubscriptionId());
    }
    
    /**
     * Tests DuplicateVehicleException.
     */
    @Test
    public void testDuplicateVehicleException() {
        DuplicateVehicleException exception = new DuplicateVehicleException("XYZ789");
        
        assertEquals("Vehicle is already parked: XYZ789", exception.getMessage());
        assertEquals("XYZ789", exception.getLicensePlate());
    }
    
    /**
     * Tests that exceptions are throwable.
     */
    @Test
    public void testExceptionsAreThrowable() {
        assertThrows(VehicleNotFoundException.class, () -> {
            throw new VehicleNotFoundException("TEST");
        });
        
        assertThrows(ParkingFullException.class, () -> {
            throw new ParkingFullException();
        });
        
        assertThrows(InvalidSubscriptionException.class, () -> {
            throw new InvalidSubscriptionException("TEST");
        });
        
        assertThrows(DuplicateVehicleException.class, () -> {
            throw new DuplicateVehicleException("TEST");
        });
    }
    
    /**
     * Tests inheritance hierarchy.
     */
    @Test
    public void testExceptionHierarchy() {
        VehicleNotFoundException vnf = new VehicleNotFoundException("TEST");
        ParkingFullException pf = new ParkingFullException();
        InvalidSubscriptionException is = new InvalidSubscriptionException("TEST");
        DuplicateVehicleException dv = new DuplicateVehicleException("TEST");
        
        assertTrue(vnf instanceof ParkingException);
        assertTrue(pf instanceof ParkingException);
        assertTrue(is instanceof ParkingException);
        assertTrue(dv instanceof ParkingException);
        
        assertTrue(vnf instanceof RuntimeException);
        assertTrue(pf instanceof RuntimeException);
    }
}
