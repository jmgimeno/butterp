package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmgimeno on 9/10/15.
 */
public class NestedMap implements Environment {

    private final Map<String, SExpression> bindings;
    private final Environment next;

    public NestedMap() {
        bindings = new HashMap<>();
        next = null;
    }

    private NestedMap(NestedMap next) {
        bindings = new HashMap<>();
        this.next = next;
    }

    @Override
    public Environment extend() {
        return new NestedMap(this);
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        bindings.put(symbol.name, value);
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
        if (next == null) bind(symbol, value);
        else next.bindGlobal(symbol, value);
    }

    @Override
    public SExpression find(Symbol symbol) {
        SExpression value = bindings.get(symbol.name);
        if (value != null) return value;
        if (next != null) return next.find(symbol);
        throw new EvaluationError(String.format("Symbol %s not found.", symbol));
    }
}
