package cat.udl.eps.mylisp;

import cat.udl.eps.mylisp.data.LispInteger;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;
import cat.udl.eps.mylisp.evaluator.Environment;
import cat.udl.eps.mylisp.evaluator.EvaluationError;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class EnvironmentTest {

    private static final Symbol A = new Symbol("A");
    private static final SExpression V1 = new LispInteger(1);
    private static final SExpression V2 = new LispInteger(2);

    private final Environment environment = new Environment();

    @Test(expected = EvaluationError.class)
    public void empty_environment() {
        environment.find(A);
    }

    @Test
    public void one_binding() {
        environment.bind(A, V1);
        assertEquals(V1, environment.find(A));
    }

    @Test
    public void change_binding() {
        environment.bind(A, V1);
        environment.bind(A, V2);
        assertEquals(V2, environment.find(A));
    }

    @Test
    public void one_environment_deep() {
        environment.bind(A, V1);
        environment.pushScope();
        assertEquals(V1, environment.find(A));
    }

    @Test
    public void push_change_pop_environment() {
        environment.bind(A, V1);
        environment.pushScope();
        environment.bind(A, V2);
        assertEquals(V2, environment.find(A));
        environment.popScope();
        assertEquals(V1, environment.find(A));
    }
}
