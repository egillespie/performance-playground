package playground;

/**
 * Contract for performing a single benchmark.
 *
 * @author egillespie
 */
public interface Benchmark {
    String describe();
    int size();
    void run() throws Exception;
}
