package parking.patterns.builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

/**
 * The {@code ParkingReport} class represents a comprehensive parking report.
 * It is created using the {@code ParkingReportBuilder} as part of the Builder pattern.
 * 
 * @author Smart Parking System Team
 */
public class ParkingReport {

    /** The title of the report. */
    private final String title;

    /** The time when the report was generated. */
    private final LocalDateTime generatedTime;

    /** The time period that the report covers. */
    private final String timePeriod;

    /** The total number of entries during the covered period. */
    private final int totalEntries;

    /** The total revenue during the covered period. */
    private final double totalRevenue;

    /** The current occupancy of the parking lot (percentage). */
    private final double currentOccupancy;

    /** The average parking duration in hours. */
    private final double averageDuration;

    /** Statistics for each vehicle type. */
    private final List<VehicleStatistic> vehicleStatistics;

    /** Additional information or notes. */
    private final String additionalInfo;

    /**
     * Constructs a new {@code ParkingReport} with the specified parameters.
     * This constructor is intended to be used by the {@code ParkingReportBuilder}.
     */
    ParkingReport(String title, LocalDateTime generatedTime, String timePeriod,
                  int totalEntries, double totalRevenue, double currentOccupancy,
                  double averageDuration, List<VehicleStatistic> vehicleStatistics,
                  String additionalInfo) {
        this.title = title;
        this.generatedTime = generatedTime;
        this.timePeriod = timePeriod;
        this.totalEntries = totalEntries;
        this.totalRevenue = totalRevenue;
        this.currentOccupancy = currentOccupancy;
        this.averageDuration = averageDuration;
        this.vehicleStatistics = new ArrayList<>(vehicleStatistics);
        this.additionalInfo = additionalInfo;
    }

    /**
     * Generates a formatted string representation of this report.
     *
     * @return A formatted report string.
     */
    public String generateReport() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder report = new StringBuilder();

        report.append("===================================================\n");
        report.append(title).append("\n");
        report.append("===================================================\n");
        report.append("Generated: ").append(generatedTime.format(formatter)).append("\n");
        report.append("Period: ").append(timePeriod).append("\n\n");

        report.append("SUMMARY\n");
        report.append("---------------------------------------------------\n");
        report.append(String.format("Total Entries: %d\n", totalEntries));
        report.append(String.format("Total Revenue: ₪%.2f\n", totalRevenue));
        report.append(String.format("Current Occupancy: %.1f%%\n", currentOccupancy));
        report.append(String.format("Average Duration: %.2f hours\n\n", averageDuration));

        report.append("VEHICLE STATISTICS\n");
        report.append("---------------------------------------------------\n");
        for (VehicleStatistic stat : vehicleStatistics) {
            report.append(stat.toString()).append("\n");
        }

        if (additionalInfo != null && !additionalInfo.isEmpty()) {
            report.append("\nADDITIONAL INFORMATION\n");
            report.append("---------------------------------------------------\n");
            report.append(additionalInfo).append("\n");
        }

        report.append("===================================================\n");

        return report.toString();
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public LocalDateTime getGeneratedTime() {
        return generatedTime;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getCurrentOccupancy() {
        return currentOccupancy;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public List<VehicleStatistic> getVehicleStatistics() {
        return new ArrayList<>(vehicleStatistics);
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
