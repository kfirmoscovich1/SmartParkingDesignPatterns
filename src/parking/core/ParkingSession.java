package parking.core;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * The ParkingSession class represents a single parking session in a parking lot.
 * It tracks the vehicle, the spot, and the entry and exit times.
 * 
 * @author Smart Parking System Team
 */
public class ParkingSession {
    private final Vehicle vehicle;
    private final ParkingSpot parkingSpot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amountPaid;
    private final boolean isSubscription;

    /**
     * Constructs a new ParkingSession with the specified parameters.
     *
     * @param vehicle The vehicle involved in this parking session.
     * @param parkingSpot The parking spot allocated for this session.
     * @param isSubscription Whether this session is for a subscriber.
     */
    public ParkingSession(Vehicle vehicle, ParkingSpot parkingSpot, boolean isSubscription) {
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
        this.vehicle.setEntryTime(entryTime);
        this.exitTime = null;
        this.amountPaid = 0.0;
        this.isSubscription = isSubscription;
    }

    /**
     * Ends this parking session by setting the exit time to the current time.
     */
    public void endSession() {
        if (exitTime == null) {
            this.exitTime = LocalDateTime.now();
        }
    }

    /**
     * Calculates the duration of this parking session in hours.
     * If the session is not ended yet, calculates the duration until the current time.
     *
     * @return The duration in hours.
     */
    public double getDurationHours() {
        LocalDateTime end = exitTime != null ? exitTime : LocalDateTime.now();
        Duration duration = Duration.between(entryTime, end);
        return duration.toMinutes() / 60.0;
    }

    /**
     * Records a payment for this parking session.
     *
     * @param amount The amount paid.
     */
    public void recordPayment(double amount) {
        this.amountPaid = amount;
    }

    /**
     * Checks if this session is active (vehicle still parked).
     *
     * @return true if the session is active, false otherwise.
     */
    public boolean isActive() {
        return exitTime == null;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public boolean isSubscription() {
        return isSubscription;
    }
}