package playground.conversion;

import com.google.common.collect.ImmutableSet;
import org.joda.time.LocalDate;

import java.util.Random;

import static playground.conversion.TestObject.NestedObject;

/**
 * Creates new instances of {@link TestObject} pre-loaded with random data.
 *
 * @author egillespie
 */
public final class TestObjectFactory {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private TestObjectFactory() { }

    public static TestObject newTestObject() {
        return new TestObject(
                RANDOM.nextBoolean(),
                RANDOM.nextInt(),
                RANDOM.nextDouble(),
                RANDOM.nextLong(),
                nextRandomString(),
                nextRandomStringArray(),
                nextRandomLocalDate(),
                newNestedObject(),
                nextRandomNestedObjectSet());

    }

    private static NestedObject newNestedObject() {
        return new NestedObject(
                nextRandomLocalDate(),
                nextRandomLocalDate(),
                nextRandomLocalDate(),
                nextRandomLocalDate(),
                nextRandomLocalDate(),
                nextRandomLocalDate(),
                nextRandomLocalDate(),
                nextRandomLocalDate(),
                nextRandomLocalDate(),
                nextRandomLocalDate());
    }

    private static String nextRandomString() {
        return String.valueOf(RANDOM.nextFloat());
    }

    private static String[] nextRandomStringArray() {
        String[] stringArray = new String[100];
        for (int i = 0; i < 100; i++) {
            stringArray[i] = nextRandomString();
        }
        return stringArray;
    }

    private static LocalDate nextRandomLocalDate() {
        return new LocalDate(RANDOM.nextLong());
    }

    private static ImmutableSet<NestedObject> nextRandomNestedObjectSet() {
        ImmutableSet.Builder<NestedObject> builder = ImmutableSet.builder();
        for (int i = 0; i < 100; i++) {
            builder.add(newNestedObject());
        }
        return builder.build();
    }
}
