package cat.udl.eps.mylisp;

import cat.udl.eps.mylisp.data.Integer;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;
import cat.udl.eps.mylisp.environment.Environment;
import cat.udl.eps.mylisp.data.EvaluationError;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class ScopeTest {

    private static final Symbol A = new Symbol("A");
    private static final SExpression V1 = new Integer(1);
    private static final SExpression V2 = new Integer(2);

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
        Environment newEnvironment = new Environment(environment);
        assertEquals(V1, newEnvironment.find(A));
    }

    @Test
    public void push_change_pop_environment() {
        environment.bind(A, V1);
        Environment newEnvironment = new Environment(environment);
        newEnvironment.bind(A, V2);
        assertEquals(V2, newEnvironment.find(A));
        assertEquals(V1, environment.find(A));
    }
}
