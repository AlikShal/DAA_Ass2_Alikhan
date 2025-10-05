package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.LongAdder;

public class PerformanceTracker {
    private final LongAdder comparisons = new LongAdder();
    private final LongAdder assignments = new LongAdder();
    private long startTime;
    private long elapsedTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        elapsedTime = System.nanoTime() - startTime;
    }

    public void countComparison() {
        comparisons.increment();
    }

    public void countAssignment() {
        assignments.increment();
    }

    public long getTimeNs() {
        return elapsedTime;
    }

    public long getComparisons() {
        return comparisons.sum();
    }

    public long getAssignments() {
        return assignments.sum();
    }

    public void reset() {
        comparisons.reset();
        assignments.reset();
        startTime = 0;
        elapsedTime = 0;
    }

    public void writeToCSV(String filePath, String label, long result) {
        try (FileWriter fw = new FileWriter(filePath, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.printf("%s,%d,%d,%d,%d%n",
                    label, getTimeNs(), getComparisons(), getAssignments(), result);
        } catch (IOException e) {
            System.err.println("Error writing metrics: " + e.getMessage());
        }
    }
}