package parking.management;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Subscription class manages parking subscriptions.
 * 
 * @author Smart Parking System Team
 */
public class Subscription {
    /** The unique ID of this subscription. */
    private final String subscriptionId;

    /** The license plate of the subscribed vehicle. */
    private final String licensePlate;

    /** The name of the subscriber. */
    private final String subscriberName;

    /** The start date of this subscription. */
    private final LocalDate startDate;

    /** The end date of this subscription. */
    private final LocalDate endDate;

    /** Whether this subscription is active. */
    private boolean active;    private final SubscriptionType subscriptionType;

    /** The list of all current subscriptions. */
    private static final List<Subscription> subscriptions = new ArrayList<>();

    /**
     * Constructs a new {@code Subscription} with the specified parameters.
     *
     * @param subscriptionId The unique ID of this subscription.
     * @param licensePlate The license plate of the subscribed vehicle.
     * @param subscriberName The name of the subscriber.
     * @param startDate The start date of this subscription.
     * @param endDate The end date of this subscription.
     * @param subscriptionType The type of subscription.
     */
    private Subscription(String subscriptionId, String licensePlate, String subscriberName,
                         LocalDate startDate, LocalDate endDate, SubscriptionType subscriptionType) {
        this.subscriptionId = subscriptionId;
        this.licensePlate = licensePlate;
        this.subscriberName = subscriberName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subscriptionType = subscriptionType;
        this.active = true;
    }

    /**
     * Creates a new subscription for a vehicle.
     *
     * @param licensePlate The license plate of the vehicle.
     * @param subscriberName The name of the subscriber.
     * @param months The number of months for the subscription.
     * @param subscriptionType The type of subscription.
     * @return The subscription ID.
     */
    public static String createSubscription(String licensePlate, String subscriberName, int months, SubscriptionType subscriptionType) {
        // Check if the vehicle already has an active subscription
        for (Subscription sub : subscriptions) {
            if (sub.licensePlate.equals(licensePlate) && sub.isActive()) {
                sub.deactivate(); // Deactivate the old subscription
                break;
            }
        }

        // Generate a unique subscription ID
        String subscriptionId = generateSubscriptionId(licensePlate);

        // Calculate dates
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(months);

        // Create and store the new subscription
        Subscription subscription = new Subscription(
                subscriptionId, licensePlate, subscriberName, startDate, endDate, subscriptionType);
        subscriptions.add(subscription);

        return subscriptionId;
    }

    /**
     * Creates a new standard subscription for a vehicle (for backward compatibility).
     *
     * @param licensePlate The license plate of the vehicle.
     * @param subscriberName The name of the subscriber.
     * @param months The number of months for the subscription.
     * @return The subscription ID.
     */
    public static String createSubscription(String licensePlate, String subscriberName, int months) {
        return createSubscription(licensePlate, subscriberName, months, SubscriptionType.STANDARD);
    }

    /**
     * Checks if a subscription is valid.
     *
     * @param subscriptionId The subscription ID to check.
     * @return true if the subscription is valid, false otherwise.
     */
    public static boolean isValidSubscription(String subscriptionId) {
        for (Subscription sub : subscriptions) {
            if (sub.subscriptionId.equals(subscriptionId) && sub.isActive()) {
                // Check if the subscription has expired
                if (LocalDate.now().isAfter(sub.endDate)) {
                    sub.deactivate();
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Deactivates this subscription.
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Checks if this subscription is active.
     *
     * @return true if the subscription is active, false otherwise.
     */
    public boolean isActive() {
        return active && !LocalDate.now().isAfter(endDate);
    }

    /**
     * Checks if this subscription is valid (alias for isActive).
     *
     * @return true if the subscription is valid, false otherwise.
     */
    public boolean isValid() {
        return isActive();
    }

    /**
     * Generates a subscription ID for a vehicle.
     *
     * @param licensePlate The license plate of the vehicle.
     * @return A unique subscription ID.
     */
    private static String generateSubscriptionId(String licensePlate) {
        // Generate a simple ID based on license plate and current timestamp
        return "SUB-" + licensePlate.replace(" ", "") + "-" + System.currentTimeMillis() % 10000;
    }

    /**
     * Gets the subscription ID.
     *
     * @return The subscription ID.
     */
    public String getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Gets the license plate.
     *
     * @return The license plate.
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Gets the subscriber name.
     *
     * @return The subscriber name.
     */
    public String getSubscriberName() {
        return subscriberName;
    }

    /**
     * Gets the start date.
     *
     * @return The start date.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date.
     *
     * @return The end date.
     */
    public LocalDate getEndDate() {
        return endDate;
    }    /**
     * Gets the subscription type.
     *
     * @return The subscription type.
     */
    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * Gets the discount multiplier for this subscription.
     *
     * @return The discount multiplier.
     */
    public double getDiscountMultiplier() {
        return subscriptionType.getDiscountMultiplier();
    }
}