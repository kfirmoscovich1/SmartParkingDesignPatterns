package parking.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The {@code ParkingConfig} class manages configuration settings for the parking system.
 * It loads settings from a properties file and provides default values if not found.
 * This follows the Singleton pattern for global configuration access.
 *
 * @author Smart Parking System Team
 */
public class ParkingConfig {
    
    /** The singleton instance of this class. */
    private static ParkingConfig instance;
    
    /** The properties object holding all configuration. */
    private final Properties properties;
    
    // Parking lot configuration
    private int regularSpots;
    private int disabledSpots;
    
    // Pricing configuration
    private double carHourlyRate;
    private double carDisabledHourlyRate;
    private double motorcycleHourlyRate;
    private double motorcycleDisabledHourlyRate;
    private double freeHours;
    
    // Subscription configuration
    private double standardSubscriptionDiscount;
    private double premiumSubscriptionDiscount;
    
    /**
     * Private constructor to prevent instantiation from outside.
     */
    private ParkingConfig() {
        this.properties = new Properties();
        loadConfiguration();
    }
    
    /**
     * Gets the singleton instance of this class.
     *
     * @return The singleton instance.
     */
    public static synchronized ParkingConfig getInstance() {
        if (instance == null) {
            instance = new ParkingConfig();
        }
        return instance;
    }
    
    /**
     * Loads configuration from the properties file or sets default values.
     */
    private void loadConfiguration() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("parking.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            // Use default values if file not found
        }
        
        // Load values with defaults
        regularSpots = getIntProperty("parking.spots.regular", 100);
        disabledSpots = getIntProperty("parking.spots.disabled", 20);
        
        carHourlyRate = getDoubleProperty("pricing.car.hourly", 18.0);
        carDisabledHourlyRate = getDoubleProperty("pricing.car.disabled.hourly", 8.0);
        motorcycleHourlyRate = getDoubleProperty("pricing.motorcycle.hourly", 12.0);
        motorcycleDisabledHourlyRate = getDoubleProperty("pricing.motorcycle.disabled.hourly", 6.0);
        freeHours = getDoubleProperty("pricing.free.hours", 2.0);
        
        standardSubscriptionDiscount = getDoubleProperty("subscription.standard.discount", 0.2);
        premiumSubscriptionDiscount = getDoubleProperty("subscription.premium.discount", 0.4);
    }
    
    /**
     * Gets an integer property value.
     *
     * @param key The property key.
     * @param defaultValue The default value if not found.
     * @return The property value.
     */
    private int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    /**
     * Gets a double property value.
     *
     * @param key The property key.
     * @param defaultValue The default value if not found.
     * @return The property value.
     */
    private double getDoubleProperty(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Double.parseDouble(value.trim());
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    // Getters for configuration values
    
    public int getRegularSpots() {
        return regularSpots;
    }
    
    public int getDisabledSpots() {
        return disabledSpots;
    }
    
    public int getTotalSpots() {
        return regularSpots + disabledSpots;
    }
    
    public double getCarHourlyRate() {
        return carHourlyRate;
    }
    
    public double getCarDisabledHourlyRate() {
        return carDisabledHourlyRate;
    }
    
    public double getMotorcycleHourlyRate() {
        return motorcycleHourlyRate;
    }
    
    public double getMotorcycleDisabledHourlyRate() {
        return motorcycleDisabledHourlyRate;
    }
    
    public double getFreeHours() {
        return freeHours;
    }
    
    public double getStandardSubscriptionDiscount() {
        return standardSubscriptionDiscount;
    }
    
    public double getPremiumSubscriptionDiscount() {
        return premiumSubscriptionDiscount;
    }
    
    /**
     * Reloads configuration from file.
     */
    public void reload() {
        loadConfiguration();
    }
    
    @Override
    public String toString() {
        return "ParkingConfig{" +
                "regularSpots=" + regularSpots +
                ", disabledSpots=" + disabledSpots +
                ", carHourlyRate=" + carHourlyRate +
                ", motorcycleHourlyRate=" + motorcycleHourlyRate +
                ", freeHours=" + freeHours +
                '}';
    }
}
