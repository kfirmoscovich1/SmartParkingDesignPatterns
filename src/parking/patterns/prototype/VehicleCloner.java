package parking.patterns.prototype;

import parking.core.Vehicle;

/**
 * The {@code VehicleCloner} class provides functionality to clone vehicles.
 * This is part of the Prototype design pattern.
 *
 * @author Smart Parking System Team
 */
public class VehicleCloner {
    /**
     * Creates a deep copy of the given vehicle.
     *
     * @param vehicle The vehicle to clone.
     * @return A clone of the vehicle.
     */
    public static Vehicle cloneVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return vehicle.clone();
    }
}