package parking.patterns.observer;

import parking.reports.ParkingStatistics;

/**
 * The {@code StatisticsObserver} class observes parking events and
 * updates the statistics accordingly.
 * This is part of the Observer design pattern.
 *
 * @author Smart Parking System Team
 */
public class StatisticsObserver implements ParkingObserver {
    /** The statistics object to update. */
    private final ParkingStatistics statistics;

    /**
     * Constructs a new {@code StatisticsObserver} with the specified statistics object.
     *
     * @param statistics The statistics object to update based on observed events.
     */
    public StatisticsObserver(ParkingStatistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Called when a vehicle enters the parking lot.
     * Updates the entry statistics.
     *
     * @param licensePlate The license plate of the vehicle that entered.
     * @param spotId The ID of the spot where the vehicle parked.
     */
    @Override
    public void onVehicleEntry(String licensePlate, int spotId) {
        statistics.recordEntry(licensePlate);
    }

    /**
     * Called when a vehicle exits the parking lot.
     * Updates the exit statistics and financials.
     *
     * @param licensePlate The license plate of the vehicle that exited.
     * @param spotId The ID of the spot that the vehicle vacated.
     * @param durationHours The duration of the parking in hours.
     * @param payment The amount paid for the parking.
     */
    @Override
    public void onVehicleExit(String licensePlate, int spotId, double durationHours, double payment) {
        statistics.recordExit(licensePlate, durationHours, payment);
    }

    /**
     * Called when the parking lot status changes.
     * Updates the occupancy statistics.
     *
     * @param totalSpots The total number of spots in the parking lot.
     * @param occupiedSpots The number of currently occupied spots.
     * @param availableSpots The number of currently available spots.
     */
    @Override
    public void onParkingStatusChange(int totalSpots, int occupiedSpots, int availableSpots) {
        statistics.updateOccupancy(totalSpots, occupiedSpots, availableSpots);
    }
}