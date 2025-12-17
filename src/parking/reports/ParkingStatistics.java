package parking.reports;

import parking.core.Car;
import parking.core.Motorcycle;
import parking.core.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code ParkingStatistics} class collects and provides statistics about parking activities.
 *
 * @author Smart Parking System Team
 */
public class ParkingStatistics {
    /** Maps date to revenue for historical tracking. */
    private final Map<LocalDate, Double> dailyRevenue;

    /** Maps date to entry count for historical tracking. */
    private final Map<LocalDate, Integer> dailyEntries;

    /** Maps date to car count for historical tracking. */
    private final Map<LocalDate, Integer> dailyCarCount;

    /** Maps date to motorcycle count for historical tracking. */
    private final Map<LocalDate, Integer> dailyMotorcycleCount;

    /** Maps date to car revenue for historical tracking. */
    private final Map<LocalDate, Double> dailyCarRevenue;

    /** Maps date to motorcycle revenue for historical tracking. */
    private final Map<LocalDate, Double> dailyMotorcycleRevenue;

    /** Maps license plate to entry time for duration calculation. */
    private final Map<String, LocalDateTime> vehicleEntryTimes;

    /** Maps license plate to vehicle type for statistics. */
    private final Map<String, String> vehicleTypes;    /** Maps license plate to disabled status for statistics. */
    private final Map<String, Boolean> vehicleDisabledStatus;    /** Maps license plate to vehicle color for statistics. */
    private final Map<String, String> vehicleColors;

    /** Counts vehicles by color for statistics. */
    private final Map<String, Integer> colorCounts;

    /** Stores all parking durations for average calculation. */
    private final List<Double> parkingDurations;

    /** Stores car parking durations for average calculation. */
    private final List<Double> carParkingDurations;

    /** Stores motorcycle parking durations for average calculation. */
    private final List<Double> motorcycleParkingDurations;

    /** Stores disabled vehicle count for statistics. */
    private int disabledVehicleCount;

    /** Stores disabled car count for statistics. */
    private int disabledCarCount;

    /** Stores disabled motorcycle count for statistics. */
    private int disabledMotorcycleCount;

    /** Stores total car count for statistics. */
    private int totalCarCount;

    /** Stores total motorcycle count for statistics. */
    private int totalMotorcycleCount;

    /** Stores current occupancy data. */
    private int totalSpots;
    private int occupiedSpots;
    private int availableSpots;

    /**
     * Constructs a new {@code ParkingStatistics}.
     */
    public ParkingStatistics() {
        this.dailyRevenue = new HashMap<>();
        this.dailyEntries = new HashMap<>();
        this.dailyCarCount = new HashMap<>();
        this.dailyMotorcycleCount = new HashMap<>();
        this.dailyCarRevenue = new HashMap<>();
        this.dailyMotorcycleRevenue = new HashMap<>();
        this.vehicleEntryTimes = new HashMap<>();
        this.vehicleTypes = new HashMap<>();
        this.vehicleDisabledStatus = new HashMap<>();
        this.vehicleColors = new HashMap<>();
        this.colorCounts = new HashMap<>();
        this.parkingDurations = new ArrayList<>();
        this.carParkingDurations = new ArrayList<>();
        this.motorcycleParkingDurations = new ArrayList<>();
        this.disabledVehicleCount = 0;
        this.disabledCarCount = 0;
        this.disabledMotorcycleCount = 0;
        this.totalCarCount = 0;
        this.totalMotorcycleCount = 0;
        this.totalSpots = 0;
        this.occupiedSpots = 0;
        this.availableSpots = 0;
    }

    /**
     * Records a vehicle entry.
     *
     * @param licensePlate The license plate of the vehicle.
     */
    public void recordEntry(String licensePlate) {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        // Record entry time
        vehicleEntryTimes.put(licensePlate, now);

        // Increment daily entries
        dailyEntries.put(today, dailyEntries.getOrDefault(today, 0) + 1);
    }

    /**
     * Records a vehicle exit and collects statistics.
     *
     * @param licensePlate The license plate of the vehicle.
     * @param durationHours The duration of the parking in hours.
     * @param payment The amount paid for the parking.
     */
    public void recordExit(String licensePlate, double durationHours, double payment) {
        LocalDate today = LocalDate.now();

        // Record revenue
        dailyRevenue.put(today, dailyRevenue.getOrDefault(today, 0.0) + payment);

        // Record duration
        parkingDurations.add(durationHours);

        // Record vehicle-specific statistics
        String vehicleType = vehicleTypes.get(licensePlate);
        boolean isDisabled = vehicleDisabledStatus.getOrDefault(licensePlate, false);

        if (vehicleType != null) {
            if (vehicleType.equals("Car")) {
                carParkingDurations.add(durationHours);
                dailyCarRevenue.put(today, dailyCarRevenue.getOrDefault(today, 0.0) + payment);
            } else if (vehicleType.equals("Motorcycle")) {
                motorcycleParkingDurations.add(durationHours);
                dailyMotorcycleRevenue.put(today, dailyMotorcycleRevenue.getOrDefault(today, 0.0) + payment);
            }
        }

        // Clean up
        vehicleEntryTimes.remove(licensePlate);
    }

