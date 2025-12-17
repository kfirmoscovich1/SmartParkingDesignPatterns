package parking.patterns.facade;

import parking.core.Vehicle;
import parking.core.ParkingSession;
import parking.management.ParkingLot;
import parking.management.PricingCalculator;
import parking.management.Subscription;
import parking.management.SubscriptionType;
import parking.patterns.builder.ParkingReport;
import parking.patterns.builder.ParkingReportBuilder;
import parking.patterns.factory.VehicleFactory;
import parking.patterns.factory.VehicleFactoryProvider;
import parking.patterns.factory.VehicleType;
import parking.reports.ParkingStatistics;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code ParkingSystemFacade} class provides a simplified interface to the complex
 * parking system. It hides the implementation details and dependencies.
 * This is part of the Facade design pattern.
 */
public class ParkingSystemFacade {

    /** The parking lot managed by this facade. */
    private final ParkingLot parkingLot;

    /** The pricing calculator used to calculate parking fees. */
    private final PricingCalculator pricingCalculator;

    /** The parking statistics manager. */
    private final ParkingStatistics statistics;

    /**
     * Constructs a new {@code ParkingSystemFacade} with the specified parameters.
     */
    public ParkingSystemFacade() {
        this.parkingLot = ParkingLot.getInstance();
        this.pricingCalculator = new PricingCalculator();
        this.statistics = new ParkingStatistics();

        // Initialize the parking lot with observers
        this.parkingLot.initialize(statistics);
    }

    public Vehicle createVehicle(VehicleType type, String licensePlate, String ownerName, boolean isDisabled, String color) {
        VehicleFactory factory = VehicleFactoryProvider.getFactory(type);
        return factory.createVehicle(licensePlate, ownerName, isDisabled, color);
    }

    public Vehicle createVehicle(VehicleType type, String licensePlate, String ownerName, boolean isDisabled) {
        return createVehicle(type, licensePlate, ownerName, isDisabled, "Unknown");
    }

    public boolean parkVehicle(Vehicle vehicle) {
        return parkingLot.parkVehicle(vehicle, false);
    }

    public boolean parkSubscriberVehicle(Vehicle vehicle, String subscriptionId) {
        if (Subscription.isValidSubscription(subscriptionId)) {
            return parkingLot.parkVehicle(vehicle, true);
        }
        return false;
    }

    public double removeVehicle(String licensePlate) {
        ParkingSession session = parkingLot.removeVehicle(licensePlate);
        if (session == null) {
            return -1;
        }
        if (!session.isSubscription()) {
            double fee = pricingCalculator.calculateFee(session);
            session.recordPayment(fee);
            return fee;
        }
        return 0; // Subscribers don't pay per session
    }

    public String createSubscription(String licensePlate, String ownerName, int months) {
        return Subscription.createSubscription(licensePlate, ownerName, months);
    }

    public String createSubscription(String licensePlate, String ownerName, int months, SubscriptionType subscriptionType) {
        return Subscription.createSubscription(licensePlate, ownerName, months, subscriptionType);
    }

    public double getOccupancyPercentage() {
        return parkingLot.getOccupancyPercentage();
    }

    public int getAvailableSpots() {
        return parkingLot.getAvailableSpots();
    }

    public ParkingReport generateDailyReport() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ParkingReportBuilder builder = new ParkingReportBuilder()
                .setTitle("Daily Parking Report")
                .setTimePeriod(today.format(formatter))
                .setTotalEntries(statistics.getDailyEntries())
                .setTotalRevenue(statistics.getDailyRevenue())
                .setCurrentOccupancy(getOccupancyPercentage())
                .setAverageDuration(statistics.getAverageDuration());

        builder.addVehicleStatistic(
                "Car",
                statistics.getDailyCarCount(),
                statistics.getDailyCarRevenue(),
                statistics.getAverageCarDuration(),
                statistics.getDisabledCarPercentage()
        );

        builder.addVehicleStatistic(
                "Motorcycle",
                statistics.getDailyMotorcycleCount(),
                statistics.getDailyMotorcycleRevenue(),
                statistics.getAverageMotorcycleDuration(),
                statistics.getDisabledMotorcyclePercentage()
        );

        return builder.build();
    }

    public ParkingReport generateMonthlyReport() {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString() + " " + today.getYear();

        ParkingReportBuilder builder = new ParkingReportBuilder()
                .setTitle("Monthly Parking Report")
                .setTimePeriod(month)
                .setTotalEntries(statistics.getMonthlyEntries())
                .setTotalRevenue(statistics.getMonthlyRevenue())
                .setCurrentOccupancy(getOccupancyPercentage())
                .setAverageDuration(statistics.getAverageDuration());

        builder.addVehicleStatistic(
                "Car",
                statistics.getMonthlyCarCount(),
                statistics.getMonthlyCarRevenue(),
                statistics.getAverageCarDuration(),
                statistics.getDisabledCarPercentage()
        );

        builder.addVehicleStatistic(
                "Motorcycle",
                statistics.getMonthlyMotorcycleCount(),
                statistics.getMonthlyMotorcycleRevenue(),
                statistics.getAverageMotorcycleDuration(),
                statistics.getDisabledMotorcyclePercentage()
        );

        return builder.build();
    }
}
