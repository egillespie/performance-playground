package playground.conversion;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import org.joda.time.Instant;

import java.util.Arrays;

/**
 * Stores a lot of different data types in various structures to make sure a broad spectrum of capabilities for each
 * data conversion library is being utilized in benchmarks.
 *
 * @author egillespie
 */
public class TestObject {
    private boolean myBoolean;
    private int myInt;
    private double myDouble;
    private long myLong;
    private String myString;
    private String[] myStringArray;
    private Instant myInstant;
    private NestedObject myNestedObject;
    private ImmutableSet<NestedObject> myNestedObjectSet;

    public TestObject() { }

    public TestObject(boolean myBoolean,
                      int myInt,
                      double myDouble,
                      long myLong,
                      String myString,
                      String[] myStringArray,
                      Instant myInstant,
                      NestedObject myNestedObject,
                      ImmutableSet<NestedObject> myNestedObjectSet) {
        this.myBoolean = myBoolean;
        this.myInt = myInt;
        this.myDouble = myDouble;
        this.myLong = myLong;
        this.myString = myString;
        this.myStringArray = myStringArray;
        this.myInstant = myInstant;
        this.myNestedObject = myNestedObject;
        this.myNestedObjectSet = myNestedObjectSet;
    }

    public boolean isMyBoolean() {
        return myBoolean;
    }

    public void setMyBoolean(boolean myBoolean) {
        this.myBoolean = myBoolean;
    }

    public int getMyInt() {
        return myInt;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    public double getMyDouble() {
        return myDouble;
    }

    public void setMyDouble(double myDouble) {
        this.myDouble = myDouble;
    }

    public long getMyLong() {
        return myLong;
    }

    public void setMyLong(long myLong) {
        this.myLong = myLong;
    }

    public String getMyString() {
        return myString;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }

    public String[] getMyStringArray() {
        return myStringArray;
    }

    public void setMyStringArray(String[] myStringArray) {
        this.myStringArray = myStringArray;
    }

    public Instant getMyInstant() {
        return myInstant;
    }

    public void setMyInstant(Instant myInstant) {
        this.myInstant = myInstant;
    }

    public NestedObject getMyNestedObject() {
        return myNestedObject;
    }

    public void setMyNestedObject(NestedObject myNestedObject) {
        this.myNestedObject = myNestedObject;
    }

    public ImmutableSet<NestedObject> getMyNestedObjectSet() {
        return myNestedObjectSet;
    }

    public void setMyNestedObjectSet(ImmutableSet<NestedObject> myNestedObjectSet) {
        this.myNestedObjectSet = myNestedObjectSet;
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
        if (myInstant != null ? !myInstant.equals(that.myInstant) : that.myInstant != null) return false;
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
        result = 31 * result + (myInstant != null ? myInstant.hashCode() : 0);
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
                .add("myInstant", myInstant.getMillis())
                .add("myNestedObject", myNestedObject)
                .add("myNestedObjectSet", myNestedObjectSet)
                .toString();
    }

    public static class NestedObject {
        private Instant instant0;
        private Instant instant1;
        private Instant instant2;
        private Instant instant3;
        private Instant instant4;
        private Instant instant5;
        private Instant instant6;
        private Instant instant7;
        private Instant instant8;
        private Instant instant9;

        public NestedObject() { }

        public NestedObject(Instant instant0,
                            Instant instant1,
                            Instant instant2,
                            Instant instant3,
                            Instant instant4,
                            Instant instant5,
                            Instant instant6,
                            Instant instant7,
                            Instant instant8,
                            Instant instant9) {
            this.instant0 = instant0;
            this.instant1 = instant1;
            this.instant2 = instant2;
            this.instant3 = instant3;
            this.instant4 = instant4;
            this.instant5 = instant5;
            this.instant6 = instant6;
            this.instant7 = instant7;
            this.instant8 = instant8;
            this.instant9 = instant9;
        }

        public Instant getInstant0() {
            return instant0;
        }

        public void setInstant0(Instant instant0) {
            this.instant0 = instant0;
        }

        public Instant getInstant1() {
            return instant1;
        }

        public void setInstant1(Instant instant1) {
            this.instant1 = instant1;
        }

        public Instant getInstant2() {
            return instant2;
        }

        public void setInstant2(Instant instant2) {
            this.instant2 = instant2;
        }

        public Instant getInstant3() {
            return instant3;
        }

        public void setInstant3(Instant instant3) {
            this.instant3 = instant3;
        }

        public Instant getInstant4() {
            return instant4;
        }

        public void setInstant4(Instant instant4) {
            this.instant4 = instant4;
        }

        public Instant getInstant5() {
            return instant5;
        }

        public void setInstant5(Instant instant5) {
            this.instant5 = instant5;
        }

        public Instant getInstant6() {
            return instant6;
        }

        public void setInstant6(Instant instant6) {
            this.instant6 = instant6;
        }

        public Instant getInstant7() {
            return instant7;
        }

        public void setInstant7(Instant instant7) {
            this.instant7 = instant7;
        }

        public Instant getInstant8() {
            return instant8;
        }

        public void setInstant8(Instant instant8) {
            this.instant8 = instant8;
        }

        public Instant getInstant9() {
            return instant9;
        }

        public void setInstant9(Instant instant9) {
            this.instant9 = instant9;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NestedObject that = (NestedObject) o;

            if (instant0 != null ? !instant0.equals(that.instant0) : that.instant0 != null) return false;
            if (instant1 != null ? !instant1.equals(that.instant1) : that.instant1 != null) return false;
            if (instant2 != null ? !instant2.equals(that.instant2) : that.instant2 != null) return false;
            if (instant3 != null ? !instant3.equals(that.instant3) : that.instant3 != null) return false;
            if (instant4 != null ? !instant4.equals(that.instant4) : that.instant4 != null) return false;
            if (instant5 != null ? !instant5.equals(that.instant5) : that.instant5 != null) return false;
            if (instant6 != null ? !instant6.equals(that.instant6) : that.instant6 != null) return false;
            if (instant7 != null ? !instant7.equals(that.instant7) : that.instant7 != null) return false;
            if (instant8 != null ? !instant8.equals(that.instant8) : that.instant8 != null) return false;
            if (instant9 != null ? !instant9.equals(that.instant9) : that.instant9 != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = instant0 != null ? instant0.hashCode() : 0;
            result = 31 * result + (instant1 != null ? instant1.hashCode() : 0);
            result = 31 * result + (instant2 != null ? instant2.hashCode() : 0);
            result = 31 * result + (instant3 != null ? instant3.hashCode() : 0);
            result = 31 * result + (instant4 != null ? instant4.hashCode() : 0);
            result = 31 * result + (instant5 != null ? instant5.hashCode() : 0);
            result = 31 * result + (instant6 != null ? instant6.hashCode() : 0);
            result = 31 * result + (instant7 != null ? instant7.hashCode() : 0);
            result = 31 * result + (instant8 != null ? instant8.hashCode() : 0);
            result = 31 * result + (instant9 != null ? instant9.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("instant0", instant0.getMillis())
                    .add("instant1", instant1.getMillis())
                    .add("instant2", instant2.getMillis())
                    .add("instant3", instant3.getMillis())
                    .add("instant4", instant4.getMillis())
                    .add("instant5", instant5.getMillis())
                    .add("instant6", instant6.getMillis())
                    .add("instant7", instant7.getMillis())
                    .add("instant8", instant8.getMillis())
                    .add("instant9", instant9.getMillis())
                    .toString();
        }
    }
}
