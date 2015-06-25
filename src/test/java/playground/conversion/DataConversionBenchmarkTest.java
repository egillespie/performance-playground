package playground.conversion;

import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import playground.BasicBenchmark;
import playground.BenchmarkRunner;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.assertEquals;
import static playground.conversion.TestObjectProtos.TestObjectProto;

/**
 * Benchmarks data conversion frameworks.
 *
 * @author egillespie
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataConversionBenchmarkTest {
    private static final String JACKSON_TOOL = "Jackson JSON 2.6.0-rc2";
    private static final String AFTERBURNER_TOOL = "Jackson Afterburner JSON 2.6.0-rc2";
    private static final String PROTOBUF_TOOL = "Protocol Buffers 3.0.0-alpha-3";

    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###");

    private static final BenchmarkRunner RUNNER = new BenchmarkRunner(100);

    private static final int OBJECT_COUNT_PER_BATCH = 1000;

    private static final TestObject[] BENCHMARK_OBJECTS;
    static {
        BENCHMARK_OBJECTS = new TestObject[OBJECT_COUNT_PER_BATCH];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            BENCHMARK_OBJECTS[i] = TestObjectFactory.newTestObject();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //
    // A. Make sure all of the frameworks function correctly.
    //
    //------------------------------------------------------------------------------------------------------------------

    @Test
    public void A01_testJacksonSerializationWorks() throws Exception {
        StandardObjectMapper objectMapper = new StandardObjectMapper();

        TestObject original = TestObjectFactory.newTestObject();
        TestObject recomposed = objectMapper.readValue(objectMapper.writeValueAsString(original), TestObject.class);
        assertEquals("Jackson Serialization and Deserialization is broken.", original, recomposed);
    }

    @Test
    public void A02_testJacksonAfterburnerSerializationWorks() throws Exception {
        StandardObjectMapper objectMapper = new StandardObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());

        TestObject original = TestObjectFactory.newTestObject();
        TestObject recomposed = objectMapper.readValue(objectMapper.writeValueAsString(original), TestObject.class);
        assertEquals("Jackson Afterburner Serialization and Deserialization is broken.", original, recomposed);
    }

    @Test
    public void A03_testGoogleProtobufSerializationWorks() throws Exception {
        TestObject original = TestObjectFactory.newTestObject();
        TestObject recomposed = TestObjectProtoTranslator.translateFromProto(TestObjectProto.parseFrom(TestObjectProtoTranslator.translateToProto(original).toByteArray()));
        assertEquals("Google Protobuf Serialization and Deserialization is broken.", original, recomposed);
    }

    //------------------------------------------------------------------------------------------------------------------
    //
    // B. Run all serialization benchmarks.
    //
    //------------------------------------------------------------------------------------------------------------------

    @Test
    public void B00_printTableHeader() {
        System.out.println("");
        System.out.println("| Framework | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |");
        System.out.println("| :-------- | ----------------: | -------------: | -------------: | :------- |");
    }

    @Test
    public void B01_benchmarkJacksonSerialization() throws Exception {
        final StandardObjectMapper objectMapper = new StandardObjectMapper();

        RUNNER.run(new BasicBenchmark(JACKSON_TOOL, OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (TestObject object : BENCHMARK_OBJECTS) {
                    objectMapper.writeValueAsString(object);
                }
            }
        });
    }

    @Test
    public void B02_benchmarkJacksonAfterburnerSerialization() throws Exception {
        final StandardObjectMapper objectMapper = new StandardObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());

        RUNNER.run(new BasicBenchmark(AFTERBURNER_TOOL, OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (TestObject object : BENCHMARK_OBJECTS) {
                    objectMapper.writeValueAsString(object);
                }
            }
        });
    }

    @Test
    public void B03_benchmarkGoogleProtobufSerialization() throws Exception {
        RUNNER.run(new BasicBenchmark(PROTOBUF_TOOL, OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (TestObject object : BENCHMARK_OBJECTS) {
                    TestObjectProtoTranslator.translateToProto(object).toByteArray();
                }
            }
        });
    }

    @Test
    public void B99_printTableFooter() {
        System.out.println("");
    }

    //------------------------------------------------------------------------------------------------------------------
    //
    // C. Run all deserialization benchmarks.
    //
    //------------------------------------------------------------------------------------------------------------------

    @Test
    public void C00_printTableHeader() {
        System.out.println("| Framework | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |");
        System.out.println("| :-------- | ----------------: | -------------: | -------------: | :------- |");
    }

    @Test
    public void C01_benchmarkJacksonDeserialization() throws Exception {
        final StandardObjectMapper objectMapper = new StandardObjectMapper();

        final String[] jsonObjects = new String[OBJECT_COUNT_PER_BATCH];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            jsonObjects[i] = objectMapper.writeValueAsString(BENCHMARK_OBJECTS[i]);
        }

        RUNNER.run(new BasicBenchmark(JACKSON_TOOL, OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (String json : jsonObjects) {
                    objectMapper.readValue(json, TestObject.class);
                }
            }
        });
    }

    @Test
    public void C02_benchmarkJacksonAfterburnerDeserialization() throws Exception {
        final StandardObjectMapper objectMapper = new StandardObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());

        final String[] jsonObjects = new String[OBJECT_COUNT_PER_BATCH];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            jsonObjects[i] = objectMapper.writeValueAsString(BENCHMARK_OBJECTS[i]);
        }

        RUNNER.run(new BasicBenchmark(AFTERBURNER_TOOL, OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (String json : jsonObjects) {
                    objectMapper.readValue(json, TestObject.class);
                }
            }
        });
    }

    @Test
    public void C03_benchmarkGoogleProtobufDeserialization() throws Exception {
        final byte[][] byteArrayObjects = new byte[OBJECT_COUNT_PER_BATCH][];
        for (int i = 0; i < OBJECT_COUNT_PER_BATCH; i++) {
            byteArrayObjects[i] = TestObjectProtoTranslator.translateToProto(BENCHMARK_OBJECTS[i]).toByteArray();
        }

        RUNNER.run(new BasicBenchmark(PROTOBUF_TOOL, OBJECT_COUNT_PER_BATCH) {
            @Override
            public void run() throws Exception {
                for (byte[] byteArray : byteArrayObjects) {
                    TestObjectProtoTranslator.translateFromProto(TestObjectProto.parseFrom(byteArray));
                }
            }
        });
    }

    @Test
    public void C99_printTableFooter() {
        System.out.println("");
    }

    //------------------------------------------------------------------------------------------------------------------
    //
    // D. Run all compression benchmarks.
    //
    //------------------------------------------------------------------------------------------------------------------

    @Test
    public void D00_printTableHeader() {
        System.out.println("| Framework | Uncompressed Size (bytes) | GZip Compressed Size (bytes) | Compression Ratio |");
        System.out.println("| :-------- | ------------------------: | ---------------------------: | ----------------: |");
    }

    @Test
    public void D01_benchmarkJacksonDataSizes() throws Exception {
        StandardObjectMapper objectMapper = new StandardObjectMapper();

        byte[] bytes = objectMapper.writeValueAsBytes(TestObjectFactory.newTestObject());
        byte[] compressedBytes = gzipCompress(bytes);
        printCompressionResults(JACKSON_TOOL, bytes, compressedBytes);
    }

    @Test
    public void D02_benchmarkJacksonAfterburnerDataSizes() throws Exception {
        StandardObjectMapper objectMapper = new StandardObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());

        byte[] bytes = objectMapper.writeValueAsBytes(TestObjectFactory.newTestObject());
        byte[] compressedBytes = gzipCompress(bytes);
        printCompressionResults(AFTERBURNER_TOOL, bytes, compressedBytes);
    }

    @Test
    public void D03_benchmarkGoogleProtobufDataSizes() throws Exception {
        byte[] bytes = TestObjectProtoTranslator.translateToProto(TestObjectFactory.newTestObject()).toByteArray();
        byte[] compressedBytes = gzipCompress(bytes);
        printCompressionResults(PROTOBUF_TOOL, bytes, compressedBytes);
    }

    @Test
    public void D99_printTableFooter() {
        System.out.println("");
    }



    private byte[] gzipCompress(byte[] bytes) throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bytes.length);
        GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
        zipStream.write(bytes);
        zipStream.close();
        byteStream.close();
        return byteStream.toByteArray();
    }

    private void printCompressionResults(String tool, byte[] bytes, byte[] compressedBytes) {
        // | Jackson JSON 2.5.1 | 28,082 | 9,736 | 2.88 |
        System.out.println(String.format("| %s | %s | %s | %.2f |",
                tool,
                FORMATTER.format(bytes.length),
                FORMATTER.format(compressedBytes.length),
                (double) bytes.length / (double) compressedBytes.length));
    }
}