    /**
     * Records vehicle type and disability status for statistics.
     *
     * @param vehicle The vehicle to record.
     */
    public void recordVehicleType(Vehicle vehicle) {
        String licensePlate = vehicle.getLicensePlate();
        boolean isDisabled = vehicle.isDisabled();

        if (vehicle instanceof Car) {
            vehicleTypes.put(licensePlate, "Car");
            totalCarCount++;
            if (isDisabled) {
                disabledCarCount++;
                disabledVehicleCount++;
            }

            // Update daily car count
            LocalDate today = LocalDate.now();
            dailyCarCount.put(today, dailyCarCount.getOrDefault(today, 0) + 1);
        } else if (vehicle instanceof Motorcycle) {
            vehicleTypes.put(licensePlate, "Motorcycle");
            totalMotorcycleCount++;
            if (isDisabled) {
                disabledMotorcycleCount++;
                disabledVehicleCount++;
            }

            // Update daily motorcycle count
            LocalDate today = LocalDate.now();
            dailyMotorcycleCount.put(today, dailyMotorcycleCount.getOrDefault(today, 0) + 1);
        }        vehicleDisabledStatus.put(licensePlate, isDisabled);

        // Record vehicle color
        String color = vehicle.getColor();
        if (color != null && !color.trim().isEmpty()) {
            vehicleColors.put(licensePlate, color);
            colorCounts.put(color, colorCounts.getOrDefault(color, 0) + 1);
        }
    }

    /**
     * Updates occupancy statistics.
     *
     * @param totalSpots The total number of spots in the parking lot.
     * @param occupiedSpots The number of currently occupied spots.
     * @param availableSpots The number of currently available spots.
     */
    public void updateOccupancy(int totalSpots, int occupiedSpots, int availableSpots) {
        this.totalSpots = totalSpots;
        this.occupiedSpots = occupiedSpots;
        this.availableSpots = availableSpots;
    }

    /**
     * Gets the daily revenue for today.
     *
     * @return The daily revenue.
     */
    public double getDailyRevenue() {
        return dailyRevenue.getOrDefault(LocalDate.now(), 0.0);
    }

    /**
     * Gets the daily entries for today.
     *
     * @return The daily entries.
     */
    public int getDailyEntries() {
        return dailyEntries.getOrDefault(LocalDate.now(), 0);
    }

    /**
     * Gets the monthly revenue (sum of daily revenues for the current month).
     *
     * @return The monthly revenue.
     */
    public double getMonthlyRevenue() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        double total = 0.0;
        for (Map.Entry<LocalDate, Double> entry : dailyRevenue.entrySet()) {
            LocalDate date = entry.getKey();
            if (date.getMonthValue() == currentMonth && date.getYear() == currentYear) {
                total += entry.getValue();
            }
        }

