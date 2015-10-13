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

    protected static SExpression mapEval(SExpression args, Environment env) {
        if (args == Symbol.NIL) return Symbol.NIL;
        else return cons(car(args).eval(env), mapEval(cdr(args), env));
    }

    protected static boolean isListOf(SExpression params, Class<?> klass) {
        try {
            SExpression current = params;
            while (current != Symbol.NIL) {
                if (!klass.isInstance(car(current)))
                    return false;
                current = cdr(current);
            }
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("<function-%x>", hashCode());
    }
}
