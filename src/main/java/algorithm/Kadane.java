package algorithm;

import metrics.PerformanceTracker;

public class Kadane {
    public static long maxSubArray(int[] arr, PerformanceTracker tracker) {
        if (arr == null || arr.length == 0) return 0;

        tracker.start();

        long meh = arr[0]; tracker.countAssignment(); // maxEndingHere
        long msf = arr[0]; tracker.countAssignment(); // maxSoFar

        for (int i = 1; i < arr.length; i++) {
            long ai = arr[i];
            long sum = meh + ai;

            tracker.countComparison();
            if (sum > ai) { meh = sum; tracker.countAssignment(); }
            else          { meh = ai;  tracker.countAssignment(); }

            tracker.countComparison();
            if (meh > msf) { msf = meh; tracker.countAssignment(); }
        }

        tracker.stop();
        return msf;
    }
}