package parking.patterns.factory;

/**
 * The {@code VehicleFactoryProvider} class provides appropriate vehicle factories
 * based on the requested vehicle type.
 * This is part of the Factory Method design pattern.
 *
 * @author Smart Parking System Team
 */
public class VehicleFactoryProvider {
    /**
     * Returns a vehicle factory for the specified vehicle type.
     *
     * @param vehicleType The type of vehicle for which a factory is required.
     * @return A vehicle factory for the specified type.
     * @throws IllegalArgumentException if the vehicle type is not supported.
     */
    public static VehicleFactory getFactory(VehicleType vehicleType) {
        switch (vehicleType) {
            case CAR:
                return new CarFactory();
            case MOTORCYCLE:
                return new MotorcycleFactory();
            default:
                throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
        }
    }
}