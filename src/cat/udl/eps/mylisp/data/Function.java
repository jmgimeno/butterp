package cat.udl.eps.mylisp.data;

import cat.udl.eps.mylisp.environment.Environment;

import static cat.udl.eps.mylisp.data.ListOps.*;

/**
 * Created by jmgimeno on 2/10/15.
 */
public abstract class Function implements SExpression {

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    @Override
    public String toString() {
        return String.format("<function-%x>", hashCode());
    }
}
