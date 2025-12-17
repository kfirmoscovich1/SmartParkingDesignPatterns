package parking.patterns.factory;

import parking.core.Car;
import parking.core.Vehicle;

/**
 * The {@code CarFactory} class is responsible for creating {@code Car} instances.
 * This is part of the Factory Method design pattern.
 *
 * @author Smart Parking System Team
 */
public class CarFactory implements VehicleFactory {
    /**
     * Creates a new {@code Car} with the specified parameters.
     *
     * @param licensePlate The license plate of the car.
     * @param ownerName The name of the car owner.
     * @param isDisabled Whether this is a disabled person's car.
     * @param color The color of the car.
     * @return A new {@code Car} instance.
     */
    @Override
    public Vehicle createVehicle(String licensePlate, String ownerName, boolean isDisabled, String color) {
        return new Car(licensePlate, ownerName, isDisabled, color);
    }
}