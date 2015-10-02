package cat.udl.eps.mylisp.evaluator;

import cat.udl.eps.mylisp.data.ConsCell;
import cat.udl.eps.mylisp.data.LispInteger;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class Evaluator {

    public SExpression eval(SExpression sexpr, Environment env) {

        if (sexpr instanceof LispInteger) return sexpr;

        if (sexpr instanceof Symbol) return env.find((Symbol) sexpr);

        ConsCell list = (ConsCell) sexpr;
        return apply(list.car(), list.cdr(), env);
    }

    private SExpression apply(SExpression fn, SExpression args, Environment env) {

        return null;
    }


}
