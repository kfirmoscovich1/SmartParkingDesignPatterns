package parking.patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ParkingEventManager} class manages observers of parking events
 * and notifies them when events occur.
 * This is part of the Observer design pattern.
 *
 * @author Smart Parking System Team
 */
public class ParkingEventManager {
    /** The list of observers to be notified of parking events. */
    private final List<ParkingObserver> observers;

    /**
     * Constructs a new {@code ParkingEventManager}.
     */
    public ParkingEventManager() {
        this.observers = new ArrayList<>();
    }

    /**
     * Adds an observer to be notified of parking events.
     *
     * @param observer The observer to add.
     */
    public void addObserver(ParkingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer so it will no longer be notified of parking events.
     *
     * @param observer The observer to remove.
     */
    public void removeObserver(ParkingObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers that a vehicle has entered the parking lot.
     *
     * @param licensePlate The license plate of the vehicle that entered.
     * @param spotId The ID of the spot where the vehicle parked.
     */
    public void notifyVehicleEntry(String licensePlate, int spotId) {
        for (ParkingObserver observer : observers) {
            observer.onVehicleEntry(licensePlate, spotId);
        }
    }

    /**
     * Notifies all observers that a vehicle has exited the parking lot.
     *
     * @param licensePlate The license plate of the vehicle that exited.
     * @param spotId The ID of the spot that the vehicle vacated.
     * @param durationHours The duration of the parking in hours.
     * @param payment The amount paid for the parking.
     */
    public void notifyVehicleExit(String licensePlate, int spotId, double durationHours, double payment) {
        for (ParkingObserver observer : observers) {
            observer.onVehicleExit(licensePlate, spotId, durationHours, payment);
        }
    }

    /**
     * Notifies all observers that the parking lot status has changed.
     *
     * @param totalSpots The total number of spots in the parking lot.
     * @param occupiedSpots The number of currently occupied spots.
     * @param availableSpots The number of currently available spots.
     */
    public void notifyParkingStatusChange(int totalSpots, int occupiedSpots, int availableSpots) {
        for (ParkingObserver observer : observers) {
            observer.onParkingStatusChange(totalSpots, occupiedSpots, availableSpots);
        }
    }
}