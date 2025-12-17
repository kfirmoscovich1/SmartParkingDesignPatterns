package parking.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code ParkingLogger} class provides logging functionality for the parking system.
 * It supports different log levels and can output to console or file.
 *
 * @author Smart Parking System Team
 */
public class ParkingLogger {
    
    /** Log level enumeration. */
    public enum Level {
        DEBUG(0, "DEBUG"),
        INFO(1, "INFO"),
        WARN(2, "WARN"),
        ERROR(3, "ERROR");
        
        private final int priority;
        private final String label;
        
        Level(int priority, String label) {
            this.priority = priority;
            this.label = label;
        }
        
        public int getPriority() {
            return priority;
        }
        
        public String getLabel() {
            return label;
        }
    }
    
    /** The singleton instance. */
    private static ParkingLogger instance;
    
    /** The current minimum log level. */
    private Level minLevel;
    
    /** Date time formatter for log entries. */
    private final DateTimeFormatter formatter;
    
    /** The name of the logger (usually the class name). */
    private String loggerName;
    
    /**
     * Private constructor for singleton.
     */
    private ParkingLogger() {
        this.minLevel = Level.INFO;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.loggerName = "ParkingSystem";
    }
    
    /**
     * Gets the singleton instance.
     *
     * @return The logger instance.
     */
    public static synchronized ParkingLogger getInstance() {
        if (instance == null) {
            instance = new ParkingLogger();
        }
        return instance;
    }
    
    /**
     * Gets a logger for a specific class.
     *
     * @param clazz The class to log for.
     * @return The logger instance.
     */
    public static ParkingLogger getLogger(Class<?> clazz) {
        ParkingLogger logger = getInstance();
        logger.loggerName = clazz.getSimpleName();
        return logger;
    }
    
    /**
     * Sets the minimum log level.
     *
     * @param level The minimum level to log.
     */
    public void setLevel(Level level) {
        this.minLevel = level;
    }
    
    /**
     * Logs a message at the specified level.
     *
     * @param level The log level.
     * @param message The message to log.
     */
    private void log(Level level, String message) {
        if (level.getPriority() >= minLevel.getPriority()) {
            String timestamp = LocalDateTime.now().format(formatter);
            String logMessage = String.format("[%s] [%s] [%s] %s", 
                    timestamp, level.getLabel(), loggerName, message);
            
            if (level == Level.ERROR || level == Level.WARN) {
                System.err.println(logMessage);
            } else {
                System.out.println(logMessage);
            }
        }
    }
    
    /**
     * Logs a message at the specified level with exception details.
     *
     * @param level The log level.
     * @param message The message to log.
     * @param throwable The exception to log.
     */
    private void log(Level level, String message, Throwable throwable) {
        log(level, message + " - " + throwable.getMessage());
        if (minLevel == Level.DEBUG) {
            throwable.printStackTrace();
        }
    }
    
    /**
     * Logs a debug message.
     *
     * @param message The message to log.
     */
    public void debug(String message) {
        log(Level.DEBUG, message);
    }
    
    /**
     * Logs an info message.
     *
     * @param message The message to log.
     */
    public void info(String message) {
        log(Level.INFO, message);
    }
    
    /**
     * Logs a warning message.
     *
     * @param message The message to log.
     */
    public void warn(String message) {
        log(Level.WARN, message);
    }
    
    /**
     * Logs a warning message with exception.
     *
     * @param message The message to log.
     * @param throwable The exception.
     */
    public void warn(String message, Throwable throwable) {
        log(Level.WARN, message, throwable);
    }
    
    /**
     * Logs an error message.
     *
     * @param message The message to log.
     */
    public void error(String message) {
        log(Level.ERROR, message);
    }
    
    /**
     * Logs an error message with exception.
     *
     * @param message The message to log.
     * @param throwable The exception.
     */
    public void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }
    
    // Convenience methods for formatted logging
    
    /**
     * Logs vehicle entry.
     *
     * @param licensePlate The license plate.
     * @param spotId The spot ID.
     */
    public void logVehicleEntry(String licensePlate, int spotId) {
        info(String.format("Vehicle entered: %s -> Spot #%d", licensePlate, spotId));
    }
    
    /**
     * Logs vehicle exit.
     *
     * @param licensePlate The license plate.
     * @param payment The payment amount.
     */
    public void logVehicleExit(String licensePlate, double payment) {
        info(String.format("Vehicle exited: %s | Payment: $%.2f", licensePlate, payment));
    }
    
    /**
     * Logs parking lot status.
     *
     * @param occupied The number of occupied spots.
     * @param total The total number of spots.
     */
    public void logStatus(int occupied, int total) {
        info(String.format("Parking status: %d/%d spots occupied (%.1f%%)", 
                occupied, total, (double) occupied / total * 100));
    }
}
