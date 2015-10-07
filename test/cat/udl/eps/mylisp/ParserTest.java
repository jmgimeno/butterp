package cat.udl.eps.mylisp;

import cat.udl.eps.mylisp.reader.Parser;
import cat.udl.eps.mylisp.data.LispInteger;
import cat.udl.eps.mylisp.data.ListOps;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    public static final SExpression SYMBOL = new Symbol("SYMBOL");
    public static final SExpression INTEGER = new LispInteger(1234);

    @Test
    public void read_one_integer() {
        SExpression sexpr = Parser.parse("1234");
        assertEquals(INTEGER, sexpr);
    }

    @Test
    public void read_one_integer_with_spaces() {
        SExpression sexpr = Parser.parse("   1234");
        assertEquals(INTEGER, sexpr);
    }

    @Test
    public void read_one_symbol() {
        SExpression sexpr = Parser.parse("SYMBOL");
        assertEquals(SYMBOL, sexpr);
    }

    @Test
    public void read_one_symbol_with_spaces() {
        SExpression sexpr = Parser.parse("   SYMBOL");
        assertEquals(SYMBOL, sexpr);
    }

    @Test
    public void read_simple_list() {
        SExpression sexpr = Parser.parse("   (1234 SYMBOL)");
        assertEquals(ListOps.list(INTEGER, SYMBOL), sexpr);
    }

    @Test
    public void read_multilevel_list_left() {
        SExpression sexpr = Parser.parse("((1234) SYMBOL   )");
        assertEquals(ListOps.list(ListOps.list(INTEGER), SYMBOL), sexpr);
    }

    @Test
    public void read_multilevel_list_right() {
        SExpression sexpr = Parser.parse("(  1234 (SYMBOL))");
        assertEquals(ListOps.list(INTEGER, ListOps.list(SYMBOL)), sexpr);
    }

    @Test
    public void read_multilevel_list_both() {
        SExpression sexpr = Parser.parse("   (  (1234  ) (  SYMBOL)  )");
        assertEquals(ListOps.list(ListOps.list(INTEGER), ListOps.list(SYMBOL)), sexpr);
    }
}