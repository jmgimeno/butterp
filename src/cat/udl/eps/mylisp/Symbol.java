package cat.udl.eps.mylisp;

/**
 * Created by jmgimeno on 18/9/15.
 */
public class Symbol extends SExpression {

    private final String symbol;

    public Symbol(String symbol) {
        super();
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol1 = (Symbol) o;

        return symbol.equals(symbol1.symbol);

    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "symbol='" + symbol + '\'' +
                '}';
    }
}
