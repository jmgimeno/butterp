package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Created by jmgimeno on 2/10/15.
 */
public interface SExpression {

    SExpression eval(Environment env);
}
