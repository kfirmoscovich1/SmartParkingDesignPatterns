package parking.management;

import parking.core.ParkingSession;

/**
 * The {@code PricingCalculator} class calculates parking fees based on
 * duration, vehicle type, and disability status.
 *
 * @author Smart Parking System Team
 */
public class PricingCalculator {
    /** The number of free hours at the beginning of parking. */
    private static final double FREE_HOURS = 2.0;

    /**
     * Calculates the fee for a parking session.
     *
     * @param session The parking session to calculate the fee for.
     * @return The calculated fee.
     */
    public double calculateFee(ParkingSession session) {
        if (session == null || session.isSubscription()) {            return 0.0; // No fee for subscribers
        }

        double durationHours = session.getDurationHours();

        // Apply free hours policy
        durationHours -= FREE_HOURS;
        if (durationHours <= 0) {
            return 0.0; // Within free period
        }

        // Calculate the base fee
        double hourlyRate = session.getVehicle().getHourlyRate();

        // Round up to nearest hour
        int chargableHours = (int) Math.ceil(durationHours);

        return hourlyRate * chargableHours;
    }

    /**
     * Calculates the cost for a parking session (alias for calculateFee).
     *
     * @param session The parking session to calculate the cost for.
     * @return The calculated cost.
     */
    public double calculateCost(ParkingSession session) {
        return calculateFee(session);
    }

    /**
     * Calculates the annual subscription fee for a vehicle.
     *
     * @param hourlyRate The hourly rate for the vehicle.
     * @return The annual subscription fee.
     */
    public double calculateAnnualSubscriptionFee(double hourlyRate) {
        // Assuming a typical car is parked for 4 hours a day, 20 days a month
        // Annual subscription gives a 40% discount
        double typicalMonthlyUsage = hourlyRate * 4 * 20; // hourly rate * 4 hours * 20 days
        double annualFee = typicalMonthlyUsage * 12 * 0.6; // 40% discount for annual subscription

        return annualFee;
    }    /**
     * Calculates the fee for a parking session with subscription discount.
     *
     * @param session The parking session to calculate the fee for.
     * @param subscription The subscription for discount application.
     * @return The calculated fee with discount applied.
     */
    public double calculateFeeWithSubscription(ParkingSession session, Subscription subscription) {
        if (session == null) {
            return 0.0;
        }

        if (subscription != null && subscription.isActive()) {
            return 0.0; // Subscribers with valid subscriptions park for free
        }

        // Calculate base fee and apply discount if subscription exists but expired
        double baseFee = calculateFee(session);
        if (subscription != null) {
            return baseFee * subscription.getDiscountMultiplier();
        }

        return baseFee;
    }
}