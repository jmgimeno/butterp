package cat.udl.eps.mylisp.main;

import cat.udl.eps.mylisp.data.*;
import cat.udl.eps.mylisp.evaluator.Environment;
import cat.udl.eps.mylisp.evaluator.EvaluationError;
import cat.udl.eps.mylisp.evaluator.Evaluator;

import static cat.udl.eps.mylisp.data.ListOps.*;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class Main {

    private static final Evaluator evaluator = new Evaluator();

    public static Environment createInitialEnvironment() {
        Environment env = new Environment();
        loadPredefined(env);
        env.pushScope();
        return env;
    }

    private static void loadPredefined(Environment env) {
        env.bind(Symbol.TRUE, Symbol.TRUE);
        env.bind(Symbol.NIL, Symbol.NIL);
        env.bind(new Symbol("QUOTE"), new Primitive() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("QUOTE needs an argument.");
                return car(args);
            }
        });
        env.bind(new Symbol("CAR"), new Primitive() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("CAR needs an argument.");
                SExpression evargs = evalArgs(args, env);
                try {
                    return car(car(evargs));
                } catch (ClassCastException ex) {
                    throw new EvaluationError("CAR needs a list argument");
                }
            }
        });
        env.bind(new Symbol("CDR"), new Primitive() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("CDR needs an argument.");
                SExpression evargs = evalArgs(args, env);
                try {
                    return cdr(car(evargs));
                } catch (ClassCastException ex) {
                    throw new EvaluationError("CDR needs a list argument");
                }
            }
        });
        env.bind(new Symbol("CONS"), new Primitive() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 2)
                    throw new EvaluationError("CONS needs two arguments.");
                SExpression evargs = evalArgs(args, env);
                SExpression car = nth(evargs, 0);
                SExpression cdr = nth(evargs, 1);
                if (cdr == Symbol.NIL || cdr instanceof ConsCell)
                    return cons(car, cdr);
                else throw new EvaluationError("CONS second argument should be list.");
            }
        });
    }

    public static SExpression evalArgs(SExpression args, Environment env) {
        if (args == Symbol.NIL) return Symbol.NIL;
        else return cons(evaluator.eval(car(args), env),
                         evalArgs(cdr(args), env));
    }

}
