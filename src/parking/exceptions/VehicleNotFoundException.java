package parking.exceptions;

/**
 * The {@code VehicleNotFoundException} is thrown when a vehicle is not found in the parking lot.
 *
 * @author Smart Parking System Team
 */
public class VehicleNotFoundException extends ParkingException {
    
    /** The license plate of the vehicle that was not found. */
    private final String licensePlate;
    
    /**
     * Constructs a new VehicleNotFoundException.
     *
     * @param licensePlate The license plate of the vehicle that was not found.
     */
    public VehicleNotFoundException(String licensePlate) {
        super("Vehicle not found: " + licensePlate);
        this.licensePlate = licensePlate;
    }
    
    /**
     * Gets the license plate of the vehicle that was not found.
     *
     * @return The license plate.
     */
    public String getLicensePlate() {
        return licensePlate;
    }
}
