package playground;

/**
 * A benchmark that assumes a constant description and size.
 *
 * @author egillespie
 */
public abstract class BasicBenchmark implements Benchmark {
    private final String description;
    private final int size;

    public BasicBenchmark(String description, int size) {
        this.description = description;
        this.size = size;
    }

    @Override
    public String describe() {
        return description;
    }

    @Override
    public int size() {
        return size;
    }

    public abstract void run() throws Exception;
}
