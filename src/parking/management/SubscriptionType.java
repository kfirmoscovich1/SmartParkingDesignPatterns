package parking.management;

/**
 * The SubscriptionType enum represents different types of parking subscriptions.
 * Each type offers different benefits and pricing.
 * 
 * @author Smart Parking System Team
 */
public enum SubscriptionType {
    STANDARD("Standard", 0.8),  // 20% discount
    PREMIUM("Premium", 0.7),    // 30% discount
    VIP("VIP", 0.6);           // 40% discount

    private final String displayName;
    private final double discountMultiplier;

    /**
     * Constructs a subscription type with the specified parameters.
     *
     * @param displayName The display name.
     * @param discountMultiplier The discount multiplier.
     */
    SubscriptionType(String displayName, double discountMultiplier) {
        this.displayName = displayName;
        this.discountMultiplier = discountMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getDiscountMultiplier() {
        return discountMultiplier;
    }

    /**
     * Gets the discount percentage as a human-readable string.
     *
     * @return The discount percentage.
     */
    public String getDiscountPercentage() {
        int discountPercent = (int) ((1.0 - discountMultiplier) * 100);
        return discountPercent + "%";
    }
}