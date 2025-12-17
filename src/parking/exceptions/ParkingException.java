package parking.exceptions;

/**
 * The {@code ParkingException} class is the base exception for all parking system errors.
 *
 * @author Smart Parking System Team
 */
public class ParkingException extends RuntimeException {
    
    /**
     * Constructs a new ParkingException with the specified message.
     *
     * @param message The error message.
     */
    public ParkingException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new ParkingException with the specified message and cause.
     *
     * @param message The error message.
     * @param cause The cause of this exception.
     */
    public ParkingException(String message, Throwable cause) {
        super(message, cause);
    }
}
