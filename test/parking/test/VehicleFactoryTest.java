package parking.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.core.Car;
import parking.core.Motorcycle;
import parking.core.Vehicle;
import parking.patterns.factory.VehicleFactory;
import parking.patterns.factory.CarFactory;
import parking.patterns.factory.MotorcycleFactory;
import parking.patterns.factory.VehicleFactoryProvider;
import parking.patterns.factory.VehicleType;

/**
 * Tests for the Vehicle Factory pattern implementation.
 */
public class VehicleFactoryTest {    /**
     * Tests that the CarFactory creates Car instances.
     */
    @Test
    public void testCarFactoryCreation() {
        VehicleFactory carFactory = new CarFactory();
        Vehicle vehicle = carFactory.createVehicle("ABC123", "John Doe", false, "Blue");

        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Car);
        assertEquals("ABC123", vehicle.getLicensePlate());
        assertEquals("John Doe", vehicle.getOwnerName());
        assertEquals("Blue", vehicle.getColor());
        assertFalse(vehicle.isDisabled());
        assertEquals(18.0, vehicle.getHourlyRate());
    }    /**
     * Tests that the MotorcycleFactory creates Motorcycle instances.
     */
    @Test
    public void testMotorcycleFactoryCreation() {
        VehicleFactory factory = new MotorcycleFactory();
        Vehicle vehicle = factory.createVehicle("789012", "Test Rider", false, "Black");

        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Motorcycle);
        assertEquals("789012", vehicle.getLicensePlate());
        assertEquals("Test Rider", vehicle.getOwnerName());
        assertEquals("Black", vehicle.getColor());
        assertFalse(vehicle.isDisabled());
        assertEquals(12.0, vehicle.getHourlyRate());
    }    /**
     * Tests that the VehicleFactoryProvider returns appropriate factories.
     */
    @Test
    public void testVehicleFactoryProvider() {
        VehicleFactory carFactory = VehicleFactoryProvider.getFactory(VehicleType.CAR);
        VehicleFactory motorcycleFactory = VehicleFactoryProvider.getFactory(VehicleType.MOTORCYCLE);

        assertNotNull(carFactory);
        assertNotNull(motorcycleFactory);
        assertTrue(carFactory instanceof CarFactory);
        assertTrue(motorcycleFactory instanceof MotorcycleFactory);
    }    /**
     * Tests disabled vehicle creation and rates.
     */
    @Test
    public void testDisabledVehiclePricing() {
        VehicleFactory carFactory = new CarFactory();
        VehicleFactory motorcycleFactory = new MotorcycleFactory();

        Vehicle disabledCar = carFactory.createVehicle("123456", "Disabled Owner", true, "White");
        Vehicle disabledMotorcycle = motorcycleFactory.createVehicle("789012", "Disabled Rider", true, "Red");

        assertTrue(disabledCar.isDisabled());
        assertTrue(disabledMotorcycle.isDisabled());

        // Both disabled vehicles should use the special rate (8₪)
        assertEquals(8.0, disabledCar.getHourlyRate());
        assertEquals(8.0, disabledMotorcycle.getHourlyRate());
    }    /**
     * Tests vehicle color assignment through factory.
     */
    @Test
    public void testVehicleColorAssignment() {
        VehicleFactory carFactory = VehicleFactoryProvider.getFactory(VehicleType.CAR);

        String[] colors = {"Red", "Blue", "Green", "White", "Black", "Silver"};

        for (String color : colors) {
            Vehicle vehicle = carFactory.createVehicle("COLOR" + color, "Owner", false, color);
            assertEquals(color, vehicle.getColor());
        }
    }    /**
     * Tests creating vehicles with various owner names.
     */
    @Test
    public void testVehicleOwnerNames() {
        VehicleFactory factory = VehicleFactoryProvider.getFactory(VehicleType.CAR);

        String[] ownerNames = {
                "Israel Israeli",
                "Moshe Cohen",
                "Sarah Levi",
                "David Ben-David",
                "Miriam Goldstein"
        };

        for (String ownerName : ownerNames) {
            Vehicle vehicle = factory.createVehicle("OWNER123", ownerName, false, "Blue");
            assertEquals(ownerName, vehicle.getOwnerName());
        }
    }    /**
     * Tests creating multiple vehicles with the same factory.
     */
    @Test
    public void testMultipleVehicleCreation() {
        VehicleFactory carFactory = new CarFactory();

        Vehicle car1 = carFactory.createVehicle("CAR001", "Owner 1", false, "Red");
        Vehicle car2 = carFactory.createVehicle("CAR002", "Owner 2", true, "Blue");
        Vehicle car3 = carFactory.createVehicle("CAR003", "Owner 3", false, "Green");

        assertNotNull(car1);
        assertNotNull(car2);
        assertNotNull(car3);

        assertNotSame(car1, car2);
        assertNotSame(car2, car3);
        assertNotSame(car1, car3);

        assertEquals("CAR001", car1.getLicensePlate());
        assertEquals("CAR002", car2.getLicensePlate());
        assertEquals("CAR003", car3.getLicensePlate());
    }    /**
     * Tests that each factory type creates the correct vehicle type.
     */
    @Test
    public void testFactoryTypeConsistency() {
        VehicleFactory carFactory = VehicleFactoryProvider.getFactory(VehicleType.CAR);
        VehicleFactory motorcycleFactory = VehicleFactoryProvider.getFactory(VehicleType.MOTORCYCLE);

        // Create 10 vehicles with each factory and verify they're all the correct type
        for (int i = 0; i < 10; i++) {
            Vehicle car = carFactory.createVehicle("CAR" + i, "Owner" + i, false, "Color" + i);
            Vehicle motorcycle = motorcycleFactory.createVehicle("MOTO" + i, "Rider" + i, false, "Color" + i);

            assertTrue(car instanceof Car);
            assertTrue(motorcycle instanceof Motorcycle);
        }
    }    /**
     * Tests vehicle cloning functionality.
     */
    @Test
    public void testVehicleCloning() {
        VehicleFactory factory = new CarFactory();
        Vehicle original = factory.createVehicle("ORIG123", "Original Owner", true, "Original Color");

        Vehicle clone = original.clone();

        assertNotNull(clone);
        assertNotSame(original, clone);
        assertEquals(original.getLicensePlate(), clone.getLicensePlate());
        assertEquals(original.getOwnerName(), clone.getOwnerName());
        assertEquals(original.isDisabled(), clone.isDisabled());
        assertEquals(original.getColor(), clone.getColor());
    }    /**
     * Tests edge cases for vehicle creation.
     */
    @Test
    public void testVehicleCreationEdgeCases() {
        VehicleFactory factory = new CarFactory();

        // Test with empty strings
        Vehicle vehicle1 = factory.createVehicle("", "", false, "");
        assertNotNull(vehicle1);
        assertEquals("", vehicle1.getLicensePlate());
        assertEquals("", vehicle1.getOwnerName());
        assertEquals("", vehicle1.getColor());

        // Test with special characters
        Vehicle vehicle2 = factory.createVehicle("!@#$%", "Name-With-Dash", false, "Color With Spaces");
        assertNotNull(vehicle2);
        assertEquals("!@#$%", vehicle2.getLicensePlate());
        assertEquals("Name-With-Dash", vehicle2.getOwnerName());
        assertEquals("Color With Spaces", vehicle2.getColor());
    }
}