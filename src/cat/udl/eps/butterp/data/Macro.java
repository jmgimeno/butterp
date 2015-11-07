package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Created by jmgimeno on 16/10/15.
 */
public class Macro extends Special {

    private final Function expansionFn;

    public Macro(SExpression params, SExpression body, Environment definitionEnv) {
        this.expansionFn = new Lambda(params, body, definitionEnv);
    }

    @Override
    public SExpression apply(SExpression args, Environment env) {
        return expansionFn.apply(args, env).eval(env);
    }
}
