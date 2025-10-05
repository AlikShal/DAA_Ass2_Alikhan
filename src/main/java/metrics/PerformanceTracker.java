// src/main/java/metrics/PerformanceTracker.java
package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class PerformanceTracker {
    private long comparisons, assignments;
    private long startNs, elapsedNs;

    // timing
    public void start() { startNs = System.nanoTime(); }
    public void stop()  { elapsedNs = System.nanoTime() - startNs; }

    // counters
    public void countComparison() { comparisons++; }
    public void countAssignment() { assignments++; }

    // getters
    public long getTimeNs()      { return elapsedNs; }
    public long getComparisons() { return comparisons; }
    public long getAssignments() { return assignments; }
    public long getMaxDepth()    { return 0; } // iterative algorithm

    // optional alias for compatibility with older runners
    public long getSwapsOrAllocations() { return getAssignments(); }

    // lifecycle
    public void reset() { comparisons = assignments = startNs = elapsedNs = 0; }

    // CSV helper (optional)
    public void writeCsv(String path, String label, long n) {
        try {
            Path p = Path.of(path);
            if (p.getParent() != null) Files.createDirectories(p.getParent());
            boolean newFile = Files.notExists(p);
            try (PrintWriter out = new PrintWriter(new FileWriter(p.toFile(), true))) {
                if (newFile) out.println("algorithm,n,time_ns,comparisons,assignments,maxDepth,trial,result");
                out.printf("%s,%d,%d,%d,%d,%d,%d,%d%n",
                        label, n, getTimeNs(), getComparisons(), getAssignments(), getMaxDepth());
            }
        } catch (IOException e) {
            System.err.println("CSV write failed: " + e.getMessage());
        }
    }
}