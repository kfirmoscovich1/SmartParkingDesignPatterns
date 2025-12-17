package parking.patterns.observer;

/**
 * The {@code DisplayObserver} class observes parking events and
 * displays information to the system operator.
 * This is part of the Observer design pattern.
 *
 * @author Smart Parking System Team
 */
public class DisplayObserver implements ParkingObserver {
    /**
     * Called when a vehicle enters the parking lot.
     * Displays entry information.
     *
     * @param licensePlate The license plate of the vehicle that entered.
     * @param spotId The ID of the spot where the vehicle parked.
     */
    @Override
    public void onVehicleEntry(String licensePlate, int spotId) {
        System.out.println("ENTRY: Vehicle " + licensePlate + " parked in spot " + spotId);
    }

    /**
     * Called when a vehicle exits the parking lot.
     * Displays exit and payment information.
     *
     * @param licensePlate The license plate of the vehicle that exited.
     * @param spotId The ID of the spot that the vehicle vacated.
     * @param durationHours The duration of the parking in hours.
     * @param payment The amount paid for the parking.
     */
    @Override
    public void onVehicleExit(String licensePlate, int spotId, double durationHours, double payment) {
        System.out.printf("EXIT: Vehicle %s left spot %d after %.2f hours. Payment: ₪%.2f%n",
                licensePlate, spotId, durationHours, payment);
    }

    /**
     * Called when the parking lot status changes.
     * Displays occupancy information.
     *
     * @param totalSpots The total number of spots in the parking lot.
     * @param occupiedSpots The number of currently occupied spots.
     * @param availableSpots The number of currently available spots.
     */
    @Override
    public void onParkingStatusChange(int totalSpots, int occupiedSpots, int availableSpots) {
        System.out.printf("STATUS: %d/%d spots occupied (%.1f%%). %d spots available.%n",
                occupiedSpots, totalSpots, (double) occupiedSpots / totalSpots * 100, availableSpots);
    }
}