package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Created by jmgimeno on 18/9/15.
 */
public class Integer implements SExpression {

    public final int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Integer that = (Integer) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
