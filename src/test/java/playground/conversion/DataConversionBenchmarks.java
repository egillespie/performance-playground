package playground.conversion;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeUnit;

/**
 * Benchmarks data conversion frameworks.
 *
 * @author egillespie
 */
@RunWith(JUnit4.class)
public class DataConversionBenchmarks {
    private static final int TOTAL_BATCHES = 100;
    private static final int OBJECT_COUNT_PER_BATCH = 1000;

    private static final TestObject[] BENCHMARK_OBJECTS;
    static {
        BENCHMARK_OBJECTS = new TestObject[OBJECT_COUNT_PER_BATCH];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            BENCHMARK_OBJECTS[i] = TestObjectFactory.newTestObject();
        }
    }

    @Test
    public void benchmarkJacksonSerialization() throws Exception {
        BenchmarkedObjectMapper objectMapper = new BenchmarkedObjectMapper();

        Stopwatch stopwatch = Stopwatch.createUnstarted();
        for (int i = 0; i < TOTAL_BATCHES; i++) {
            stopwatch.start();
            for (TestObject object : BENCHMARK_OBJECTS) {
                objectMapper.writeValueAsString(object);
            }
            stopwatch.stop();
        }

        System.out.println(String.format("Jackson Serialization Benchmark: [ batches = %d, objects = %d, average ms = %d ]",
                TOTAL_BATCHES, OBJECT_COUNT_PER_BATCH, stopwatch.elapsed(TimeUnit.MILLISECONDS) / TOTAL_BATCHES));
    }

    @Test
    public void benchmarkJacksonDeserialization() throws Exception {
        BenchmarkedObjectMapper objectMapper = new BenchmarkedObjectMapper();

        String[] JSON_OBJECTS = new String[OBJECT_COUNT_PER_BATCH];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            JSON_OBJECTS[i] = objectMapper.writeValueAsString(BENCHMARK_OBJECTS[i]);
        }

        Stopwatch stopwatch = Stopwatch.createUnstarted();
        for (int i = 0; i < TOTAL_BATCHES; i++) {
            stopwatch.start();
            for (String json : JSON_OBJECTS) {
                objectMapper.readValue(json, TestObject.class);
            }
            stopwatch.stop();
        }

        System.out.println(String.format("Jackson Deserialization Benchmark: [ batches = %d, objects = %d, average ms = %d ]",
                TOTAL_BATCHES, OBJECT_COUNT_PER_BATCH, stopwatch.elapsed(TimeUnit.MILLISECONDS) / TOTAL_BATCHES));
    }
}
