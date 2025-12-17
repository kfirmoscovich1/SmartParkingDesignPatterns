package parking;

import parking.core.Vehicle;
import parking.patterns.facade.ParkingSystemFacade;
import parking.patterns.factory.VehicleType;
import parking.patterns.builder.ParkingReport;
import parking.management.SubscriptionType;

/**
 * The {@code Main} class provides a simple demonstration of the parking system.
 *
 * @author Smart Parking System Team
 */
public class Main {
    /**
     * The entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Smart Parking System");
        System.out.println("====================");
        System.out.println();

        // Create the facade
        ParkingSystemFacade facade = new ParkingSystemFacade();

        // Create some vehicles with colors
        System.out.println("Creating Vehicles:");
        System.out.println("==================");
        Vehicle car1 = facade.createVehicle(VehicleType.CAR, "123456", "Israel Israeli", false, "Blue");
        Vehicle car2 = facade.createVehicle(VehicleType.CAR, "654321", "Moshe Cohen", true, "Red");
        Vehicle motorcycle1 = facade.createVehicle(VehicleType.MOTORCYCLE, "789012", "David Levy", false, "Black");

        // Create subscriptions
        System.out.println("Creating Subscriptions:");
        String standardSubscriptionId = facade.createSubscription("111222", "Chaim Yaakov", 12);
        Vehicle subscriberCar = facade.createVehicle(VehicleType.CAR, "111222", "Chaim Yaakov", false, "White");
        String premiumSubscriptionId = facade.createSubscription("333444", "Sarah Levi", 6, SubscriptionType.PREMIUM);
        Vehicle premiumSubscriberCar = facade.createVehicle(VehicleType.CAR, "333444", "Sarah Levi", false, "Silver");
        System.out.println();

        // Park vehicles
        System.out.println("Parking Vehicles:");
        System.out.println("=================");
        boolean parked = facade.parkVehicle(car1);
        System.out.println("Parking " + car1.getColor() + " car " + car1.getLicensePlate() + " (Owner: " + car1.getOwnerName() + ") - " + (parked ? "Success" : "Failed"));
        System.out.println();

        parked = facade.parkVehicle(car2);
        System.out.println("Parking " + car2.getColor() + " disabled car " + car2.getLicensePlate() + " (Owner: " + car2.getOwnerName() + ") - " + (parked ? "Success" : "Failed"));
        System.out.println();

        parked = facade.parkVehicle(motorcycle1);
        System.out.println("Parking " + motorcycle1.getColor() + " motorcycle " + motorcycle1.getLicensePlate() + " (Owner: " + motorcycle1.getOwnerName() + ") - " + (parked ? "Success" : "Failed"));
        System.out.println();

        parked = facade.parkSubscriberVehicle(subscriberCar, standardSubscriptionId);
        System.out.println("Parking " + subscriberCar.getColor() + " subscriber car " + subscriberCar.getLicensePlate() + " (Standard Member) - " + (parked ? "Success" : "Failed"));
        System.out.println();

        parked = facade.parkSubscriberVehicle(premiumSubscriberCar, premiumSubscriptionId);
        System.out.println("Parking " + premiumSubscriberCar.getColor() + " premium subscriber car " + premiumSubscriberCar.getLicensePlate() + " (Premium Member) - " + (parked ? "Success" : "Failed"));
        System.out.println();

        // Display status
        System.out.println("Parking Lot Status:");
        System.out.println("===================");
        System.out.println("Occupancy: " + String.format("%.1f%%", facade.getOccupancyPercentage()));
        System.out.println("Available spots: " + facade.getAvailableSpots());
        System.out.println();

        // Simulate wait
        try {
            System.out.println("Waiting for a few seconds...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Remove vehicles
        System.out.println("Removing Vehicles:");
        System.out.println("==================");
        double payment = facade.removeVehicle(car1.getLicensePlate());
        if (payment >= 0) {
            System.out.println("Car " + car1.getLicensePlate() + " exited. Payment: $" + String.format("%.2f", payment));
        } else {
            System.out.println("Car " + car1.getLicensePlate() + " not found in parking lot.");
        }
        System.out.println();

        payment = facade.removeVehicle(motorcycle1.getLicensePlate());
        if (payment >= 0) {
            System.out.println("Motorcycle " + motorcycle1.getLicensePlate() + " exited. Payment: $" + String.format("%.2f", payment));
        } else {
            System.out.println("Motorcycle " + motorcycle1.getLicensePlate() + " not found in parking lot.");
        }

        // Generate report
        System.out.println();
        System.out.println("Daily Report:");
        System.out.println("=============");
        System.out.println();
        ParkingReport dailyReport = facade.generateDailyReport();
        System.out.println(dailyReport.generateReport());
    }
}
