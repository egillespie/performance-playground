package playground.conversion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import org.joda.time.LocalDate;

import java.util.Arrays;

/**
 * Stores a lot of different data types in various structures to make sure a broad spectrum of capabilities for each
 * data conversion library is being utilized in benchmarks.
 *
 * @author egillespie
 */
public class TestObject {
    private final boolean myBoolean;
    private final int myInt;
    private final double myDouble;
    private final long myLong;
    private final String myString;
    private final String[] myStringArray;
    private final LocalDate myLocalDate;
    private final NestedObject myNestedObject;
    private final ImmutableSet<NestedObject> myNestedObjectSet;

    @JsonCreator
    public TestObject(@JsonProperty("myBoolean") boolean myBoolean,
                      @JsonProperty("myInt") int myInt,
                      @JsonProperty("myDouble") double myDouble,
                      @JsonProperty("myLong") long myLong,
                      @JsonProperty("myString") String myString,
                      @JsonProperty("myStringArray") String[] myStringArray,
                      @JsonProperty("myLocalDate") LocalDate myLocalDate,
                      @JsonProperty("myNestedObject") NestedObject myNestedObject,
                      @JsonProperty("myNestedObjectSet") ImmutableSet<NestedObject> myNestedObjectSet) {
        this.myBoolean = myBoolean;
        this.myInt = myInt;
        this.myDouble = myDouble;
        this.myLong = myLong;
        this.myString = myString;
        this.myStringArray = myStringArray;
        this.myLocalDate = myLocalDate;
        this.myNestedObject = myNestedObject;
        this.myNestedObjectSet = myNestedObjectSet;
    }

    public boolean isMyBoolean() {
        return myBoolean;
    }

    public int getMyInt() {
        return myInt;
    }

    public double getMyDouble() {
        return myDouble;
    }

    public long getMyLong() {
        return myLong;
    }

    public String getMyString() {
        return myString;
    }

    public String[] getMyStringArray() {
        return myStringArray;
    }

    public LocalDate getMyLocalDate() {
        return myLocalDate;
    }

    public NestedObject getMyNestedObject() {
        return myNestedObject;
    }

