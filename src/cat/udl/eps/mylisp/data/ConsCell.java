package cat.udl.eps.mylisp.data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class ConsCell implements SExpression {

    public final SExpression car;
    public final SExpression cdr;

    public ConsCell(SExpression car, SExpression cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public static SExpression cons(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    public static SExpression list(SExpression... elems) {
        return list(Arrays.asList(elems));
    }

    public static SExpression list(List<SExpression> elems) {
        SExpression list = Symbol.NIL;
        for (int i = elems.size(); i > 0; i--) {
            list = cons(elems.get(i - 1), list);
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsCell consCell = (ConsCell) o;

        if (car != null ? !car.equals(consCell.car) : consCell.car != null) return false;
        return !(cdr != null ? !cdr.equals(consCell.cdr) : consCell.cdr != null);

    }

    @Override
    public int hashCode() {
        int result = car != null ? car.hashCode() : 0;
        result = 31 * result + (cdr != null ? cdr.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConsCell{" +
                "car=" + car +
                ", cdr=" + cdr +
                '}';
    }
}
