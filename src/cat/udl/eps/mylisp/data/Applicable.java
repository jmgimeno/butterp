package cat.udl.eps.mylisp.data;

import cat.udl.eps.mylisp.environment.Environment;

/**
 * Created by jmgimeno on 2/10/15.
 */
public abstract class Applicable implements SExpression {

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    public abstract SExpression apply(SExpression args, Environment env);

}
