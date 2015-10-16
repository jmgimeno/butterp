package cat.udl.eps.mylisp.data;

import cat.udl.eps.mylisp.environment.Environment;

/**
 * Created by jmgimeno on 16/10/15.
 */
public abstract class Special implements SExpression {
    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    @Override
    public String toString() {
        return String.format("<special-%x>", hashCode());
    }
}
