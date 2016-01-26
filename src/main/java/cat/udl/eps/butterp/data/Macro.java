package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Macro extends Special {

    private final Function expansionFn;

    public Macro(SExpression params, SExpression body, Environment definitionEnv) {
        this.expansionFn = new Lambda(params, body, definitionEnv);
    }

    @Override
    public SExpression applySpecial(SExpression args, Environment env) {
        SExpression expr = ListOps.list(args);
        return expansionFn.apply(expr, env).eval(env);
    }
}
