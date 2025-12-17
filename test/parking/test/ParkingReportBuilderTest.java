package parking.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import parking.patterns.builder.ParkingReport;
import parking.patterns.builder.ParkingReportBuilder;

import java.time.LocalDateTime;

/**
 * Tests for the ParkingReportBuilder pattern implementation.
 */
public class ParkingReportBuilderTest {
    /**
     * Tests creating a basic report with the builder.
     */
    @Test
    public void testBasicReportBuilding() {
        ParkingReport report = new ParkingReportBuilder()
                .setTitle("Test Report")
                .setTimePeriod("Test Period")
                .setTotalEntries(10)
                .setTotalRevenue(500.0)
                .setCurrentOccupancy(50.0)
                .setAverageDuration(3.5)
                .build();

        assertEquals("Test Report", report.getTitle());
        assertEquals("Test Period", report.getTimePeriod());
        assertEquals(10, report.getTotalEntries());
        assertEquals(500.0, report.getTotalRevenue());
        assertEquals(50.0, report.getCurrentOccupancy());
        assertEquals(3.5, report.getAverageDuration());
    }

    /**
     * Tests creating a report with vehicle statistics.
     */
    @Test
    public void testReportWithVehicleStats() {
        ParkingReport report = new ParkingReportBuilder()
                .setTitle("Test Report")
                .setTimePeriod("Test Period")
                .addVehicleStatistic("Car", 8, 400.0, 4.0, 25.0)
                .addVehicleStatistic("Motorcycle", 2, 100.0, 2.5, 0.0)
                .build();

        assertEquals(2, report.getVehicleStatistics().size());
        assertEquals("Car", report.getVehicleStatistics().get(0).getVehicleType());
        assertEquals(8, report.getVehicleStatistics().get(0).getCount());
        assertEquals(400.0, report.getVehicleStatistics().get(0).getRevenue());
        assertEquals(4.0, report.getVehicleStatistics().get(0).getAverageDuration());
        assertEquals(25.0, report.getVehicleStatistics().get(0).getDisabledPercentage());

        assertEquals("Motorcycle", report.getVehicleStatistics().get(1).getVehicleType());
        assertEquals(2, report.getVehicleStatistics().get(1).getCount());
    }

    /**
     * Tests creating a report with additional information.
     */
    @Test
    public void testReportWithAdditionalInfo() {
        ParkingReport report = new ParkingReportBuilder()
                .setTitle("Test Report")
                .setTimePeriod("Test Period")
                .setAdditionalInfo("This is additional information for the report.")
                .build();

        assertEquals("This is additional information for the report.", report.getAdditionalInfo());
    }

    /**
     * Tests that the report generation produces a properly formatted string.
     */
    @Test
    public void testReportGeneration() {
        LocalDateTime now = LocalDateTime.now();

        ParkingReport report = new ParkingReportBuilder()
                .setTitle("Test Report")
                .setGeneratedTime(now)
                .setTimePeriod("Today")
                .setTotalEntries(10)
                .setTotalRevenue(500.0)
                .setCurrentOccupancy(50.0)
                .setAverageDuration(3.5)
                .addVehicleStatistic("Car", 8, 400.0, 4.0, 25.0)
                .addVehicleStatistic("Motorcycle", 2, 100.0, 2.5, 0.0)
                .build();

        String generatedReport = report.generateReport();

        assertTrue(generatedReport.contains("Test Report"));
        assertTrue(generatedReport.contains("Today"));
        assertTrue(generatedReport.contains("Total Entries: 10"));
        assertTrue(generatedReport.contains("Total Revenue: ₪500.00"));
        assertTrue(generatedReport.contains("Current Occupancy: 50.0%"));
        assertTrue(generatedReport.contains("Average Duration: 3.50 hours"));
        assertTrue(generatedReport.contains("Car: Count=8, Revenue=₪400.00"));
        assertTrue(generatedReport.contains("Motorcycle: Count=2, Revenue=₪100.00"));
    }

    /**
     * Tests building report with default values.
     */
    @Test
    public void testReportWithDefaults() {
        ParkingReport report = new ParkingReportBuilder().build();

        assertEquals("Parking Lot Report", report.getTitle());
        assertEquals("Today", report.getTimePeriod());
        assertEquals(0, report.getTotalEntries());
        assertEquals(0.0, report.getTotalRevenue());
        assertEquals(0.0, report.getCurrentOccupancy());
        assertEquals(0.0, report.getAverageDuration());
        assertTrue(report.getVehicleStatistics().isEmpty());
        assertEquals("", report.getAdditionalInfo());
    }

    /**
     * Tests method chaining in the builder.
     */
    @Test
    public void testMethodChaining() {
        ParkingReportBuilder builder = new ParkingReportBuilder();

        // All methods should return the same builder instance
        ParkingReportBuilder result = builder
                .setTitle("Chain Test")
                .setTimePeriod("Test Period")
                .setTotalEntries(5)
                .setTotalRevenue(100.0)
                .setCurrentOccupancy(25.0)
                .setAverageDuration(2.0)
                .addVehicleStatistic("Car", 5, 100.0, 2.0, 20.0)
                .setAdditionalInfo("Method chaining test");

        assertSame(builder, result);

        ParkingReport report = result.build();
        assertEquals("Chain Test", report.getTitle());
        assertEquals(5, report.getTotalEntries());
    }

    /**
     * Tests creating multiple reports from the same builder.
     */
    @Test
    public void testMultipleBuilds() {
        ParkingReportBuilder builder = new ParkingReportBuilder()
                .setTitle("Multi Build Test")
                .setTotalEntries(10);

        ParkingReport report1 = builder.build();
        ParkingReport report2 = builder.build();

        // Both reports should have the same data but be different instances
        assertNotSame(report1, report2);
        assertEquals(report1.getTitle(), report2.getTitle());
        assertEquals(report1.getTotalEntries(), report2.getTotalEntries());
    }

    /**
     * Tests report with edge case values.
     */
    @Test
    public void testReportWithEdgeCaseValues() {
        ParkingReport report = new ParkingReportBuilder()
                .setTitle("")
                .setTimePeriod("")
                .setTotalEntries(0)
                .setTotalRevenue(0.0)
                .setCurrentOccupancy(100.0)
                .setAverageDuration(0.0)
                .setAdditionalInfo(null)
                .build();

        assertEquals("", report.getTitle());
        assertEquals("", report.getTimePeriod());
        assertEquals(0, report.getTotalEntries());
        assertEquals(0.0, report.getTotalRevenue());
        assertEquals(100.0, report.getCurrentOccupancy());
        assertEquals(0.0, report.getAverageDuration());
        assertNull(report.getAdditionalInfo());
    }
}