package cat.udl.eps.mylisp.main;

import cat.udl.eps.mylisp.data.*;
import cat.udl.eps.mylisp.environment.Environment;
import cat.udl.eps.mylisp.data.EvaluationError;

import static cat.udl.eps.mylisp.data.ListOps.*;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class Main {

    public static Environment createInitialEnvironment() {
        Environment env = new Environment();
        loadPredefined(env);
        return env;
    }

    private static void loadPredefined(Environment env) {
        env.bind(Symbol.TRUE, Symbol.TRUE);
        env.bind(Symbol.NIL, Symbol.NIL);

        env.bind(new Symbol("QUOTE"), new Applicable() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("QUOTE needs an argument.");
                return car(args);
            }
        });

        env.bind(new Symbol("CAR"), new Applicable() {
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

        env.bind(new Symbol("CDR"), new Applicable() {
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

        env.bind(new Symbol("CONS"), new Applicable() {
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

        env.bind(new Symbol("LAMBDA"), new Applicable() {
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
    }

}
