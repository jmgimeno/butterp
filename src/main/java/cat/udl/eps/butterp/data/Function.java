package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Created by jmgimeno on 2/10/15.
 */
public abstract class Function implements SExpression {

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    public abstract SExpression apply(SExpression evargs, Environment env);

    @Override
    public String toString() {
        return String.format("<function-%x>", hashCode());
    }
}
