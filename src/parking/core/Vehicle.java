package parking.core;

import java.time.LocalDateTime;

/**
 * The Vehicle class represents an individual vehicle in a parking lot.
 * It contains information about the vehicle's license plate, owner, and disability status.
 * This class implements the Prototype pattern, allowing vehicles to be cloned.
 * 
 * @author Smart Parking System Team
 */
public abstract class Vehicle implements Cloneable {
    private String licensePlate;
    private String ownerName;
    private boolean isDisabled;
    private LocalDateTime entryTime;
    private String color;

    /**
     * Constructs a new Vehicle object with the specified parameters.
     *
     * @param licensePlate The license plate of the vehicle.
     * @param ownerName The name of the vehicle owner.
     * @param isDisabled Whether this is a disabled person's vehicle.
     * @param color The color of the vehicle.
     */
    public Vehicle(String licensePlate, String ownerName, boolean isDisabled, String color) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.isDisabled = isDisabled;
        this.color = color;
        this.entryTime = null;
    }

    /**
     * Returns the hourly rate for this vehicle type.
     * 
     * @return The hourly rate for parking this vehicle.
     */
    public abstract double getHourlyRate();

    /**
     * Creates and returns a copy of this vehicle object.
     * 
     * @return A clone of this vehicle.
     */
    @Override
    public Vehicle clone() {
        try {
            return (Vehicle) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Vehicle cloning failed", e);
        }
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}