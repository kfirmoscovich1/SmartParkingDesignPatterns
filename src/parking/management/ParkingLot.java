package parking.management;

import parking.core.Vehicle;
import parking.core.ParkingSpot;
import parking.core.ParkingSession;
import parking.patterns.observer.ParkingEventManager;
import parking.patterns.observer.ParkingObserver;
import parking.patterns.observer.StatisticsObserver;
import parking.patterns.observer.DisplayObserver;
import parking.reports.ParkingStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ParkingLot} class manages the parking spots and vehicles.
 * It follows the Singleton design pattern to ensure that only one instance exists.
 * 
 * @author Smart Parking System Team
 */
public class ParkingLot {

    /** The singleton instance of this class. */
    private static ParkingLot instance;

    /** The total number of regular spots in this parking lot. */
    private static final int REGULAR_SPOTS = 100;

    /** The total number of disabled spots in this parking lot. */
    private static final int DISABLED_SPOTS = 20;

    /** The list of all spots in this parking lot. */
    private final List<ParkingSpot> spots;

    /** The list of current parking sessions. */
    private final List<ParkingSession> currentSessions;

    /** The history of all completed parking sessions. */
    private final List<ParkingSession> sessionHistory;

    /** The event manager for notifying observers about parking events. */
    private final ParkingEventManager eventManager;

    /** The parking statistics for tracking. */
    private ParkingStatistics statistics;

    /**
     * Private constructor to prevent instantiation from outside.
     * This is part of the Singleton design pattern.
     */
    private ParkingLot() {
        this.spots = new ArrayList<>();
        this.currentSessions = new ArrayList<>();
        this.sessionHistory = new ArrayList<>();
        this.eventManager = new ParkingEventManager();
        initializeSpots();
    }

    /**
     * Gets the singleton instance of this class.
     * 
     * @return The singleton instance.
     */
    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public void initialize(ParkingStatistics statistics) {
        this.statistics = statistics;
        eventManager.addObserver(new StatisticsObserver(statistics));
        eventManager.addObserver(new DisplayObserver());
        notifyStatusChange();
    }

    private void initializeSpots() {
        for (int i = 1; i <= REGULAR_SPOTS; i++) {
            spots.add(new ParkingSpot(i, false));
        }
        for (int i = REGULAR_SPOTS + 1; i <= REGULAR_SPOTS + DISABLED_SPOTS; i++) {
            spots.add(new ParkingSpot(i, true));
        }
    }

    public boolean parkVehicle(Vehicle vehicle, boolean isSubscription) {
        if (vehicle == null) return false;

        for (ParkingSession session : currentSessions) {
            if (session.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate())) return false;
        }

        ParkingSpot spot = findAvailableSpot(vehicle.isDisabled());
        if (spot == null || !spot.parkVehicle(vehicle)) return false;

        ParkingSession session = new ParkingSession(vehicle, spot, isSubscription);
        currentSessions.add(session);

        if (statistics != null) {
            statistics.recordVehicleType(vehicle);
        }

        eventManager.notifyVehicleEntry(vehicle.getLicensePlate(), spot.getSpotId());
        notifyStatusChange();

        return true;
    }

    public ParkingSession parkVehicle(Vehicle vehicle) {
        if (parkVehicle(vehicle, false)) {
            for (ParkingSession session : currentSessions) {
                if (session.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate())) {
                    return session;
                }
            }
        }
        return null;
    }

    public ParkingSession removeVehicle(String licensePlate) {
        ParkingSession sessionToRemove = null;
        for (ParkingSession session : currentSessions) {
            if (session.getVehicle().getLicensePlate().equals(licensePlate)) {
                sessionToRemove = session;
                break;
            }
        }

        if (sessionToRemove == null) return null;

        sessionToRemove.endSession();
        ParkingSpot spot = sessionToRemove.getParkingSpot();
        Vehicle vehicle = spot.removeVehicle();
        if (vehicle == null) return null;

        currentSessions.remove(sessionToRemove);
        sessionHistory.add(sessionToRemove);

        double durationHours = sessionToRemove.getDurationHours();
        double payment = sessionToRemove.getAmountPaid();

        eventManager.notifyVehicleExit(licensePlate, spot.getSpotId(), durationHours, payment);
        notifyStatusChange();

        return sessionToRemove;
    }

    private ParkingSpot findAvailableSpot(boolean isDisabled) {
        for (ParkingSpot spot : spots) {
            if (!spot.isOccupied() && spot.isDisabledSpot() == isDisabled) return spot;
        }

        if (isDisabled) {
            for (ParkingSpot spot : spots) {
                if (!spot.isOccupied() && !spot.isDisabledSpot()) return spot;
            }
        }

        return null;
    }

    public double getOccupancyPercentage() {
        int totalSpots = spots.size();
        int occupiedSpots = 0;
        for (ParkingSpot spot : spots) {
            if (spot.isOccupied()) occupiedSpots++;
        }
        return (double) occupiedSpots / totalSpots * 100;
    }

    public int getAvailableSpots() {
        int availableSpots = 0;
        for (ParkingSpot spot : spots) {
            if (!spot.isOccupied()) availableSpots++;
        }
        return availableSpots;
    }

    public int getOccupiedSpots() {
        return spots.size() - getAvailableSpots();
    }

    public List<ParkingSession> getCurrentSessions() {
        return new ArrayList<>(currentSessions);
    }

    public List<ParkingSession> getSessionHistory() {
        return new ArrayList<>(sessionHistory);
    }

    public void addObserver(ParkingObserver observer) {
        eventManager.addObserver(observer);
    }

    public void removeObserver(ParkingObserver observer) {
        eventManager.removeObserver(observer);
    }

    private void notifyStatusChange() {
        eventManager.notifyParkingStatusChange(
            spots.size(),
            getOccupiedSpots(),
            getAvailableSpots()
        );
    }

    public void reset() {
        currentSessions.clear();
        for (ParkingSpot spot : spots) {
            spot.vacate();
        }
        notifyStatusChange();
    }
}