    public ImmutableSet<NestedObject> getMyNestedObjectSet() {
        return myNestedObjectSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestObject that = (TestObject) o;

        if (myBoolean != that.myBoolean) return false;
        if (Double.compare(that.myDouble, myDouble) != 0) return false;
        if (myInt != that.myInt) return false;
        if (myLong != that.myLong) return false;
        if (myLocalDate != null ? !myLocalDate.equals(that.myLocalDate) : that.myLocalDate != null) return false;
        if (myNestedObject != null ? !myNestedObject.equals(that.myNestedObject) : that.myNestedObject != null)
            return false;
        if (myNestedObjectSet != null ? !myNestedObjectSet.equals(that.myNestedObjectSet) : that.myNestedObjectSet != null)
            return false;
        if (myString != null ? !myString.equals(that.myString) : that.myString != null) return false;
        if (!Arrays.equals(myStringArray, that.myStringArray)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (myBoolean ? 1 : 0);
        result = 31 * result + myInt;
        temp = Double.doubleToLongBits(myDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (myLong ^ (myLong >>> 32));
        result = 31 * result + (myString != null ? myString.hashCode() : 0);
        result = 31 * result + (myStringArray != null ? Arrays.hashCode(myStringArray) : 0);
        result = 31 * result + (myLocalDate != null ? myLocalDate.hashCode() : 0);
        result = 31 * result + (myNestedObject != null ? myNestedObject.hashCode() : 0);
        result = 31 * result + (myNestedObjectSet != null ? myNestedObjectSet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("myBoolean", myBoolean)
                .add("myInt", myInt)
                .add("myDouble", myDouble)
                .add("myLong", myLong)
                .add("myString", myString)
                .add("myStringArray", String.format("[%s]", Joiner.on(',').join(myStringArray)))
                .add("myLocalDate", myLocalDate)
                .add("myNestedObject", myNestedObject)
                .add("myNestedObjectSet", myNestedObjectSet)
                .toString();
    }

    public static class NestedObject {
        private final LocalDate date0;
        private final LocalDate date1;
        private final LocalDate date2;
        private final LocalDate date3;
        private final LocalDate date4;
        private final LocalDate date5;
        private final LocalDate date6;
        private final LocalDate date7;
        private final LocalDate date8;
        private final LocalDate date9;

        @JsonCreator
        public NestedObject(@JsonProperty("date0") LocalDate date0,
                            @JsonProperty("date1") LocalDate date1,
                            @JsonProperty("date2") LocalDate date2,
                            @JsonProperty("date3") LocalDate date3,
                            @JsonProperty("date4") LocalDate date4,
                            @JsonProperty("date5") LocalDate date5,
                            @JsonProperty("date6") LocalDate date6,
                            @JsonProperty("date7") LocalDate date7,
                            @JsonProperty("date8") LocalDate date8,
                            @JsonProperty("date9") LocalDate date9) {
            this.date0 = date0;
            this.date1 = date1;
            this.date2 = date2;
            this.date3 = date3;
            this.date4 = date4;
            this.date5 = date5;
            this.date6 = date6;
            this.date7 = date7;
            this.date8 = date8;
            this.date9 = date9;
        }

        public LocalDate getDate0() {
            return date0;
        }

        public LocalDate getDate1() {
            return date1;
        }

        public LocalDate getDate2() {
            return date2;
        }

        public LocalDate getDate3() {
            return date3;
        }

        public LocalDate getDate4() {
            return date4;
        }

        public LocalDate getDate5() {
            return date5;
        }

        public LocalDate getDate6() {
            return date6;
        }

        public LocalDate getDate7() {
            return date7;
        }

        public LocalDate getDate8() {
            return date8;
        }

        public LocalDate getDate9() {
            return date9;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NestedObject that = (NestedObject) o;

            if (date0 != null ? !date0.equals(that.date0) : that.date0 != null) return false;
            if (date1 != null ? !date1.equals(that.date1) : that.date1 != null) return false;
            if (date2 != null ? !date2.equals(that.date2) : that.date2 != null) return false;
            if (date3 != null ? !date3.equals(that.date3) : that.date3 != null) return false;
            if (date4 != null ? !date4.equals(that.date4) : that.date4 != null) return false;
            if (date5 != null ? !date5.equals(that.date5) : that.date5 != null) return false;
            if (date6 != null ? !date6.equals(that.date6) : that.date6 != null) return false;
            if (date7 != null ? !date7.equals(that.date7) : that.date7 != null) return false;
            if (date8 != null ? !date8.equals(that.date8) : that.date8 != null) return false;
            if (date9 != null ? !date9.equals(that.date9) : that.date9 != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = date0 != null ? date0.hashCode() : 0;
            result = 31 * result + (date1 != null ? date1.hashCode() : 0);
            result = 31 * result + (date2 != null ? date2.hashCode() : 0);
            result = 31 * result + (date3 != null ? date3.hashCode() : 0);
            result = 31 * result + (date4 != null ? date4.hashCode() : 0);
            result = 31 * result + (date5 != null ? date5.hashCode() : 0);
            result = 31 * result + (date6 != null ? date6.hashCode() : 0);
            result = 31 * result + (date7 != null ? date7.hashCode() : 0);
            result = 31 * result + (date8 != null ? date8.hashCode() : 0);
            result = 31 * result + (date9 != null ? date9.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("date0", date0)
                    .add("date1", date1)
                    .add("date2", date2)
                    .add("date3", date3)
                    .add("date4", date4)
                    .add("date5", date5)
                    .add("date6", date6)
                    .add("date7", date7)
                    .add("date8", date8)
                    .add("date9", date9)
                    .toString();
        }
    }
}
