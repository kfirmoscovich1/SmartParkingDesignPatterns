package parking.exceptions;

/**
 * The {@code InvalidSubscriptionException} is thrown when a subscription is invalid or expired.
 *
 * @author Smart Parking System Team
 */
public class InvalidSubscriptionException extends ParkingException {
    
    /** The subscription ID that was invalid. */
    private final String subscriptionId;
    
    /**
     * Constructs a new InvalidSubscriptionException.
     *
     * @param subscriptionId The invalid subscription ID.
     */
    public InvalidSubscriptionException(String subscriptionId) {
        super("Invalid or expired subscription: " + subscriptionId);
        this.subscriptionId = subscriptionId;
    }
    
    /**
     * Gets the subscription ID that was invalid.
     *
     * @return The subscription ID.
     */
    public String getSubscriptionId() {
        return subscriptionId;
    }
}
