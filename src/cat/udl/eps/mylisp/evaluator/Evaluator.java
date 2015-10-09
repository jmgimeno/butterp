package cat.udl.eps.mylisp.evaluator;

import cat.udl.eps.mylisp.data.*;

import static cat.udl.eps.mylisp.data.ListOps.car;
import static cat.udl.eps.mylisp.data.ListOps.cdr;
import static cat.udl.eps.mylisp.data.ListOps.cons;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class Evaluator {

    public static SExpression eval(SExpression sexpr, Environment env) {

        if (sexpr instanceof LispInteger) return sexpr;

        if (sexpr instanceof Symbol) return env.find((Symbol) sexpr);

        ConsCell list = (ConsCell) sexpr;
        return apply(list.car, list.cdr, env);
    }

    private static SExpression apply(SExpression fn, SExpression args, Environment env) {
        SExpression evfn = eval(fn, env);
        if (evfn instanceof Applicable) {
            return ((Applicable) evfn).apply(args, env);
        }
        throw new EvaluationError("Cannot apply");
    }

    public static SExpression mapEval(SExpression args, Environment env) {
        if (args == Symbol.NIL) return Symbol.NIL;
        else return cons(eval(car(args), env),
                         mapEval(cdr(args), env));
    }
}
