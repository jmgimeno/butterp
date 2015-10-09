package cat.udl.eps.mylisp.data;

import cat.udl.eps.mylisp.environment.Environment;

/**
 * Created by jmgimeno on 2/10/15.
 */
public interface SExpression {
    SExpression eval(Environment env);
}
