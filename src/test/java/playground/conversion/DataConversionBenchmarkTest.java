package playground.conversion;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import playground.BasicBenchmark;
import playground.BenchmarkRunner;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.assertEquals;
import static playground.conversion.TestObjectProtos.TestObjectProto;

/**
 * Benchmarks data conversion frameworks.
 *
 * @author egillespie
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.JVM)
public class DataConversionBenchmarkTest {
    private static final BenchmarkRunner RUNNER = new BenchmarkRunner(100);

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
        StandardObjectMapper objectMapper = new StandardObjectMapper();
        TestObject original = TestObjectFactory.newTestObject();
        TestObject recomposed = objectMapper.readValue(objectMapper.writeValueAsString(original), TestObject.class);
        assertEquals("Jackson Serialization and Deserialization is broken.", original, recomposed);
    }

    @Test
    public void benchmarkJacksonDataSizes() throws Exception {
        byte[] bytes = new StandardObjectMapper().writeValueAsBytes(TestObjectFactory.newTestObject());
        byte[] compressedBytes = gzipCompress(bytes);
        System.out.println(String.format("Jackson Serialization Data Sizes: [ uncompressed bytes = %d, gzip compressed bytes = %d, compression ratio = %.2f]",
                bytes.length, compressedBytes.length, (double) bytes.length / (double) compressedBytes.length));
    }

    @Test
    public void benchmarkJacksonSerialization() throws Exception {
        final StandardObjectMapper objectMapper = new StandardObjectMapper();

        RUNNER.run(new BasicBenchmark("Jackson Serialization Benchmark", OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (TestObject object : BENCHMARK_OBJECTS) {
                    objectMapper.writeValueAsString(object);
                }
            }
        });
    }

    @Test
    public void benchmarkJacksonDeserialization() throws Exception {
        final StandardObjectMapper objectMapper = new StandardObjectMapper();

        final String[] jsonObjects = new String[OBJECT_COUNT_PER_BATCH];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            jsonObjects[i] = objectMapper.writeValueAsString(BENCHMARK_OBJECTS[i]);
        }

        RUNNER.run(new BasicBenchmark("Jackson Deserialization Benchmark", OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (String json : jsonObjects) {
                    objectMapper.readValue(json, TestObject.class);
                }
            }
        });
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
        byte[] compressedBytes = gzipCompress(bytes);
        System.out.println(String.format("Google Protobuf Data Sizes: [ uncompressed bytes = %d, gzip compressed bytes = %d, compression ratio = %.2f]",
                bytes.length, compressedBytes.length, (double) bytes.length / (double) compressedBytes.length));
    }

    @Test
    public void benchmarkGoogleProtobufSerialization() throws Exception {
        RUNNER.run(new BasicBenchmark("Google Protobuf Serialization Benchmark", OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (TestObject object : BENCHMARK_OBJECTS) {
                    TestObjectProtoTranslator.translateToProto(object).toByteArray();
                }
            }
        });
    }

    @Test
    public void benchmarkGoogleProtobufDeserialization() throws Exception {
        final byte[][] byteArrayObjects = new byte[OBJECT_COUNT_PER_BATCH][];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            byteArrayObjects[i] = TestObjectProtoTranslator.translateToProto(BENCHMARK_OBJECTS[i]).toByteArray();
        }

        RUNNER.run(new BasicBenchmark("Google Protobuf Deserialization Benchmark", OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (byte[] byteArray : byteArrayObjects) {
                    TestObjectProtoTranslator.translateFromProto(TestObjectProto.parseFrom(byteArray));
                }
            }
        });
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
