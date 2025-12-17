package parking.patterns.builder;

/**
 * The {@code VehicleStatistic} class holds statistical information about a specific vehicle type.
 * This is used as a component in the {@code ParkingReport}.
 *
 * @author Smart Parking System Team
 */
public class VehicleStatistic {
    
    /** The type of vehicle (e.g., "Car", "Motorcycle"). */
    private final String vehicleType;

    /** The count of this vehicle type. */
    private final int count;

    /** The total revenue from this vehicle type. */
    private final double revenue;

    /** The average parking duration for this vehicle type in hours. */
    private final double averageDuration;

    /** The percentage of disabled vehicles of this type. */
    private final double disabledPercentage;

    /**
     * Constructs a new {@code VehicleStatistic} with the specified parameters.
     *
     * @param vehicleType The type of vehicle.
     * @param count The count of this vehicle type.
     * @param revenue The total revenue from this vehicle type.
     * @param averageDuration The average parking duration for this vehicle type in hours.
     * @param disabledPercentage The percentage of disabled vehicles of this type.
     */
    public VehicleStatistic(String vehicleType, int count, double revenue,
                            double averageDuration, double disabledPercentage) {
        this.vehicleType = vehicleType;
        this.count = count;
        this.revenue = revenue;
        this.averageDuration = averageDuration;
        this.disabledPercentage = disabledPercentage;
    }

    /**
     * Gets the vehicle type.
     *
     * @return The vehicle type.
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * Gets the count of this vehicle type.
     *
     * @return The count.
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets the total revenue from this vehicle type.
     *
     * @return The revenue.
     */
    public double getRevenue() {
        return revenue;
    }

    /**
     * Gets the average parking duration for this vehicle type in hours.
     *
     * @return The average duration.
     */
    public double getAverageDuration() {
        return averageDuration;
    }

    /**
     * Gets the percentage of disabled vehicles of this type.
     *
     * @return The disabled percentage.
     */
    public double getDisabledPercentage() {
        return disabledPercentage;
    }

    /**
     * Returns a string representation of this vehicle statistic.
     *
     * @return A string representation.
     */
    @Override
    public String toString() {
        return String.format("%s: Count=%d, Revenue=₪%.2f, Avg Duration=%.2f hours, Disabled=%.1f%%",
                vehicleType, count, revenue, averageDuration, disabledPercentage);
    }
}