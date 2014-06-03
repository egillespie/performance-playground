package playground.serialization.jackson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import playground.serialization.TestObjectFactory;

/**
 * Benchmarks serialization and deserialization of {@link playground.serialization.TestObject}s using the Jackson framework.
 *
 * @author egillespie
 */
@RunWith(JUnit4.class)
public class JacksonSerializationBenchmark {
    @Test
    public void alwaysPasses() {
        System.out.println(TestObjectFactory.newTestObject());
    }
}
