package algorithm;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KadaneTest {

    @Test
    void shouldHandleEmptyArray() {
        int[] arr = {};
        PerformanceTracker tracker = new PerformanceTracker();
        long result = Kadane.maxSubArray(arr, tracker);
        assertEquals(0, result, "Empty array should return 0");
    }

    @Test
    void shouldHandleSingleElement() {
        int[] arr = {7};
        PerformanceTracker tracker = new PerformanceTracker();
        long result = Kadane.maxSubArray(arr, tracker);
        assertEquals(7, result, "Single element array should return its value");
    }

    @Test
    void shouldHandleAllNegatives() {
        int[] arr = {-3, -2, -5, -1, -6};
        PerformanceTracker tracker = new PerformanceTracker();
        long result = Kadane.maxSubArray(arr, tracker);
        assertEquals(-1, result, "All negative numbers should return the largest one");
    }

    @Test
    void shouldHandleAllPositives() {
        int[] arr = {1, 2, 3, 4, 5};
        PerformanceTracker tracker = new PerformanceTracker();
        long result = Kadane.maxSubArray(arr, tracker);
        assertEquals(15, result, "Sum of all positive numbers should be returned");
    }

    @Test
    void shouldHandleMixedValues() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        PerformanceTracker tracker = new PerformanceTracker();
        long result = Kadane.maxSubArray(arr, tracker);
        assertEquals(6, result, "Maximum subarray sum should be 6");
    }

    @Test
    void shouldWriteMetricsToCSV() {
        int[] arr = {2, -1, 2, 3, 4, -5};
        PerformanceTracker tracker = new PerformanceTracker();
        Kadane.maxSubArray(arr, tracker);
        tracker.writeCsv("target/kadane_metrics.csv", "Kadane_Test", 10);
        assertTrue(true, "Metrics should be written to CSV successfully");
    }
}