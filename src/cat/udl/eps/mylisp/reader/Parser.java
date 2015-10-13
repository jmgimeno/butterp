package cat.udl.eps.mylisp.reader;

import cat.udl.eps.mylisp.data.Integer;
import cat.udl.eps.mylisp.data.ListOps;
import cat.udl.eps.mylisp.data.SExpression;
import cat.udl.eps.mylisp.data.Symbol;

import java.util.ArrayList;
import java.util.List;

import static cat.udl.eps.mylisp.data.ListOps.cons;
import static cat.udl.eps.mylisp.data.ListOps.list;

// Based on an example from Language Implementation Patterns by Terrence Parr

public class Parser {

    private final Lexer input;     // from where do we get tokens?
    private Token lookahead;       // the current lookahead token

    public Parser(Lexer input) {
        this.input = input;
        consume();
    }

    public static SExpression parse(String input) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        return parser.sexpr();
    }

    private void consume() {
        lookahead = input.nextToken();
    }

    private void match(Token.Type type) {
        if ( lookahead.type == type ) {
            consume();
        } else {
            throw new ParserError("expecting " + type + "; found " + lookahead);
        }
    }

    private SExpression atom() {
        if (lookahead.type == Token.Type.ATOM) {
            SExpression sexpr = new Symbol(lookahead.text);
            consume();
            return sexpr;
        } else {
            throw new ParserError("expecting " + Token.Type.ATOM + "; found " + lookahead);
        }
    }

    private SExpression integer() {
        if (lookahead.type == Token.Type.INTEGER) {
            SExpression sexpr = new Integer(java.lang.Integer.parseInt(lookahead.text));
            consume();
            return sexpr;
        } else {
            throw new ParserError("expecting " + Token.Type.INTEGER + "; found " + lookahead);
        }
    }

    private SExpression list() {
        List<SExpression> elems = new ArrayList<>();
        match(Token.Type.LPAREN);
        while (lookahead.type != Token.Type.RPAREN) {
            elems.add(sexpr());
        }
        match(Token.Type.RPAREN);
        return ListOps.list(elems);
    }

    public SExpression sexpr() {
        if (lookahead.type == Token.Type.ATOM) {
            return atom();
        } else if (lookahead.type == Token.Type.INTEGER) {
            return integer();
        } else if (lookahead.type == Token.Type.LPAREN) {
            return list();
        } else if (lookahead.type == Token.Type.QUOTE) {
            consume();
            return ListOps.list(new Symbol("quote"), sexpr());
        } else {
            throw new ParserError("expecting atom, integer or list, found " + lookahead);
        }
    }

    public boolean hasMoreInput() {
        return lookahead.type != Token.Type.EOF;
    }
}
