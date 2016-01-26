package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Created by jmgimeno on 18/9/15.
 */
public class Symbol implements SExpression {

    public static final Symbol TRUE = new Symbol("t");
    public static final Symbol NIL = new Symbol("nil");

    public final String name;

    public Symbol(String symbol) {
        this.name = symbol;
    }

    @Override
    public SExpression eval(Environment env) {
        return env.find(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol1 = (Symbol) o;

        return name.equals(symbol1.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
