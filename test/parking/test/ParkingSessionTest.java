package parking.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.core.Car;
import parking.core.ParkingSession;
import parking.core.ParkingSpot;

import java.time.LocalDateTime;

import java.lang.reflect.Field;

/**
 * Tests for the ParkingSession functionality.
 */
public class ParkingSessionTest {

    /**
     * Tests creating a new parking session.
     */
    @Test
    public void testCreateSession() {
        Car car = new Car("123456", "Test Owner", false, "Blue");
        ParkingSpot spot = new ParkingSpot(1, false);

        ParkingSession session = new ParkingSession(car, spot, false);

        // Check that the session is correctly initialized
        assertEquals(car, session.getVehicle());
        assertEquals(spot, session.getParkingSpot());
        assertFalse(session.isSubscription());
        assertNull(session.getExitTime()); // Not ended yet
        assertEquals(0.0, session.getAmountPaid()); // Not paid yet
        assertNotNull(session.getEntryTime()); // Entry time should be set
        assertNotNull(car.getEntryTime()); // Vehicle entry time should also be set
    }

    /**
     * Tests ending a parking session.
     */
    @Test
    public void testEndSession() {
        Car car = new Car("123456", "Test Owner", false, "Red");
        ParkingSpot spot = new ParkingSpot(1, false);

        ParkingSession session = new ParkingSession(car, spot, false);

        // End the session
        session.endSession();

        // Check that exit time is set
        assertNotNull(session.getExitTime());
    }

    /**
     * Tests calculating the duration of a parking session.
     */
    @Test
    public void testGetDurationHours() throws Exception {
        Car car = new Car("123456", "Test Owner", false, "Green");
        ParkingSpot spot = new ParkingSpot(1, false);

        ParkingSession session = new ParkingSession(car, spot, false);

        // Set entry time to 3 hours ago
        LocalDateTime entryTime = LocalDateTime.now().minusHours(3);
        setEntryTime(session, entryTime);
        car.setEntryTime(entryTime);

        // Calculate duration (should be close to 3 hours)
        double duration = session.getDurationHours();
        assertTrue(Math.abs(duration - 3.0) < 0.1); // Allow small difference due to execution time

        // End the session
        session.endSession();

        // Set exit time to 4 hours after entry time
        setExitTime(session, entryTime.plusHours(4));

        // Calculate duration (should now be exactly 4 hours)
        duration = session.getDurationHours();
        assertEquals(4.0, duration);
    }

    /**
     * Tests recording payment for a parking session.
     */
    @Test
    public void testRecordPayment() {
        Car car = new Car("123456", "Test Owner", false, "Yellow");
        ParkingSpot spot = new ParkingSpot(1, false);

        ParkingSession session = new ParkingSession(car, spot, false);

        // Initially no payment
        assertEquals(0.0, session.getAmountPaid());

        // Record a payment
        session.recordPayment(50.0);

        // Check the payment
        assertEquals(50.0, session.getAmountPaid());
    }

    /**
     * Tests subscription sessions.
     */
    @Test
    public void testSubscriptionSession() {
        Car car = new Car("123456", "Test Owner", false, "Black");
        ParkingSpot spot = new ParkingSpot(1, false);

        // Create a subscription session
        ParkingSession session = new ParkingSession(car, spot, true);

        // Check that it's a subscription
        assertTrue(session.isSubscription());
    }

    /**
     * Test session with disabled vehicle.     */
    @Test
    public void testDisabledVehicleSession() {
        Car disabledCar = new Car("DISABLED123", "Disabled Owner", true, "White");
        ParkingSpot disabledSpot = new ParkingSpot(4, true);

        ParkingSession session = new ParkingSession(disabledCar, disabledSpot, false);

        assertTrue(session.getVehicle().isDisabled());
        assertTrue(session.getParkingSpot().isDisabledSpot());
    }

    /**
     * Helper method to set the entry time using reflection.
     */
    private void setEntryTime(ParkingSession session, LocalDateTime time) throws Exception {
        Field entryTimeField = ParkingSession.class.getDeclaredField("entryTime");
        entryTimeField.setAccessible(true);
        entryTimeField.set(session, time);
    }

    /**
     * Helper method to set the exit time using reflection.
     */
    private void setExitTime(ParkingSession session, LocalDateTime time) throws Exception {
        Field exitTimeField = ParkingSession.class.getDeclaredField("exitTime");
        exitTimeField.setAccessible(true);
        exitTimeField.set(session, time);
    }
}
