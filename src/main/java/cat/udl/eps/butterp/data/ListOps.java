package cat.udl.eps.butterp.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListOps {

    public static SExpression cons(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    public static SExpression car(SExpression cell) {
        return ((ConsCell) cell).car;
    }

    public static SExpression cdr(SExpression cell) {
        return ((ConsCell) cell).cdr;
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

    public static int length(SExpression list) {
        if (list == Symbol.NIL) return 0;
        return 1 + length(cdr(list));
    }

    public static SExpression nth(SExpression list, int n) {
        if (n == 0) return car(list);
        else return nth(cdr(list), n-1);
    }

    public static boolean isListOf(SExpression sexpr, Class<?> klass) {
        try {
            while (sexpr != Symbol.NIL) {
                if (!klass.isInstance(car(sexpr)))
                    return false;
                sexpr = cdr(sexpr);
            }
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public static boolean allDifferent(SExpression list) {
        Set<SExpression> seen = new HashSet<>();
        while (list != Symbol.NIL) {
            if (!seen.add(car(list)))
                return false;
            list = cdr(list);
        }
        return true;
    }
}
