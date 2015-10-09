package cat.udl.eps.mylisp.data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jmgimeno on 4/10/15.
 */
public class ListOps {

    public static SExpression cons(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    public static SExpression car(SExpression sexpr) {
        return ((ConsCell) sexpr).car;
    }

    public static SExpression cdr(SExpression sexpr) {
        return ((ConsCell) sexpr).cdr;
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

    public static int length(SExpression sexpr) {
        if (sexpr == Symbol.NIL) return 0;
        return 1 + length(cdr(sexpr));
    }

    public static SExpression nth(SExpression sexpr, int n) {
        if (n == 0) return car(sexpr);
        else return nth(cdr(sexpr), n-1);
    }

    public static boolean isListOfSymbols(SExpression params) {
        try {
            SExpression current = params;
            while (current != Symbol.NIL) {
                if (!(car(current) instanceof Symbol))
                    return false;
                current = cdr(current);
            }
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }
}
