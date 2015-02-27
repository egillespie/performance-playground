package playground;

import com.google.common.base.Stopwatch;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * Runs a {@link Benchmark} and outputs data about its execution.
 *
 * @author egillespie
 */
public class BenchmarkRunner {
    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###");

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
        printBenchmarkResult(benchmark, min, max, average);
    }

    private void printBenchmarkResult(Benchmark benchmark, long min, long max, long average) {
        // | Jackson JSON 2.5.1 | 1,559 | 1,358 | 2,330 | -0.13% / +0.49% |
        System.out.println(String.format("| %s | %s | %s | %s | -%.2f%% / +%.2f%% |",
                benchmark.describe(),
                FORMATTER.format(average),
                FORMATTER.format(min),
                FORMATTER.format(max),
                ((double) average - (double) min) / (double) average,
                ((double) max - (double) average) / (double) average));
    }
}
