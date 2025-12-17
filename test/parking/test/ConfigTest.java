package parking.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.config.ParkingConfig;

/**
 * Tests for the ParkingConfig configuration class.
 */
public class ConfigTest {
    
    /**
     * Tests that getInstance returns the same instance (Singleton).
     */
    @Test
    public void testSingletonInstance() {
        ParkingConfig config1 = ParkingConfig.getInstance();
        ParkingConfig config2 = ParkingConfig.getInstance();
        
        assertSame(config1, config2);
    }
    
    /**
     * Tests default regular spots value.
     */
    @Test
    public void testDefaultRegularSpots() {
        ParkingConfig config = ParkingConfig.getInstance();
        assertEquals(100, config.getRegularSpots());
    }
    
    /**
     * Tests default disabled spots value.
     */
    @Test
    public void testDefaultDisabledSpots() {
        ParkingConfig config = ParkingConfig.getInstance();
        assertEquals(20, config.getDisabledSpots());
    }
    
    /**
     * Tests total spots calculation.
     */
    @Test
    public void testTotalSpots() {
        ParkingConfig config = ParkingConfig.getInstance();
        assertEquals(120, config.getTotalSpots());
    }
    
    /**
     * Tests default car hourly rate.
     */
    @Test
    public void testDefaultCarHourlyRate() {
        ParkingConfig config = ParkingConfig.getInstance();
        assertEquals(18.0, config.getCarHourlyRate());
    }
    
    /**
     * Tests default motorcycle hourly rate.
     */
    @Test
    public void testDefaultMotorcycleHourlyRate() {
        ParkingConfig config = ParkingConfig.getInstance();
        assertEquals(12.0, config.getMotorcycleHourlyRate());
    }
    
    /**
     * Tests default free hours.
     */
    @Test
    public void testDefaultFreeHours() {
        ParkingConfig config = ParkingConfig.getInstance();
        assertEquals(2.0, config.getFreeHours());
    }
    
    /**
     * Tests config toString method.
     */
    @Test
    public void testToString() {
        ParkingConfig config = ParkingConfig.getInstance();
        String str = config.toString();
        
        assertNotNull(str);
        assertTrue(str.contains("regularSpots"));
        assertTrue(str.contains("carHourlyRate"));
    }
}
