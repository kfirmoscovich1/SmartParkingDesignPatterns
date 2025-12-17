package parking.cli;

import parking.core.Vehicle;
import parking.management.SubscriptionType;
import parking.patterns.builder.ParkingReport;
import parking.patterns.facade.ParkingSystemFacade;
import parking.patterns.factory.VehicleType;
import parking.util.ParkingLogger;

import java.util.Scanner;

/**
 * The {@code ParkingCLI} class provides a command-line interface for the parking system.
 * It allows users to interact with the system through text commands.
 *
 * @author Smart Parking System Team
 */
public class ParkingCLI {
    
    /** The facade for the parking system. */
    private final ParkingSystemFacade facade;
    
    /** The logger for this class. */
    private final ParkingLogger logger;
    
    /** Scanner for reading user input. */
    private final Scanner scanner;
    
    /** Flag to control the main loop. */
    private boolean running;
    
    /**
     * Constructs a new ParkingCLI.
     */
    public ParkingCLI() {
        this.facade = new ParkingSystemFacade();
        this.logger = ParkingLogger.getLogger(ParkingCLI.class);
        this.scanner = new Scanner(System.in);
        this.running = true;
    }
    
    /**
     * Starts the CLI application.
     */
    public void start() {
        printWelcome();
        
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            processCommand(choice);
        }
        
