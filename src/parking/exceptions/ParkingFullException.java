package parking.exceptions;

/**
 * The {@code ParkingFullException} is thrown when the parking lot is full.
 *
 * @author Smart Parking System Team
 */
public class ParkingFullException extends ParkingException {
    
    /**
     * Constructs a new ParkingFullException.
     */
    public ParkingFullException() {
        super("Parking lot is full. No available spots.");
    }
    
    /**
     * Constructs a new ParkingFullException with a specific message.
     *
     * @param message The error message.
     */
    public ParkingFullException(String message) {
        super(message);
    }
}
