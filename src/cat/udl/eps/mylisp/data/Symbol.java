package cat.udl.eps.mylisp.data;

/**
 * Created by jmgimeno on 18/9/15.
 */
public class Symbol implements SExpression {

    public final String name;

    public Symbol(String symbol) {
        this.name = symbol;
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
        return "Symbol{" +
                "name='" + name + '\'' +
                '}';
    }
}
