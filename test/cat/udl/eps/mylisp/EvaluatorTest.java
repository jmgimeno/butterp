package cat.udl.eps.mylisp;

import cat.udl.eps.mylisp.environment.Environment;
import cat.udl.eps.mylisp.data.EvaluationError;
import cat.udl.eps.mylisp.reader.Parser;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;
import cat.udl.eps.mylisp.main.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class EvaluatorTest {

    private final Environment env = Main.createInitialEnvironment();

    private void assertEvalTo(String input, String output) {
        SExpression sexpr = Parser.parse(input);
        SExpression actual = sexpr.eval(env);
        SExpression expected = Parser.parse(output);
        assertEquals(expected, actual);
    }

    private void assertEvalFails(String input) {
        assertEvalTo(input, null);
    }

    @Test(expected = EvaluationError.class)
    public void unbound_symbol() {
        assertEvalFails("NotExists");
    }

    @Test public void true_() {
        SExpression sexpr = Parser.parse("T");
        assertEquals(Symbol.TRUE, sexpr.eval(env));
    }

    @Test public void nil() {
        SExpression sexpr = Parser.parse("NIL");
        assertEquals(Symbol.NIL, sexpr.eval(env));
    }

    @Test
    public void number() {
        assertEvalTo("1", "1");
    }

    @Test public void quoted_symbol() {
        assertEvalTo("(QUOTE A)", "A");
    }

    @Test public void quoted_number() {
        assertEvalTo("(QUOTE 1)", "1");
    }

    @Test public void quoted_list() {
        assertEvalTo("(QUOTE (1 2 3 4))", "(1 2 3 4)");
    }

    @Test public void quoted_empty_list() {
        assertEvalTo("(QUOTE ())", "NIL");
    }

    @Test(expected = EvaluationError.class)
    public void quoted_no_arg() {
        assertEvalFails("(QUOTE)");
    }

    @Test(expected = EvaluationError.class)
    public void quoted_too_many_args() {
        assertEvalFails("(QUOTE 1 2 3)");
    }

    @Test public void car() {
        assertEvalTo("(CAR (QUOTE (1 2 3)))", "1");
    }

    @Test(expected = EvaluationError.class)
    public void car_no_args() {
        assertEvalFails("(CAR)");
    }

    @Test(expected = EvaluationError.class)
    public void car_too_many_args() {
        assertEvalFails("(CAR 1 2 3)");
    }

    @Test(expected = EvaluationError.class)
    public void car_number() {
        assertEvalFails("(CAR 1)");
    }

    @Test public void cdr() {
        assertEvalTo("(CDR (QUOTE (1 2 3)))", "(2 3)");
    }

    @Test(expected = EvaluationError.class)
    public void cdr_no_args() {
        assertEvalFails("(CDR)");
    }

    @Test(expected = EvaluationError.class)
    public void cdr_too_many_args() {
        assertEvalFails("(CDR 1 2 3)");
    }

    @Test(expected = EvaluationError.class)
    public void cdr_number() {
        assertEvalFails("(CDR 1)");
    }

    @Test(expected = EvaluationError.class)
    public void cons_no_args() {
        assertEvalFails("(CONS)");
    }

    @Test(expected = EvaluationError.class)
    public void cons_one_args() {
        assertEvalFails("(CONS 1)");
    }

    @Test(expected = EvaluationError.class)
    public void cons_too_many_args() {
        SExpression sexpr = Parser.parse("(CONS 1 2 3 4)");
        sexpr.eval(env);
    }

    @Test(expected = EvaluationError.class)
    public void cons_second_num_args() {
        assertEvalFails("(CONS NIL 2)");
    }

    @Test public void cons_to_nil() {
        SExpression sexpr = Parser.parse(("(CONS 1 NIL)"));
        SExpression expected = Parser.parse("(1)");
        assertEquals(expected, sexpr.eval(env));
    }

    @Test public void cons_list() {
        assertEvalTo("(CONS 1 (QUOTE (2 3 4)))", "(1 2 3 4)");
    }

    @Test(expected = EvaluationError.class)
    public void lambda_no_arg() {
        assertEvalFails("(LAMBDA)");
    }

    @Test(expected = EvaluationError.class)
    public void lambda_no_params_list() {
        assertEvalFails("(LAMBDA 1)");
    }

    @Test(expected = EvaluationError.class)
    public void lambda_params_list_no_symbol() {
        assertEvalFails("(LAMBDA (A 1 B))");
    }

    @Test
    public void lambda_constantly_one() {
        assertEvalTo("((LAMBDA () 1))", "1");
    }

    @Test
    public void lambda_constantly_one_more_expr_in_body() {
        assertEvalTo("((LAMBDA () 0 1))", "1");
    }

    @Test
    public void lambda_snoc() {
        assertEvalTo("((LAMBDA (D A) (CONS A D)) NIL 1)", "(1)");
    }

    @Test
    public void nested_lambdas() {
        assertEvalTo("(((LAMBDA (A) (LAMBDA (D) (CONS A D))) 1) NIL)", "(1)");
    }

    @Test
    public void lambda_func_param() {
        assertEvalTo("((LAMBDA (F A) (F A)) CAR (QUOTE (1 2)))", "1");
    }

    @Test(expected = EvaluationError.class)
    public void eq_no_args() {
        assertEvalFails("(EQ)");
    }

    @Test(expected = EvaluationError.class)
    public void eq_one_arg() {
        assertEvalFails("(EQ 1)");
    }

    @Test(expected = EvaluationError.class)
    public void eq_too_many_args() {
        assertEvalFails("(EQ 1 2 3 4)");
    }

    @Test
    public void eq_numbers_true() {
        assertEvalTo("(EQ 1 1)", "T");
    }

    @Test
    public void eq_numbers_nil() {
        assertEvalTo("(EQ 1 2)", "NIL");
    }

    @Test
    public void eq_complex_true() {
        assertEvalTo("(EQ (QUOTE (1 2 (3 4) 5 (6))) (QUOTE (1 2 (3 4) 5 (6))))", "T");
    }

    @Test
    public void eq_complex_false() {
        assertEvalTo("(EQ (QUOTE (1 2 (3) 5 (6))) (QUOTE (1 2 (3 4) 5 (6))))", "NIL");
    }

    @Test(expected = EvaluationError.class)
    public void if_no_args() {
        assertEvalFails("(IF)");
    }

    @Test(expected = EvaluationError.class)
    public void if_one_arg() {
        assertEvalFails("(IF 1)");
    }

    @Test(expected = EvaluationError.class)
    public void if_two_args() {
        assertEvalFails("(IF 1 2)");
    }

    @Test(expected = EvaluationError.class)
    public void if_too_many_args() {
        assertEvalFails("(IF)");
    }

    @Test
    public void if_then() {
        assertEvalTo("(IF (EQ 1 1) (QUOTE 1) (QUOTE 2))", "1");
    }

    @Test
    public void if_else() {
        assertEvalTo("(IF (EQ 1 2) (QUOTE 1) (QUOTE 2))", "2");
    }

    @Test
    public void add_no_arg() {
        assertEvalTo("(ADD)", "0");
    }

    @Test
    public void add_many_args() {
        assertEvalTo("(ADD 1 2 3 4)", "10");
    }

    @Test(expected = EvaluationError.class)
    public void add_not_a_number() {
        assertEvalFails("(ADD 1 T 2)");
    }
}
