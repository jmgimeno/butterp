package cat.udl.eps.mylisp.data;

import cat.udl.eps.mylisp.environment.Environment;

import static cat.udl.eps.mylisp.data.ListOps.*;

/**
 * Created by jmgimeno on 9/10/15.
 */
public class Lambda extends Applicable {

    private final SExpression parameters;
    private final SExpression body;
    private final Environment definitionEnv;

    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
        this.parameters = params;
        this.body = body;
        this.definitionEnv = definitionEnv;
    }

    @Override
    public SExpression apply(SExpression args, Environment callingEnv) {
        if (length(parameters) != length(args))
            throw new EvaluationError("Incorrect number of args in the call.");
        SExpression evargs = mapEval(args, callingEnv);
        Environment evalEnv = makeEvalEnv(evargs);
        return evalBody(evalEnv);
    }

    private Environment makeEvalEnv(SExpression evargs) {
        SExpression params = parameters;
        Environment evalEnv = new Environment(definitionEnv);
        while (params != Symbol.NIL) {
            evalEnv.bind((Symbol) car(params), car(evargs));
            params = cdr(params);
            evargs = cdr(evargs);
        }
        return evalEnv;
    }

    private SExpression evalBody(Environment env) {
        SExpression body = this.body;
        SExpression last = Symbol.NIL;
        while (body != Symbol.NIL) {
            last = car(body).eval(env);
            body = cdr(body);
        }
        return last;
    }
}
