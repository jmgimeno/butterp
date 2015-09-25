package cat.udl.eps.mylisp;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ReaderTest {

    public static final Symbol SYMBOL = new Symbol("SYMBOL");
    public static final LispInteger INTEGER = new LispInteger(1234);

    private final Reader reader = new Reader();

    @Test
    public void read_one_integer() {
        SExpression sexpr = reader.read("1234");
        assertEquals(INTEGER, sexpr);
    }

    @Test
    public void read_one_integer_with_spaces() {
        SExpression sexpr = reader.read("   1234");
        assertEquals(INTEGER, sexpr);
    }

    @Test
    public void read_one_symbol() {
        SExpression sexpr = reader.read("SYMBOL");
        assertEquals(SYMBOL, sexpr);
    }

    @Test
    public void read_one_symbol_with_spaces() {
        SExpression sexpr = reader.read("   SYMBOL");
        assertEquals(SYMBOL, sexpr);
    }

    @Test
    public void read_simple_list() {
        SExpression sexpr = reader.read("   (1234 SYMBOL)");
        assertEquals(new LispList(INTEGER, SYMBOL), sexpr);
    }

    @Test
    public void read_multilevel_list_left() {
        SExpression sexpr = reader.read("((1234) SYMBOL   )");
        assertEquals(new LispList(new LispList(INTEGER), SYMBOL), sexpr);
    }

    @Test
    public void read_multilevel_list_right() {
        SExpression sexpr = reader.read("(  1234 (SYMBOL))");
        assertEquals(new LispList(INTEGER, new LispList(SYMBOL)), sexpr);
    }

    @Test
    public void read_multilevel_list_both() {
        SExpression sexpr = reader.read("   (  (1234  ) (  SYMBOL)  )");
        assertEquals(new LispList(new LispList(INTEGER), new LispList(SYMBOL)), sexpr);
    }
}