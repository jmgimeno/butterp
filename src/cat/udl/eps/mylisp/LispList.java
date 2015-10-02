package cat.udl.eps.mylisp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmgimeno on 18/9/15.
 */
public class LispList implements SExpression {

    List<SExpression> list;

    public LispList() {
        list = new ArrayList<>();
    }

    public LispList(SExpression... sexprs) {
        this();
        for(SExpression sexpr: sexprs) {
            append(sexpr);
        }
    }

    public void append(SExpression sexpr) {
        list.add(sexpr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LispList lispList = (LispList) o;

        return list.equals(lispList.list);

    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        return "LispList{" +
                "list=" + list +
                '}';
    }
}
