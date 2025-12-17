package parking.core;

/**
 * The Car class represents a regular car vehicle in a parking lot.
 * It extends the abstract Vehicle class and implements specific
 * behaviors for cars, such as hourly rate calculation.
 * 
 * @author Smart Parking System Team
 */
public class Car extends Vehicle {
    private static final double STANDARD_HOURLY_RATE = 18.0;
    private static final double DISABLED_HOURLY_RATE = 8.0;

    /**
     * Constructs a new Car object with the specified parameters.
     *
     * @param licensePlate The license plate of the car.
     * @param ownerName The name of the car owner.
     * @param isDisabled Whether this is a disabled person's car.
     * @param color The color of the car.
     */
    public Car(String licensePlate, String ownerName, boolean isDisabled, String color) {
        super(licensePlate, ownerName, isDisabled, color);
    }

    /**
     * Returns the hourly rate for this car.
     * Regular cars pay 18 per hour, while cars registered to disabled persons pay 8 per hour.
     *
     * @return The hourly rate for parking this car.
     */
    @Override
    public double getHourlyRate() {
        return isDisabled() ? DISABLED_HOURLY_RATE : STANDARD_HOURLY_RATE;
    }

    /**
     * Creates and returns a copy of this car object.
     * 
     * @return A clone of this car.
     */
    @Override
    public Car clone() {
        return (Car) super.clone();
    }
}