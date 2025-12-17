package parking.core;

/**
 * The ParkingSpot class represents a single parking spot in a parking lot.
 * It tracks whether the spot is occupied, and whether it is designated for disabled persons.
 * 
 * @author Smart Parking System Team
 */
public class ParkingSpot {
    private final int spotId;
    private boolean isOccupied;
    private final boolean isDisabledSpot;
    private Vehicle occupyingVehicle;

    /**
     * Constructs a new ParkingSpot with the specified parameters.
     *
     * @param spotId The unique identifier of this parking spot.
     * @param isDisabledSpot Whether this parking spot is designated for disabled persons.
     */
    public ParkingSpot(int spotId, boolean isDisabledSpot) {
        this.spotId = spotId;
        this.isOccupied = false;
        this.isDisabledSpot = isDisabledSpot;
        this.occupyingVehicle = null;
    }

    /**
     * Attempts to park a vehicle in this spot.
     * Regular vehicles cannot park in spots designated for disabled persons.
     *
     * @param vehicle The vehicle to park in this spot.
     * @return true if the parking was successful, false otherwise.
     * @throws IllegalStateException if the spot is already occupied.
     */
    public boolean parkVehicle(Vehicle vehicle) {
        if (isOccupied) {
            throw new IllegalStateException("Parking spot " + spotId + " is already occupied");
        }

        if (isDisabledSpot && !vehicle.isDisabled()) {
            return false;
        }

        this.occupyingVehicle = vehicle;
        this.isOccupied = true;
        return true;
    }

    /**
     * Removes the currently parked vehicle from this spot.
     *
     * @return The removed vehicle, or null if the spot was already empty.
     */
    public Vehicle removeVehicle() {
        if (!isOccupied) {
            return null;
        }

        Vehicle removedVehicle = this.occupyingVehicle;
        this.occupyingVehicle = null;
        this.isOccupied = false;
        return removedVehicle;
    }

    /**
     * Vacates this parking spot completely.
     */
    public void vacate() {
        this.occupyingVehicle = null;
        this.isOccupied = false;
    }

    public int getSpotId() {
        return spotId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isDisabledSpot() {
        return isDisabledSpot;
    }

    public Vehicle getOccupyingVehicle() {
        return occupyingVehicle;
    }
}