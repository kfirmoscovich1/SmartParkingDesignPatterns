package parking.exceptions;

/**
 * The {@code DuplicateVehicleException} is thrown when trying to park a vehicle
 * that is already in the parking lot.
 *
 * @author Smart Parking System Team
 */
public class DuplicateVehicleException extends ParkingException {
    
    /** The license plate of the duplicate vehicle. */
    private final String licensePlate;
    
    /**
     * Constructs a new DuplicateVehicleException.
     *
     * @param licensePlate The license plate of the duplicate vehicle.
     */
    public DuplicateVehicleException(String licensePlate) {
        super("Vehicle is already parked: " + licensePlate);
        this.licensePlate = licensePlate;
    }
    
    /**
     * Gets the license plate of the duplicate vehicle.
     *
     * @return The license plate.
     */
    public String getLicensePlate() {
        return licensePlate;
    }
}
