package cat.udl.eps.mylisp.main;

import cat.udl.eps.mylisp.data.*;
import cat.udl.eps.mylisp.environment.Environment;
import cat.udl.eps.mylisp.data.EvaluationError;
import cat.udl.eps.mylisp.environment.NestedMap;

import static cat.udl.eps.mylisp.data.ListOps.*;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class Main {

    public static Environment createInitialEnvironment() {
        Environment env = new NestedMap();
        loadPredefined(env);
        return env;
    }

    private static void loadPredefined(Environment env) {
        env.bindGlobal(Symbol.TRUE, Symbol.TRUE);
        env.bindGlobal(Symbol.NIL, Symbol.NIL);

        env.bindGlobal(new Symbol("QUOTE"), new Function() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("QUOTE needs an argument.");
                return car(args);
            }
        });

        env.bindGlobal(new Symbol("CAR"), new Function() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("CAR needs an argument.");
                SExpression evargs = mapEval(args, env);
                try {
                    return car(car(evargs));
                } catch (ClassCastException ex) {
                    throw new EvaluationError("CAR needs a list argument");
                }
            }
        });

        env.bindGlobal(new Symbol("CDR"), new Function() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("CDR needs an argument.");
                SExpression evargs = mapEval(args, env);
                try {
                    return cdr(car(evargs));
                } catch (ClassCastException ex) {
                    throw new EvaluationError("CDR needs a list argument");
                }
            }
        });

        env.bindGlobal(new Symbol("CONS"), new Function() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 2)
                    throw new EvaluationError("CONS needs two arguments.");
                SExpression evargs = mapEval(args, env);
                SExpression car = nth(evargs, 0);
                SExpression cdr = nth(evargs, 1);
                if (cdr == Symbol.NIL || cdr instanceof ConsCell)
                    return cons(car, cdr);
                else throw new EvaluationError("CONS second argument should be list.");
            }
        });

        env.bindGlobal(new Symbol("LAMBDA"), new Function() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) < 1)
                    throw new EvaluationError("LAMBDA needs at least one arg");
                SExpression params = car(args);
                if (! isListOfSymbols(params))
                    throw new EvaluationError("LAMBDA params should be a list of symbols");
                SExpression body = cdr(args);
                return new Lambda(params, body, env);
            }
        });

        env.bindGlobal(new Symbol("EQ"), new Function() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 2)
                    throw new EvaluationError("EQ needs two arguments");
                SExpression evargs = mapEval(args, env);
                SExpression arg1 = nth(evargs, 0);
                SExpression arg2 = nth(evargs, 1);
                return arg1.equals(arg2) ? Symbol.TRUE : Symbol.NIL;
            }
        });
    }

}
