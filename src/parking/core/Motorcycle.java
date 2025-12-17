package parking.core;

/**
 * The Motorcycle class represents a motorcycle vehicle in a parking lot.
 * It extends the abstract Vehicle class and implements specific
 * behaviors for motorcycles, such as hourly rate calculation.
 * 
 * @author Smart Parking System Team
 */
public class Motorcycle extends Vehicle {
    private static final double STANDARD_HOURLY_RATE = 12.0;
    private static final double DISABLED_HOURLY_RATE = 8.0;

    /**
     * Constructs a new Motorcycle object with the specified parameters.
     *
     * @param licensePlate The license plate of the motorcycle.
     * @param ownerName The name of the motorcycle owner.
     * @param isDisabled Whether this is a disabled person's motorcycle.
     * @param color The color of the motorcycle.
     */
    public Motorcycle(String licensePlate, String ownerName, boolean isDisabled, String color) {
        super(licensePlate, ownerName, isDisabled, color);
    }

    /**
     * Returns the hourly rate for this motorcycle.
     * Regular motorcycles pay 12 per hour, while motorcycles registered to disabled persons pay 8 per hour.
     *
     * @return The hourly rate for parking this motorcycle.
     */
    @Override
    public double getHourlyRate() {
        return isDisabled() ? DISABLED_HOURLY_RATE : STANDARD_HOURLY_RATE;
    }

    /**
     * Creates and returns a copy of this motorcycle object.
     *
     * @return A clone of this motorcycle.
     */
    @Override
    public Motorcycle clone() {
        return (Motorcycle) super.clone();
    }
}