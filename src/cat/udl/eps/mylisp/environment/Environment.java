package cat.udl.eps.mylisp.environment;

import cat.udl.eps.mylisp.data.EvaluationError;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmgimeno on 9/10/15.
 */
public class Environment {

    private final Map<String, SExpression> bindings;
    private final Environment next;

    public Environment() {
        bindings = new HashMap<>();
        next = null;
    }

    public Environment(Environment next) {
        bindings = new HashMap<>();
        this.next = next;
    }

    public void bind(Symbol symbol, SExpression value) {
        bindings.put(symbol.name, value);
    }

    public void bindGlobal(Symbol symbol, SExpression value) {
        if (next == null) bind(symbol, value);
        else next.bindGlobal(symbol, value);
    }

    public SExpression find(Symbol symbol) {
        SExpression value = bindings.get(symbol.name);
        if (value != null) return value;
        if (next != null) return next.find(symbol);
        throw new EvaluationError("Symbol not found");
    }
}
