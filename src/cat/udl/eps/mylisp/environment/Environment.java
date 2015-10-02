package cat.udl.eps.mylisp.environment;

import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class Environment {

    private Scope top;

    public Environment() {
        top = new Scope();
    }

    public void bind(Symbol symbol, SExpression value) {
        top.bind(symbol, value);
    }

    public SExpression find(Symbol symbol) {
        return top.find(symbol);
    }

    public void pushScope() {
        top = new Scope(top);
    }

    public void popScope() {
        top = top.next;
    }

    private static class Scope {

        private final Map<String, SExpression> bindings;
        private final Scope next;

        public Scope() {
            bindings = new HashMap<>();
            next = null;
        }

        public Scope(Scope next) {
            bindings = new HashMap<>();
            this.next = next;
        }

        public void bind(Symbol symbol, SExpression value) {
            bindings.put(symbol.name, value);
        }

        public SExpression find(Symbol symbol) {
            SExpression value = bindings.get(symbol.name);
            if (value != null) return value;
            if (next != null) return next.find(symbol);
            return null;
        }
    }
}
