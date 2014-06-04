package playground;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * Runs a {@link Benchmark} and outputs data about its execution.
 *
 * @author egillespie
 */
public class BenchmarkRunner {
    private final int totalBatches;

    public BenchmarkRunner(int totalBatches) {
        this.totalBatches = totalBatches;
    }

    public void run(Benchmark benchmark) throws Exception {
        long min = 0, max = 0, total = 0;
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        for (int i = 0; i < totalBatches; i++) {
            stopwatch.reset().start();
            benchmark.run();
            stopwatch.stop();
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            total += elapsed;
            if (min == 0 || elapsed < min) {
                min = elapsed;
            }
            if (max == 0 || elapsed > max) {
                max = elapsed;
            }
        }

        long average = total / totalBatches;
        System.out.println(String.format("%s: [ total benchmarks = %d, benchmark size = %d, average ms = %d, min ms = %d (%.2f%%), max ms = %d (%.2f%%) ]",
                benchmark.describe(), totalBatches, benchmark.size(), average,
                min, ((double) average - (double) min) / (double) average,
                max, ((double) max - (double) average) / (double) average));
    }
}
