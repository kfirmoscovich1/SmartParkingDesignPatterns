package parking.patterns.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ParkingReportBuilder} class is responsible for building complex
 * {@code ParkingReport} objects step by step.
 * This is part of the Builder design pattern.
 *
 * @author Smart Parking System Team
 */
public class ParkingReportBuilder {
    /** The title of the report. */
    private String title = "Parking Lot Report";

    /** The time when the report was generated. */
    private LocalDateTime generatedTime = LocalDateTime.now();

    /** The time period that the report covers. */
    private String timePeriod = "Today";

    /** The total number of entries during the covered period. */
    private int totalEntries = 0;

    /** The total revenue during the covered period. */
    private double totalRevenue = 0.0;

    /** The current occupancy of the parking lot (percentage). */
    private double currentOccupancy = 0.0;

    /** The average parking duration in hours. */
    private double averageDuration = 0.0;

    /** Statistics for each vehicle type. */
    private final List<VehicleStatistic> vehicleStatistics = new ArrayList<>();

    /** Additional information or notes. */
    private String additionalInfo = "";

    /**
     * Sets the title of the report.
     *
     * @param title The title to set.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the generated time of the report.
     *
     * @param generatedTime The generated time to set.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder setGeneratedTime(LocalDateTime generatedTime) {
        this.generatedTime = generatedTime;
        return this;
    }

    /**
     * Sets the time period of the report.
     *
     * @param timePeriod The time period to set.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
        return this;
    }

    /**
     * Sets the total entries count.
     *
     * @param totalEntries The total entries to set.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder setTotalEntries(int totalEntries) {
        this.totalEntries = totalEntries;
        return this;
    }

    /**
     * Sets the total revenue.
     *
     * @param totalRevenue The total revenue to set.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
        return this;
    }

    /**
     * Sets the current occupancy percentage.
     *
     * @param currentOccupancy The current occupancy to set.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder setCurrentOccupancy(double currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
        return this;
    }

    /**
     * Sets the average parking duration.
     *
     * @param averageDuration The average duration to set.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder setAverageDuration(double averageDuration) {
        this.averageDuration = averageDuration;
        return this;
    }

    /**
     * Adds vehicle statistics to the report.
     *
     * @param vehicleType The type of vehicle.
     * @param count The count of this vehicle type.
     * @param revenue The total revenue from this vehicle type.
     * @param averageDuration The average parking duration for this vehicle type in hours.
     * @param disabledPercentage The percentage of disabled vehicles of this type.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder addVehicleStatistic(String vehicleType, int count, double revenue,
                                                    double averageDuration, double disabledPercentage) {
        VehicleStatistic stat = new VehicleStatistic(vehicleType, count, revenue, averageDuration, disabledPercentage);
        this.vehicleStatistics.add(stat);
        return this;
    }

    /**
     * Sets additional information or notes for the report.
     *
     * @param additionalInfo The additional info to set.
     * @return This builder for method chaining.
     */
    public ParkingReportBuilder setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    /**
     * Builds and returns a new {@code ParkingReport} object with the current builder state.
     *
     * @return A new parking report instance.
     */
    public ParkingReport build() {
        return new ParkingReport(
                title,
                generatedTime,
                timePeriod,
                totalEntries,
                totalRevenue,
                currentOccupancy,
                averageDuration,
                vehicleStatistics,
                additionalInfo
        );
    }
}