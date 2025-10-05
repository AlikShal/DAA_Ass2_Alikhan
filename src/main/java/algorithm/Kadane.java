package algorithm;

import metrics.PerformanceTracker;

public class Kadane {
    // Returns maximum subarray sum using Kadane’s Algorithm
    public static long maxSubArray(int[] arr, PerformanceTracker tracker) {
        if (arr == null || arr.length == 0) return 0;

        tracker.start();

        long maxEndingHere = arr[0];
        long maxSoFar = arr[0];
        tracker.countAssignment();
        tracker.countAssignment();

        for (int i = 1; i < arr.length; i++) {
            long extend = maxEndingHere + arr[i];
            tracker.countAssignment();

            tracker.countComparison();
            if (extend > arr[i]) {
                maxEndingHere = extend;
            } else {
                maxEndingHere = arr[i];
            }
            tracker.countAssignment();

            tracker.countComparison();
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                tracker.countAssignment();
            }
        }

        tracker.stop();
        return maxSoFar;
    }
}