        scanner.close();
        System.out.println("\nThank you for using Smart Parking System. Goodbye!");
    }
    
    /**
     * Prints the welcome message.
     */
    private void printWelcome() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║     SMART PARKING SYSTEM v1.0.0          ║");
        System.out.println("║     Design Patterns Project              ║");
        System.out.println("╚══════════════════════════════════════════╝\n");
    }
    
    /**
     * Prints the main menu.
     */
    private void printMenu() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│           MAIN MENU                 │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. Park a vehicle                   │");
        System.out.println("│ 2. Remove a vehicle                 │");
        System.out.println("│ 3. View parking status              │");
        System.out.println("│ 4. Create subscription              │");
        System.out.println("│ 5. Park subscriber vehicle          │");
        System.out.println("│ 6. Generate daily report            │");
        System.out.println("│ 7. Exit                             │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Enter your choice (1-7): ");
    }
    
    /**
     * Processes a user command.
     *
     * @param choice The user's choice.
     */
    private void processCommand(String choice) {
        switch (choice) {
            case "1":
                parkVehicle();
                break;
            case "2":
                removeVehicle();
                break;
            case "3":
                viewStatus();
                break;
            case "4":
                createSubscription();
                break;
            case "5":
                parkSubscriberVehicle();
                break;
            case "6":
                generateReport();
                break;
            case "7":
                running = false;
                break;
            default:
                System.out.println("❌ Invalid choice. Please enter a number between 1 and 7.");
        }
    }
    
    /**
     * Handles parking a new vehicle.
     */
    private void parkVehicle() {
        System.out.println("\n--- PARK A VEHICLE ---");
        
        // Get vehicle type
        System.out.print("Vehicle type (1=Car, 2=Motorcycle): ");
        String typeInput = scanner.nextLine().trim();
        VehicleType type = typeInput.equals("2") ? VehicleType.MOTORCYCLE : VehicleType.CAR;
        
        // Get license plate
        System.out.print("License plate: ");
        String licensePlate = scanner.nextLine().trim().toUpperCase();
        
        if (licensePlate.isEmpty()) {
            System.out.println("❌ License plate cannot be empty.");
            return;
        }
        
        // Get owner name
        System.out.print("Owner name: ");
        String ownerName = scanner.nextLine().trim();
        
        // Get disabled status
        System.out.print("Is disabled vehicle? (y/n): ");
        boolean isDisabled = scanner.nextLine().trim().toLowerCase().startsWith("y");
        
        // Get color
        System.out.print("Vehicle color: ");
        String color = scanner.nextLine().trim();
        if (color.isEmpty()) {
            color = "Unknown";
        }
        
        // Create and park vehicle
        Vehicle vehicle = facade.createVehicle(type, licensePlate, ownerName, isDisabled, color);
        boolean success = facade.parkVehicle(vehicle);
        
        if (success) {
            System.out.println("✅ Vehicle " + licensePlate + " parked successfully!");
            logger.logVehicleEntry(licensePlate, 0);
        } else {
            System.out.println("❌ Failed to park vehicle. Parking may be full or vehicle already parked.");
        }
    }
    
    /**
     * Handles removing a vehicle from the parking lot.
     */
    private void removeVehicle() {
        System.out.println("\n--- REMOVE A VEHICLE ---");
        
        System.out.print("Enter license plate: ");
        String licensePlate = scanner.nextLine().trim().toUpperCase();
        
        if (licensePlate.isEmpty()) {
            System.out.println("❌ License plate cannot be empty.");
            return;
        }
        
        double payment = facade.removeVehicle(licensePlate);
        
        if (payment >= 0) {
            System.out.println("✅ Vehicle " + licensePlate + " exited successfully.");
            System.out.println("💰 Payment due: $" + String.format("%.2f", payment));
            logger.logVehicleExit(licensePlate, payment);
        } else {
            System.out.println("❌ Vehicle " + licensePlate + " not found in parking lot.");
        }
    }
    
    /**
     * Displays the current parking status.
     */
    private void viewStatus() {
        System.out.println("\n--- PARKING STATUS ---");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│ Occupancy: " + String.format("%6.1f%%", facade.getOccupancyPercentage()) + "                  │");
        System.out.println("│ Available spots: " + String.format("%3d", facade.getAvailableSpots()) + "                 │");
        System.out.println("└─────────────────────────────────────┘");
    }
    
    /**
     * Handles creating a new subscription.
     */
    private void createSubscription() {
        System.out.println("\n--- CREATE SUBSCRIPTION ---");
        
        System.out.print("License plate: ");
        String licensePlate = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Owner name: ");
        String ownerName = scanner.nextLine().trim();
        
        System.out.print("Duration in months: ");
        int months;
        try {
            months = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number of months.");
            return;
        }
        
        System.out.print("Subscription type (1=Standard, 2=Premium): ");
        String typeInput = scanner.nextLine().trim();
        SubscriptionType type = typeInput.equals("2") ? SubscriptionType.PREMIUM : SubscriptionType.STANDARD;
        
        String subscriptionId = facade.createSubscription(licensePlate, ownerName, months, type);
        System.out.println("✅ Subscription created successfully!");
        System.out.println("📋 Subscription ID: " + subscriptionId);
    }
    
    /**
     * Handles parking a subscriber's vehicle.
     */
    private void parkSubscriberVehicle() {
        System.out.println("\n--- PARK SUBSCRIBER VEHICLE ---");
        
        System.out.print("Subscription ID: ");
        String subscriptionId = scanner.nextLine().trim();
        
        System.out.print("Vehicle type (1=Car, 2=Motorcycle): ");
        String typeInput = scanner.nextLine().trim();
        VehicleType type = typeInput.equals("2") ? VehicleType.MOTORCYCLE : VehicleType.CAR;
        
        System.out.print("License plate: ");
        String licensePlate = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Owner name: ");
        String ownerName = scanner.nextLine().trim();
        
        System.out.print("Vehicle color: ");
        String color = scanner.nextLine().trim();
        if (color.isEmpty()) {
            color = "Unknown";
        }
        
        Vehicle vehicle = facade.createVehicle(type, licensePlate, ownerName, false, color);
        boolean success = facade.parkSubscriberVehicle(vehicle, subscriptionId);
        
        if (success) {
            System.out.println("✅ Subscriber vehicle parked successfully!");
        } else {
            System.out.println("❌ Failed to park. Invalid subscription or parking full.");
        }
    }
    
    /**
     * Generates and displays a daily report.
     */
    private void generateReport() {
        System.out.println("\n--- DAILY REPORT ---");
        ParkingReport report = facade.generateDailyReport();
        System.out.println(report.toString());
    }
    
    /**
     * Main entry point for CLI mode.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        ParkingCLI cli = new ParkingCLI();
        cli.start();
    }
}
