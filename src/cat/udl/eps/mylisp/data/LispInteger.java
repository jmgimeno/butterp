package cat.udl.eps.mylisp.data;

/**
 * Created by jmgimeno on 18/9/15.
 */
public class LispInteger implements SExpression {

    private final int value;

    public LispInteger(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LispInteger that = (LispInteger) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "LispInteger{" +
                "value=" + value +
                '}';
    }
}