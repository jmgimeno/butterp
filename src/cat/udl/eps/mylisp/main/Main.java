package cat.udl.eps.mylisp.main;

import cat.udl.eps.mylisp.data.ConsCell;
import cat.udl.eps.mylisp.data.Primitive;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;
import cat.udl.eps.mylisp.evaluator.Environment;
import cat.udl.eps.mylisp.evaluator.EvaluationError;
import cat.udl.eps.mylisp.evaluator.Evaluator;

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
                ConsCell largs = (ConsCell) args;
                return largs.car;
            }
        });
        env.bind(new Symbol("CAR"), new Primitive() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("CAR needs an argument.");
                SExpression value = evaluator.eval(((ConsCell) args).car, env);
                if (value instanceof ConsCell)
                    return ((ConsCell)value).car;
                throw new EvaluationError("CAR needs a list argument");
            }
        });
        env.bind(new Symbol("CDR"), new Primitive() {
            @Override
            public SExpression apply(SExpression args, Environment env) {
                if (length(args) != 1)
                    throw new EvaluationError("CDR needs an argument.");
                SExpression value = evaluator.eval(((ConsCell) args).car, env);
                if (value instanceof ConsCell)
                    return ((ConsCell)value).cdr;
                throw new EvaluationError("CDR needs a list argument");
            }
        });
    }

    public static int length(SExpression sexpr) {
        if (sexpr == Symbol.NIL) return 0;
        ConsCell cell = (ConsCell) sexpr;
        return 1 + length(cell.cdr);
    }
}
