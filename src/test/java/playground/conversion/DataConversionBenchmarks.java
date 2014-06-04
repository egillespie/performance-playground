package playground.conversion;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.assertEquals;
import static playground.conversion.TestObjectProtos.TestObjectProto;

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
    public void testJacksonSerializationWorks() throws Exception {
        BenchmarkedObjectMapper objectMapper = new BenchmarkedObjectMapper();
        TestObject original = TestObjectFactory.newTestObject();
        TestObject recomposed = objectMapper.readValue(objectMapper.writeValueAsString(original), TestObject.class);
        assertEquals("Jackson Serialization and Deserialization is broken.", original, recomposed);
    }

    @Test
    public void benchmarkJacksonDataSizes() throws Exception {
        byte[] bytes = new BenchmarkedObjectMapper().writeValueAsBytes(TestObjectFactory.newTestObject());
        System.out.println(String.format("Jackson Serialization Data Sizes: [ uncompressed bytes = %d, gzip compressed bytes = %d ]", bytes.length, gzipCompress(bytes).length));
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

        String[] jsonObjects = new String[OBJECT_COUNT_PER_BATCH];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            jsonObjects[i] = objectMapper.writeValueAsString(BENCHMARK_OBJECTS[i]);
        }

        Stopwatch stopwatch = Stopwatch.createUnstarted();
        for (int i = 0; i < TOTAL_BATCHES; i++) {
            stopwatch.start();
            for (String json : jsonObjects) {
                objectMapper.readValue(json, TestObject.class);
            }
            stopwatch.stop();
        }

        System.out.println(String.format("Jackson Deserialization Benchmark: [ batches = %d, objects = %d, average ms = %d ]",
                TOTAL_BATCHES, OBJECT_COUNT_PER_BATCH, stopwatch.elapsed(TimeUnit.MILLISECONDS) / TOTAL_BATCHES));
    }

    @Test
    public void testGoogleProtobufSerializationWorks() throws Exception {
        TestObject original = TestObjectFactory.newTestObject();
        TestObject recomposed = TestObjectProtoTranslator.translateFromProto(TestObjectProto.parseFrom(TestObjectProtoTranslator.translateToProto(original).toByteArray()));
        assertEquals("Google Protobuf Serialization and Deserialization is broken.", original, recomposed);
    }

    @Test
    public void benchmarkGoogleProtobufDataSizes() throws Exception {
        byte[] bytes = TestObjectProtoTranslator.translateToProto(TestObjectFactory.newTestObject()).toByteArray();
        System.out.println(String.format("Google Protobuf Data Sizes: [ uncompressed bytes = %d, gzip compressed bytes = %d ]", bytes.length, gzipCompress(bytes).length));
    }

    @Test
    public void benchmarkGoogleProtobufSerialization() throws Exception {
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        for (int i = 0; i < TOTAL_BATCHES; i++) {
            stopwatch.start();
            for (TestObject object : BENCHMARK_OBJECTS) {
                TestObjectProtoTranslator.translateToProto(object).toByteArray();
            }
            stopwatch.stop();
        }

        System.out.println(String.format("Google Protobuf Serialization Benchmark: [ batches = %d, objects = %d, average ms = %d ]",
                TOTAL_BATCHES, OBJECT_COUNT_PER_BATCH, stopwatch.elapsed(TimeUnit.MILLISECONDS) / TOTAL_BATCHES));
    }

    @Test
    public void benchmarkGoogleProtobufDeserialization() throws Exception {
        byte[][] byteArrayObjects = new byte[OBJECT_COUNT_PER_BATCH][];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            byteArrayObjects[i] = TestObjectProtoTranslator.translateToProto(BENCHMARK_OBJECTS[i]).toByteArray();
        }

        Stopwatch stopwatch = Stopwatch.createUnstarted();
        for (int i = 0; i < TOTAL_BATCHES; i++) {
            stopwatch.start();
            for (byte[] byteArray : byteArrayObjects) {
                TestObjectProtoTranslator.translateFromProto(TestObjectProto.parseFrom(byteArray));
            }
            stopwatch.stop();
        }

        System.out.println(String.format("Google Protobuf Deserialization Benchmark: [ batches = %d, objects = %d, average ms = %d ]",
                TOTAL_BATCHES, OBJECT_COUNT_PER_BATCH, stopwatch.elapsed(TimeUnit.MILLISECONDS) / TOTAL_BATCHES));
    }

    private byte[] gzipCompress(byte[] bytes) throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bytes.length);
        GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
        zipStream.write(bytes);
        zipStream.close();
        byteStream.close();
        return byteStream.toByteArray();
    }
}
