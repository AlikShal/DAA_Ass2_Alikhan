
package cli;

import algorithm.Kadane;
import metrics.PerformanceTracker;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        Map<String, String> opt = parse(args);
        int trials = Integer.parseInt(opt.getOrDefault("trials", "5"));
        String csv = opt.getOrDefault("csv", "target/kadane_metrics.csv");
        long seed = Long.parseLong(opt.getOrDefault("seed", "42"));
        String dist = opt.getOrDefault("dist", "random"); // random|reverse|nearly

        int[] sizes = Arrays.stream(opt.getOrDefault("n", "100,1000,10000,100000")
                .split(",")).map(String::trim).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();

        ensureHeader(csv);
        Random rnd = new Random(seed);

        for (int n : sizes) {
            for (int t = 1; t <= trials; t++) {
                int[] a = makeArray(n, dist, rnd);

                PerformanceTracker tracker = new PerformanceTracker();
                long result = Kadane.maxSubArray(a, tracker);

                write(csv, "Kadane", "-", n, tracker.getTimeNs(),
                        tracker.getComparisons(), tracker.getAssignments(), 0, t, result);
            }
        }
        System.out.println("Done → " + csv);
    }

    private static int[] makeArray(int n, String dist, Random rnd) {
        int[] a = rnd.ints(n, -1_000, 1_000).toArray(); // negatives allowed
        switch (dist.toLowerCase()) {
            case "reverse":
                Arrays.sort(a);
                reverse(a);
                break;
            case "nearly":
                Arrays.sort(a);
                int swaps = Math.max(1, n / 100);
                for (int i = 0; i < swaps; i++) {
                    int i1 = rnd.nextInt(n), i2 = rnd.nextInt(n);
                    int tmp = a[i1]; a[i1] = a[i2]; a[i2] = tmp;
                }
                break;
            case "random":
            default:
                // already random
        }
        return a;
    }

    private static void reverse(int[] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }

    private static void ensureHeader(String path) throws Exception {
        File f = new File(path);
        if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
        if (!f.exists()) {
            try (PrintWriter out = new PrintWriter(new FileWriter(f, true))) {
                out.println("algorithm,gap_seq,n,time_ns,comparisons,assignments,maxDepth,trial,result");
            }
        }
    }

    private static void write(String path, String algo, String seq, int n, long timeNs,
                              long cmp, long asn, long depth, int trial, long result) throws Exception {
        try (PrintWriter out = new PrintWriter(new FileWriter(path, true))) {
            out.printf("%s,%s,%d,%d,%d,%d,%d,%d,%d%n",
                    algo, seq, n, timeNs, cmp, asn, depth, trial, result);
        }
    }

    private static Map<String, String> parse(String[] args) {
        Map<String, String> m = new HashMap<>();
        for (String a : args) {
            String s = a.replaceFirst("^--", "");
            int k = s.indexOf('=');
            if (k > 0) m.put(s.substring(0, k).toLowerCase(), s.substring(k + 1));
        }
        return m;
    }
}