        return total;
    }

    /**
     * Gets the monthly entries (sum of daily entries for the current month).
     *
     * @return The monthly entries.
     */
    public int getMonthlyEntries() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        int total = 0;
        for (Map.Entry<LocalDate, Integer> entry : dailyEntries.entrySet()) {
            LocalDate date = entry.getKey();
            if (date.getMonthValue() == currentMonth && date.getYear() == currentYear) {
                total += entry.getValue();
            }
        }

        return total;
    }

    /**
     * Gets the average parking duration in hours.
     *
     * @return The average duration, or 0 if no data is available.
     */
    public double getAverageDuration() {
        if (parkingDurations.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (double duration : parkingDurations) {
            sum += duration;
        }

        return sum / parkingDurations.size();
    }

    /**
     * Gets the average car parking duration in hours.
     *
     * @return The average duration, or 0 if no data is available.
     */
    public double getAverageCarDuration() {
        if (carParkingDurations.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (double duration : carParkingDurations) {
            sum += duration;
        }

        return sum / carParkingDurations.size();
    }

    /**
     * Gets the average motorcycle parking duration in hours.
     *
     * @return The average duration, or 0 if no data is available.
     */
    public double getAverageMotorcycleDuration() {
        if (motorcycleParkingDurations.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (double duration : motorcycleParkingDurations) {
            sum += duration;
        }

        return sum / motorcycleParkingDurations.size();
    }

    /**
     * Gets the percentage of disabled vehicles among all vehicles.
     *
     * @return The disabled percentage, or 0 if no data is available.
     */
    public double getDisabledPercentage() {
        int totalVehicles = totalCarCount + totalMotorcycleCount;
        if (totalVehicles == 0) {
            return 0.0;
        }

        return (double) disabledVehicleCount / totalVehicles * 100;
    }

    /**
     * Gets the percentage of disabled cars among all cars.
     *
     * @return The disabled car percentage, or 0 if no data is available.
     */
    public double getDisabledCarPercentage() {
        if (totalCarCount == 0) {
            return 0.0;
        }

        return (double) disabledCarCount / totalCarCount * 100;
    }

    /**
     * Gets the percentage of disabled motorcycles among all motorcycles.
     *
     * @return The disabled motorcycle percentage, or 0 if no data is available.
     */
    public double getDisabledMotorcyclePercentage() {
        if (totalMotorcycleCount == 0) {
            return 0.0;
        }

        return (double) disabledMotorcycleCount / totalMotorcycleCount * 100;
    }

    /**
     * Gets the daily car count for today.
     *
     * @return The daily car count.
     */
    public int getDailyCarCount() {
        return dailyCarCount.getOrDefault(LocalDate.now(), 0);
    }

    /**
     * Gets the daily motorcycle count for today.
     *
     * @return The daily motorcycle count.
     */
    public int getDailyMotorcycleCount() {
        return dailyMotorcycleCount.getOrDefault(LocalDate.now(), 0);
    }

    /**
     * Gets the daily car revenue for today.
     *
     * @return The daily car revenue.
     */
    public double getDailyCarRevenue() {
        return dailyCarRevenue.getOrDefault(LocalDate.now(), 0.0);
    }

    /**
     * Gets the daily motorcycle revenue for today.
     *
     * @return The daily motorcycle revenue.
     */
    public double getDailyMotorcycleRevenue() {
        return dailyMotorcycleRevenue.getOrDefault(LocalDate.now(), 0.0);
    }

    /**
     * Gets the monthly car count for the current month.
     *
     * @return The monthly car count.
     */
    public int getMonthlyCarCount() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        int total = 0;
        for (Map.Entry<LocalDate, Integer> entry : dailyCarCount.entrySet()) {
            LocalDate date = entry.getKey();
            if (date.getMonthValue() == currentMonth && date.getYear() == currentYear) {
                total += entry.getValue();
            }
        }

        return total;
    }

    /**
     * Gets the monthly motorcycle count for the current month.
     *
     * @return The monthly motorcycle count.
     */
    public int getMonthlyMotorcycleCount() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        int total = 0;
        for (Map.Entry<LocalDate, Integer> entry : dailyMotorcycleCount.entrySet()) {
            LocalDate date = entry.getKey();
            if (date.getMonthValue() == currentMonth && date.getYear() == currentYear) {
                total += entry.getValue();
            }
        }

        return total;
    }

    /**
     * Gets the monthly car revenue for the current month.
     *
     * @return The monthly car revenue.
     */
    public double getMonthlyCarRevenue() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        double total = 0.0;
        for (Map.Entry<LocalDate, Double> entry : dailyCarRevenue.entrySet()) {
            LocalDate date = entry.getKey();
            if (date.getMonthValue() == currentMonth && date.getYear() == currentYear) {
                total += entry.getValue();
            }
        }

        return total;
    }

    /**
     * Gets the monthly motorcycle revenue for the current month.
     *
     * @return The monthly motorcycle revenue.
     */
    public double getMonthlyMotorcycleRevenue() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        double total = 0.0;
        for (Map.Entry<LocalDate, Double> entry : dailyMotorcycleRevenue.entrySet()) {
            LocalDate date = entry.getKey();
            if (date.getMonthValue() == currentMonth && date.getYear() == currentYear) {
                total += entry.getValue();
            }
        }

        return total;
    }    /**
     * Gets the most popular vehicle color.
     *
     * @return The most popular color, or "Unknown" if no data.
     */
    public String getMostPopularColor() {
        if (colorCounts.isEmpty()) {
            return "Unknown";
        }

        return colorCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }    /**
     * Gets the count for a specific vehicle color.
     *
     * @param color The color to get count for.
     * @return The count for the color.
     */
    public int getColorCount(String color) {
        return colorCounts.getOrDefault(color, 0);
    }

    /**
     * Gets all unique vehicle colors that have been recorded.
     *
     * @return Set of vehicle colors.
     */
    public Set<String> getAllColors() {
        return new HashSet<>(colorCounts.keySet());
    }
}