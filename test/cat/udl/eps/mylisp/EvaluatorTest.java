package cat.udl.eps.mylisp;

import cat.udl.eps.mylisp.reader.Parser;
import cat.udl.eps.mylisp.data.LispInteger;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;
import cat.udl.eps.mylisp.evaluator.Environment;
import cat.udl.eps.mylisp.evaluator.EvaluationError;
import cat.udl.eps.mylisp.evaluator.Evaluator;
import cat.udl.eps.mylisp.main.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class EvaluatorTest {
    private static final SExpression A = new Symbol("A");
    private static final SExpression V1 = new LispInteger(1);

    private final Environment env = Main.createInitialEnvironment();
    private final Evaluator evaluator = new Evaluator();

    @Test public void eval_number() {
        assertEquals(V1, evaluator.eval(V1, env));
    }

    @Test public void eval_bound_symbol() {
        env.bind((Symbol) A, V1);
        assertEquals(V1, evaluator.eval(A, env));
    }

    @Test(expected = EvaluationError.class)
    public void eval_unbound_symbol() {
        evaluator.eval(A, env);
    }

    @Test public void eval_true() {
        SExpression sexpr = Parser.parse("T");
        assertEquals(Symbol.TRUE, evaluator.eval(sexpr, env));
    }

    @Test public void eval_nil() {
        SExpression sexpr = Parser.parse("NIL");
        assertEquals(Symbol.NIL, evaluator.eval(sexpr, env));
    }

    @Test public void eval_quoted_symbol() {
        SExpression sexpr = Parser.parse("(QUOTE A)");
        assertEquals(A, evaluator.eval(sexpr, env));
    }

    @Test public void eval_quoted_number() {
        SExpression sexpr = Parser.parse("(QUOTE 1)");
        SExpression expected = Parser.parse("1");
        assertEquals(expected, evaluator.eval(sexpr, env));
    }

    @Test public void eval_quoted_list() {
        SExpression sexpr = Parser.parse("(QUOTE (1 2 3 4))");
        SExpression expected = Parser.parse("(1 2 3 4)");
        assertEquals(expected, evaluator.eval(sexpr, env));
    }

    @Test public void eval_quoted_empty_list() {
        SExpression sexpr = Parser.parse("(QUOTE ())");
        assertEquals(Symbol.NIL, evaluator.eval(sexpr, env));
    }

    @Test(expected = EvaluationError.class)
    public void eval_quoted_no_arg() {
        SExpression sexpr = Parser.parse("(QUOTE)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_quoted_too_many_args() {
        SExpression sexpr = Parser.parse("(QUOTE 1 2 3)");
        evaluator.eval(sexpr, env);
    }

    @Test public void eval_car() {
        SExpression sexpr = Parser.parse("(CAR (QUOTE (1 2 3)))");
        SExpression expected = Parser.parse("1");
        assertEquals(expected, evaluator.eval(sexpr, env));
    }

    @Test(expected = EvaluationError.class)
    public void eval_car_no_args() {
        SExpression sexpr = Parser.parse("(CAR)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_car_too_many_args() {
        SExpression sexpr = Parser.parse("(CAR 1 2 3)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_car_number() {
        SExpression sexpr = Parser.parse("(CAR 1)");
        evaluator.eval(sexpr, env);
    }

    @Test public void eval_cdr() {
        SExpression sexpr = Parser.parse("(CDR (QUOTE (1 2 3)))");
        SExpression expected = Parser.parse("(2 3)");
        assertEquals(expected, evaluator.eval(sexpr, env));
    }

    @Test(expected = EvaluationError.class)
    public void eval_cdr_no_args() {
        SExpression sexpr = Parser.parse("(CDR)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_cdr_too_many_args() {
        SExpression sexpr = Parser.parse("(CDR 1 2 3)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_cdr_number() {
        SExpression sexpr = Parser.parse("(CDR 1)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_cons_no_args() {
        SExpression sexpr = Parser.parse("(CONS)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_cons_one_args() {
        SExpression sexpr = Parser.parse("(CONS 1)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_cons_too_many_args() {
        SExpression sexpr = Parser.parse("(CONS 1 2 3 4)");
        evaluator.eval(sexpr, env);
    }

    @Test(expected = EvaluationError.class)
    public void eval_cons_second_num_args() {
        SExpression sexpr = Parser.parse("(CONS NIL 2)");
        evaluator.eval(sexpr, env);
    }

    @Test public void eval_cons_to_nil() {
        SExpression sexpr = Parser.parse(("(CONS 1 NIL)"));
        SExpression expected = Parser.parse("(1)");
        assertEquals(expected, evaluator.eval(sexpr, env));
    }

    @Test public void eval_cons_list() {
        SExpression sexpr = Parser.parse(("(CONS 1 (QUOTE (2 3 4)))"));
        SExpression expected = Parser.parse("(1 2 3 4)");
        assertEquals(expected, evaluator.eval(sexpr, env));
    }
}
