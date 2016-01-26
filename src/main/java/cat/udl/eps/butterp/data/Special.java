package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Created by jmgimeno on 16/10/15.
 */
public abstract class Special implements SExpression {
    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    public abstract SExpression apply(SExpression args, Environment env);

    @Override
    public String toString() {
        return String.format("<special-%x>", hashCode());
    }
}
