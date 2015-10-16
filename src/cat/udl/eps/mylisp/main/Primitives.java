package cat.udl.eps.mylisp.main;

import cat.udl.eps.mylisp.data.*;
import cat.udl.eps.mylisp.data.Integer;
import cat.udl.eps.mylisp.environment.Environment;

import static cat.udl.eps.mylisp.data.ListOps.*;

/**
 * Created by jmgimeno on 13/10/15.
 */
public class Primitives {

    public static void loadPrimitives(Environment env) {
        env.bindGlobal(Symbol.TRUE, Symbol.TRUE);
        env.bindGlobal(Symbol.NIL, Symbol.NIL);

        env.bindGlobal(new Symbol("define"), new Special() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 2)
                    throw new EvaluationError("DEFINE should have two arguments");
                SExpression symbol = nth(args, 0);
                if (!(symbol instanceof Symbol))
                    throw new EvaluationError("DEFINE's first argument shoul be a symbol");
                SExpression value = nth(args, 1).eval(env);
                env.bindGlobal((Symbol) symbol, value);
                return Symbol.NIL;
            }
        });

        env.bindGlobal(new Symbol("quote"), new Special() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("QUOTE needs an argument.");
                return car(args);
            }
        });

        env.bindGlobal(new Symbol("car"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (length(evargs) != 1)
                    throw new EvaluationError("CAR needs an argument.");
                try {
                    return car(car(evargs));
                } catch (ClassCastException ex) {
                    throw new EvaluationError("CAR needs a list argument");
                }
            }
        });

        env.bindGlobal(new Symbol("cdr"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (length(evargs) != 1)
                    throw new EvaluationError("CDR needs an argument.");
                try {
                    return cdr(car(evargs));
                } catch (ClassCastException ex) {
                    throw new EvaluationError("CDR needs a list argument");
                }
            }
        });

        env.bindGlobal(new Symbol("cons"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (length(evargs) != 2)
                    throw new EvaluationError("CONS needs two arguments.");
                SExpression car = nth(evargs, 0);
                SExpression cdr = nth(evargs, 1);
                if (cdr == Symbol.NIL || cdr instanceof ConsCell)
                    return cons(car, cdr);
                else throw new EvaluationError("CONS second argument should be list.");
            }
        });

        env.bindGlobal(new Symbol("lambda"), new Special() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) < 1)
                    throw new EvaluationError("LAMBDA needs at least one arg");
                SExpression params = car(args);
                if (!isListOf(params, Symbol.class))
                    throw new EvaluationError("LAMBDA params should be a list of symbols");
                SExpression body = cdr(args);
                return new Lambda(params, body, env);
            }
        });

        env.bindGlobal(new Symbol("eq"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (length(evargs) != 2)
                    throw new EvaluationError("EQ needs two arguments");
                SExpression arg1 = nth(evargs, 0);
                SExpression arg2 = nth(evargs, 1);
                return arg1.equals(arg2) ? Symbol.TRUE : Symbol.NIL;
            }
        });

        env.bindGlobal(new Symbol("if"), new Special() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 3)
                    throw new EvaluationError("IF needs condition, then and else parts.");
                SExpression test = nth(args, 0);
                SExpression then = nth(args, 1);
                SExpression e1se = nth(args, 2);
                return test.eval(env) != Symbol.NIL ? then.eval(env) : e1se.eval(env);
            }
        });

        env.bindGlobal(new Symbol("add"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (!isListOf(evargs, Integer.class))
                    throw new EvaluationError("ADD should get only integer arguments.");
                int accumulator = 0;
                while (evargs != Symbol.NIL) {
                    accumulator += ((Integer) car(evargs)).value;
                    evargs = cdr(evargs);
                }
                return new Integer(accumulator);
            }
        });

        env.bindGlobal(new Symbol("mult"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (!isListOf(evargs, Integer.class))
                    throw new EvaluationError("MULT should get only integer arguments.");
                int accumulator = 1;
                while (evargs != Symbol.NIL) {
                    accumulator *= ((Integer) car(evargs)).value;
                    evargs = cdr(evargs);
                }
                return new Integer(accumulator);
            }
        });

        env.bindGlobal(new Symbol("eval"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (length(evargs) != 1)
                    throw new EvaluationError("EVAL should get only one argument");
                return car(evargs).eval(env);
            }
        });

        env.bindGlobal(new Symbol("apply"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (length(evargs) != 2) {
                    throw new EvaluationError("APPLY should get two arguments");
                }
                return car(evargs).apply(car(cdr(evargs)), env);
            }
        });

        env.bindGlobal(new Symbol("list"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                return evargs;
            }
        });
    }
}
