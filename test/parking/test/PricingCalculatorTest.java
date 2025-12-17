package parking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.core.Car;
import parking.core.Motorcycle;
import parking.core.ParkingSession;
import parking.core.ParkingSpot;
import parking.management.PricingCalculator;

import java.time.LocalDateTime;
import java.lang.reflect.Field;

/**
 * Tests for the PricingCalculator class.
 */
public class PricingCalculatorTest {

    private PricingCalculator calculator;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        calculator = new PricingCalculator();
    }

    /**
     * Tests the free hours policy (first 2 hours free).
     */
    @Test
    public void testFreeHoursPolicy() throws Exception {
        Car car = new Car("123456", "Test Owner", false, "Blue");
        ParkingSpot spot = new ParkingSpot(1, false);
        ParkingSession session = new ParkingSession(car, spot, false);
        setEntryTimeHoursAgo(session, 1);
        double fee = calculator.calculateFee(session);
        assertEquals(0.0, fee);
    }

    /**
     * Tests the fee for a regular car with parking duration > 2 hours.
     */
    @Test
    public void testRegularCarFee() throws Exception {
        Car car = new Car("123456", "Test Owner", false, "Red");
        ParkingSpot spot = new ParkingSpot(1, false);
        ParkingSession session = new ParkingSession(car, spot, false);
        setEntryTimeHoursAgo(session, 3);
        double fee = calculator.calculateFee(session);
        assertEquals(18.0, fee);
        setEntryTimeHoursAgo(session, 5.5);
        fee = calculator.calculateFee(session);
        assertEquals(72.0, fee);
    }

    /**
     * Tests the fee for a disabled person's car.
     */
    @Test
    public void testDisabledCarFee() throws Exception {
        Car car = new Car("123456", "Test Owner", true, "White");
        ParkingSpot spot = new ParkingSpot(1, true);
        ParkingSession session = new ParkingSession(car, spot, false);
        setEntryTimeHoursAgo(session, 4);
        double fee = calculator.calculateFee(session);
        assertEquals(16.0, fee);
    }

    /**
     * Tests the fee for a motorcycle.
     */
    @Test
    public void testMotorcycleFee() throws Exception {
        Motorcycle motorcycle = new Motorcycle("789012", "Test Rider", false, "Black");
        ParkingSpot spot = new ParkingSpot(1, false);
        ParkingSession session = new ParkingSession(motorcycle, spot, false);
        setEntryTimeHoursAgo(session, 3);
        double fee = calculator.calculateFee(session);
        assertEquals(12.0, fee);
    }

    /**
     * Tests that subscribers pay no fee.
     */
    @Test
    public void testSubscriberFee() throws Exception {
        Car car = new Car("123456", "Test Owner", false, "Green");
        ParkingSpot spot = new ParkingSpot(1, false);
        ParkingSession session = new ParkingSession(car, spot, true);
        setEntryTimeHoursAgo(session, 5);
        double fee = calculator.calculateFee(session);
        assertEquals(0.0, fee);
    }

    /**
     * Test minimum parking duration pricing.
     */
    @Test
    public void testMinimumDurationRounding() throws Exception {
        Car testCar = new Car("QUICK123", "Quick Parker", false, "Purple");
        ParkingSpot spot = new ParkingSpot(4, false);
        ParkingSession session = new ParkingSession(testCar, spot, false);
        setEntryTimeHoursAgo(session, 2.1);
        double fee = calculator.calculateFee(session);
        assertEquals(18.0, fee);
    }

    /**
     * Test using calculateCost alias method.
     */
    @Test
    public void testCalculateCostAlias() throws Exception {
        Car car = new Car("ALIAS123", "Test Owner", false, "Orange");
        ParkingSpot spot = new ParkingSpot(5, false);
        ParkingSession session = new ParkingSession(car, spot, false);
        setEntryTimeHoursAgo(session, 3);
        double fee = calculator.calculateFee(session);
        double cost = calculator.calculateCost(session);
        assertEquals(fee, cost);
        assertEquals(18.0, cost);
    }

    /**
     * Helper method to set the entry time to a specific number of hours ago.
     */
    private void setEntryTimeHoursAgo(ParkingSession session, double hoursAgo) throws Exception {
        LocalDateTime entryTime = LocalDateTime.now().minusMinutes((long)(hoursAgo * 60));

        // Update entryTime in ParkingSession
        Field entryTimeField = ParkingSession.class.getDeclaredField("entryTime");
        entryTimeField.setAccessible(true);
        entryTimeField.set(session, entryTime);

        // Update entryTime in the vehicle, whether it's Car or Motorcycle
        Field vehicleField = ParkingSession.class.getDeclaredField("vehicle");
        vehicleField.setAccessible(true);
        Object vehicle = vehicleField.get(session);
        vehicle.getClass().getMethod("setEntryTime", LocalDateTime.class).invoke(vehicle, entryTime);
    }
}
