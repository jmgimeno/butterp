package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

import static cat.udl.eps.butterp.data.ListOps.mapEval;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class ConsCell implements SExpression {

    public final SExpression car;
    public final SExpression cdr;

    public ConsCell(SExpression car, SExpression cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
        SExpression evfn = car.eval(env);
        if (evfn instanceof Special) {
            return evfn.apply(cdr, env);
        } else {
            return evfn.apply(mapEval(cdr, env), env);
        }
    }

    @Override
    public SExpression apply(SExpression args, Environment env) {
        throw new EvaluationError("Lists are not applicable.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsCell consCell = (ConsCell) o;

        if (car != null ? !car.equals(consCell.car) : consCell.car != null) return false;
        return !(cdr != null ? !cdr.equals(consCell.cdr) : consCell.cdr != null);

    }

    @Override
    public int hashCode() {
        int result = car != null ? car.hashCode() : 0;
        result = 31 * result + (cdr != null ? cdr.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("(");
        SExpression current = this;
        boolean first = true;
        while (current != Symbol.NIL) {
            builder.append(String.format(first ? "%s" : " %s", ListOps.car(current)));
            current = ListOps.cdr(current);
            first = false;
        }
        builder.append(")");
        return builder.toString();
    }
}
