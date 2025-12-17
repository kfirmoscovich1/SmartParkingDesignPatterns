package parking.patterns.factory;

import parking.core.Vehicle;

/**
 * The {@code VehicleFactory} interface defines a factory method for creating vehicles.
 * This is part of the Factory Method design pattern.
 *
 * @author Smart Parking System Team
 */
public interface VehicleFactory {
    /**
     * Creates a new vehicle with the specified parameters.
     *
     * @param licensePlate The license plate of the vehicle.
     * @param ownerName The name of the vehicle owner.
     * @param isDisabled Whether this is a disabled person's vehicle.
     * @param color The color of the vehicle.
     * @return A new vehicle instance.
     */
    Vehicle createVehicle(String licensePlate, String ownerName, boolean isDisabled, String color);
}