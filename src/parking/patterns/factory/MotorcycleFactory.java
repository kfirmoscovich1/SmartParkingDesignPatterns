package parking.patterns.factory;

import parking.core.Motorcycle;
import parking.core.Vehicle;

/**
 * The {@code MotorcycleFactory} class is responsible for creating {@code Motorcycle} instances.
 * This is part of the Factory Method design pattern.
 *
 * @author Smart Parking System Team
 */
public class MotorcycleFactory implements VehicleFactory {
    /**
     * Creates a new {@code Motorcycle} with the specified parameters.
     *
     * @param licensePlate The license plate of the motorcycle.
     * @param ownerName The name of the motorcycle owner.
     * @param isDisabled Whether this is a disabled person's motorcycle.
     * @param color The color of the motorcycle.
     * @return A new {@code Motorcycle} instance.
     */
    @Override
    public Vehicle createVehicle(String licensePlate, String ownerName, boolean isDisabled, String color) {
        return new Motorcycle(licensePlate, ownerName, isDisabled, color);
    }
}