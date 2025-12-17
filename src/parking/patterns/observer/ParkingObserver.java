package parking.patterns.observer;

/**
 * The {@code ParkingObserver} interface defines the contract for objects that
 * observe changes in the parking lot status.
 * This is part of the Observer design pattern.
 *
 * @author Smart Parking System Team
 */
public interface ParkingObserver {
    /**
     * Called when a vehicle enters the parking lot.
     *
     * @param licensePlate The license plate of the vehicle that entered.
     * @param spotId The ID of the spot where the vehicle parked.
     */
    void onVehicleEntry(String licensePlate, int spotId);

    /**
     * Called when a vehicle exits the parking lot.
     *
     * @param licensePlate The license plate of the vehicle that exited.
     * @param spotId The ID of the spot that the vehicle vacated.
     * @param durationHours The duration of the parking in hours.
     * @param payment The amount paid for the parking.
     */
    void onVehicleExit(String licensePlate, int spotId, double durationHours, double payment);

    /**
     * Called when the parking lot status changes (e.g., capacity changes).
     *
     * @param totalSpots The total number of spots in the parking lot.
     * @param occupiedSpots The number of currently occupied spots.
     * @param availableSpots The number of currently available spots.
     */
    void onParkingStatusChange(int totalSpots, int occupiedSpots, int availableSpots);
}