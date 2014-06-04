package playground.conversion;

import com.google.common.collect.ImmutableSet;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static playground.conversion.TestObject.NestedObject;
import static playground.conversion.TestObjectProtos.TestObjectProto;
import static playground.conversion.TestObjectProtos.TestObjectProto.NestedObjectProto;

/**
 * Translates between {@link TestObject} and {@link TestObjectProtos} protocol buffer objects.
 *
 * @author egillespie
 */
public final class TestObjectProtoTranslator {
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private TestObjectProtoTranslator() { }

    public static TestObjectProto translateToProto(TestObject testObject) {
        TestObjectProto.Builder builder =
                TestObjectProto.newBuilder()
                .setMyBoolean(testObject.isMyBoolean())
                .setMyInt(testObject.getMyInt())
                .setMyDouble(testObject.getMyDouble())
                .setMyLong(testObject.getMyLong())
                .setMyString(testObject.getMyString())
                .setMyLocalDate(testObject.getMyLocalDate().toString(LOCAL_DATE_FORMATTER))
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
                LocalDate.parse(testObjectProto.getMyLocalDate(), LOCAL_DATE_FORMATTER),
                translateFromProto(testObjectProto.getMyNestedObject()),
                nestedObjectBuilder.build());
    }

    private static NestedObjectProto translateToProto(NestedObject nestedObject) {
        return NestedObjectProto.newBuilder()
                .setDate0(nestedObject.getDate0().toString(LOCAL_DATE_FORMATTER))
                .setDate1(nestedObject.getDate1().toString(LOCAL_DATE_FORMATTER))
                .setDate2(nestedObject.getDate2().toString(LOCAL_DATE_FORMATTER))
                .setDate3(nestedObject.getDate3().toString(LOCAL_DATE_FORMATTER))
                .setDate4(nestedObject.getDate4().toString(LOCAL_DATE_FORMATTER))
                .setDate5(nestedObject.getDate5().toString(LOCAL_DATE_FORMATTER))
                .setDate6(nestedObject.getDate6().toString(LOCAL_DATE_FORMATTER))
                .setDate7(nestedObject.getDate7().toString(LOCAL_DATE_FORMATTER))
                .setDate8(nestedObject.getDate8().toString(LOCAL_DATE_FORMATTER))
                .setDate9(nestedObject.getDate9().toString(LOCAL_DATE_FORMATTER))
                .build();
    }

    private static NestedObject translateFromProto(NestedObjectProto nestedObjectProto) {
        return new NestedObject(
                LocalDate.parse(nestedObjectProto.getDate0(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate1(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate2(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate3(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate4(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate5(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate6(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate7(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate8(), LOCAL_DATE_FORMATTER),
                LocalDate.parse(nestedObjectProto.getDate9(), LOCAL_DATE_FORMATTER));
    }
}
