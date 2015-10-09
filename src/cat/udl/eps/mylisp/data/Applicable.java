package cat.udl.eps.mylisp.data;

import cat.udl.eps.mylisp.evaluator.Environment;

/**
 * Created by jmgimeno on 2/10/15.
 */
public interface Applicable extends SExpression {
    SExpression apply(SExpression args, Environment env);
}
