package parking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.patterns.observer.ParkingObserver;
import parking.patterns.observer.ParkingEventManager;
import parking.patterns.observer.StatisticsObserver;
import parking.reports.ParkingStatistics;

/**
 * Tests for the Observer pattern implementation.
 */
public class ObserverTest {
    
    private ParkingEventManager eventManager;
    private TestObserver testObserver;
    
    /**
     * A test implementation of ParkingObserver for testing purposes.
     */
    private static class TestObserver implements ParkingObserver {
        public int entryCount = 0;
        public int exitCount = 0;
        public int statusChangeCount = 0;
        public String lastLicensePlate = null;
        public int lastSpotId = -1;
        public double lastDuration = 0;
        public double lastPayment = 0;
        
        @Override
        public void onVehicleEntry(String licensePlate, int spotId) {
            entryCount++;
            lastLicensePlate = licensePlate;
            lastSpotId = spotId;
        }
        
        @Override
        public void onVehicleExit(String licensePlate, int spotId, double durationHours, double payment) {
            exitCount++;
            lastLicensePlate = licensePlate;
            lastSpotId = spotId;
            lastDuration = durationHours;
            lastPayment = payment;
        }
        
        @Override
        public void onParkingStatusChange(int totalSpots, int occupiedSpots, int availableSpots) {
            statusChangeCount++;
        }
    }
    
    @BeforeEach
    public void setUp() {
        eventManager = new ParkingEventManager();
        testObserver = new TestObserver();
    }
    
    /**
     * Tests that observers can be added to the event manager.
     */
    @Test
    public void testAddObserver() {
        eventManager.addObserver(testObserver);
        eventManager.notifyVehicleEntry("ABC123", 1);
        
        assertEquals(1, testObserver.entryCount);
        assertEquals("ABC123", testObserver.lastLicensePlate);
    }
    
    /**
     * Tests that observers can be removed from the event manager.
     */
    @Test
    public void testRemoveObserver() {
        eventManager.addObserver(testObserver);
        eventManager.removeObserver(testObserver);
        eventManager.notifyVehicleEntry("ABC123", 1);
        
        assertEquals(0, testObserver.entryCount);
    }
    
    /**
     * Tests that duplicate observers are not added.
     */
    @Test
    public void testNoDuplicateObservers() {
        eventManager.addObserver(testObserver);
        eventManager.addObserver(testObserver);
        eventManager.notifyVehicleEntry("ABC123", 1);
        
        assertEquals(1, testObserver.entryCount);
    }
    
    /**
     * Tests vehicle entry notification.
     */
    @Test
    public void testNotifyVehicleEntry() {
        eventManager.addObserver(testObserver);
        eventManager.notifyVehicleEntry("XYZ789", 5);
        
        assertEquals(1, testObserver.entryCount);
        assertEquals("XYZ789", testObserver.lastLicensePlate);
        assertEquals(5, testObserver.lastSpotId);
    }
    
    /**
     * Tests vehicle exit notification.
     */
    @Test
    public void testNotifyVehicleExit() {
        eventManager.addObserver(testObserver);
        eventManager.notifyVehicleExit("ABC123", 3, 2.5, 45.0);
        
        assertEquals(1, testObserver.exitCount);
        assertEquals("ABC123", testObserver.lastLicensePlate);
        assertEquals(3, testObserver.lastSpotId);
        assertEquals(2.5, testObserver.lastDuration, 0.01);
        assertEquals(45.0, testObserver.lastPayment, 0.01);
    }
    
    /**
     * Tests parking status change notification.
     */
    @Test
    public void testNotifyParkingStatusChange() {
        eventManager.addObserver(testObserver);
        eventManager.notifyParkingStatusChange(100, 50, 50);
        
        assertEquals(1, testObserver.statusChangeCount);
    }
    
    /**
     * Tests multiple observers receive notifications.
     */
    @Test
    public void testMultipleObservers() {
        TestObserver observer1 = new TestObserver();
        TestObserver observer2 = new TestObserver();
        
        eventManager.addObserver(observer1);
        eventManager.addObserver(observer2);
        eventManager.notifyVehicleEntry("TEST123", 10);
        
        assertEquals(1, observer1.entryCount);
        assertEquals(1, observer2.entryCount);
    }
    
    /**
     * Tests StatisticsObserver integration.
     */
    @Test
    public void testStatisticsObserver() {
        ParkingStatistics statistics = new ParkingStatistics();
        StatisticsObserver statsObserver = new StatisticsObserver(statistics);
        
        eventManager.addObserver(statsObserver);
        eventManager.notifyVehicleEntry("CAR001", 1);
        
        // The statistics should have recorded the entry
        assertTrue(statistics.getDailyEntries() >= 1);
    }
}
