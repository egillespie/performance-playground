package playground.conversion;

import com.google.common.collect.ImmutableSet;
import org.joda.time.Instant;

import static playground.conversion.TestObject.NestedObject;
import static playground.conversion.TestObjectProtos.TestObjectProto;
import static playground.conversion.TestObjectProtos.TestObjectProto.NestedObjectProto;

/**
 * Translates between {@link TestObject} and {@link TestObjectProtos} protocol buffer objects.
 *
 * @author egillespie
 */
public final class TestObjectProtoTranslator {
    private TestObjectProtoTranslator() { }

    public static TestObjectProto translateToProto(TestObject testObject) {
        TestObjectProto.Builder builder =
                TestObjectProto.newBuilder()
                .setMyBoolean(testObject.isMyBoolean())
                .setMyInt(testObject.getMyInt())
                .setMyDouble(testObject.getMyDouble())
                .setMyLong(testObject.getMyLong())
                .setMyString(testObject.getMyString())
                .setMyInstant(translateFromInstant(testObject.getMyInstant()))
                .setMyNestedObject(translateToProto(testObject.getMyNestedObject()));

        for (String string : testObject.getMyStringArray()) {
            builder.addMyStringArray(string);
        }

        for (NestedObject nestedObject : testObject.getMyNestedObjectSet()) {
            builder.addMyNestedObjectSet(translateToProto(nestedObject));
        }

        return builder.build();
    }

    public static TestObject translateFromProto(TestObjectProto testObjectProto) {
        ImmutableSet.Builder<NestedObject> nestedObjectBuilder = ImmutableSet.builder();
        for (int i = 0; i < testObjectProto.getMyNestedObjectSetCount(); i++) {
            nestedObjectBuilder.add(translateFromProto(testObjectProto.getMyNestedObjectSet(i)));
        }

        String[] myStringArray = new String[testObjectProto.getMyStringArrayCount()];
        for (int i = 0; i < testObjectProto.getMyStringArrayCount(); i++) {
            myStringArray[i] = testObjectProto.getMyStringArray(i);
        }

        return new TestObject(
                testObjectProto.getMyBoolean(),
                testObjectProto.getMyInt(),
                testObjectProto.getMyDouble(),
                testObjectProto.getMyLong(),
                testObjectProto.getMyString(),
                myStringArray,
                translateToInstant(testObjectProto.getMyInstant()),
                translateFromProto(testObjectProto.getMyNestedObject()),
                nestedObjectBuilder.build());
    }

    private static NestedObjectProto translateToProto(NestedObject nestedObject) {
        return NestedObjectProto.newBuilder()
                .setInstant0(translateFromInstant(nestedObject.getInstant0()))
                .setInstant1(translateFromInstant(nestedObject.getInstant1()))
                .setInstant2(translateFromInstant(nestedObject.getInstant2()))
                .setInstant3(translateFromInstant(nestedObject.getInstant3()))
                .setInstant4(translateFromInstant(nestedObject.getInstant4()))
                .setInstant5(translateFromInstant(nestedObject.getInstant5()))
                .setInstant6(translateFromInstant(nestedObject.getInstant6()))
                .setInstant7(translateFromInstant(nestedObject.getInstant7()))
                .setInstant8(translateFromInstant(nestedObject.getInstant8()))
                .setInstant9(translateFromInstant(nestedObject.getInstant9()))
                .build();
    }

    private static NestedObject translateFromProto(NestedObjectProto nestedObjectProto) {
        return new NestedObject(
                translateToInstant(nestedObjectProto.getInstant0()),
                translateToInstant(nestedObjectProto.getInstant1()),
                translateToInstant(nestedObjectProto.getInstant2()),
                translateToInstant(nestedObjectProto.getInstant3()),
                translateToInstant(nestedObjectProto.getInstant4()),
                translateToInstant(nestedObjectProto.getInstant5()),
                translateToInstant(nestedObjectProto.getInstant6()),
                translateToInstant(nestedObjectProto.getInstant7()),
                translateToInstant(nestedObjectProto.getInstant8()),
                translateToInstant(nestedObjectProto.getInstant9()));
    }

    private static Instant translateToInstant(long time) {
        return new Instant(time);
    }

    private static long translateFromInstant(Instant instant) {
        return instant.getMillis();
    }
}
