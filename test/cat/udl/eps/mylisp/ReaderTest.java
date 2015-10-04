package cat.udl.eps.mylisp;

import cat.udl.eps.mylisp.data.LispInteger;
import cat.udl.eps.mylisp.data.ListOps;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;
import cat.udl.eps.mylisp.reader.Reader;
import org.junit.Test;

import static cat.udl.eps.mylisp.data.ListOps.list;
import static org.junit.Assert.assertEquals;

public class ReaderTest {

    public static final SExpression SYMBOL = new Symbol("SYMBOL");
    public static final SExpression INTEGER = new LispInteger(1234);

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
        assertEquals(ListOps.list(INTEGER, SYMBOL), sexpr);
    }

    @Test
    public void read_multilevel_list_left() {
        SExpression sexpr = reader.read("((1234) SYMBOL   )");
        assertEquals(ListOps.list(ListOps.list(INTEGER), SYMBOL), sexpr);
    }

    @Test
    public void read_multilevel_list_right() {
        SExpression sexpr = reader.read("(  1234 (SYMBOL))");
        assertEquals(ListOps.list(INTEGER, ListOps.list(SYMBOL)), sexpr);
    }

    @Test
    public void read_multilevel_list_both() {
        SExpression sexpr = reader.read("   (  (1234  ) (  SYMBOL)  )");
        assertEquals(ListOps.list(ListOps.list(INTEGER), ListOps.list(SYMBOL)), sexpr);
    }
}