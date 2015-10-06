package cat.udl.eps.mylisp.altreader;

// Based on an example from Language Implementation Patterns by Terrence Parr

import static cat.udl.eps.mylisp.altreader.Token.*;

public class Lexer {

    public static final char EOF = (char) -1; // represent end of file char

    private final String input; // input string
    private int  p = 0;         // index into input of current character
    private char c;             // current character


    public Lexer(String input) {
        this.input = input;
        c = input.charAt(p); // prime lookahead
    }

    public void consume() {
        p++;
        c = p < input.length() ? input.charAt(p) : EOF;
    }

    private boolean isALPHA() {
        return isDIGIT() || isLETTER();
    }

    private boolean isLETTER() {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    private boolean isSIGN() {
        return c == '+' || c == '-';
    }

    private boolean isNUMBER() {
        return isSIGN() || isDIGIT();
    }

    private boolean isDIGIT() {
        return c >= '0' && c <= '9';
    }

    private boolean isWS() {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    public Token nextToken() {
        while (c != EOF) {
            if (isWS()) {
                WS();
            } else if (c == '(') {
                consume();
                return new Token(Type.LPAREN, "(");
            } else if (c == ')') {
                consume();
                return new Token(Type.RPAREN, ")");
            } else if (isLETTER()) {
                return ATOM();
            } else if (isNUMBER()) {
                return INTEGER();
            } else {
                throw new Error("invalid character: " + c);
            }
        }
        return new Token(Type.EOF, "<EOF>");
    }

    private Token ATOM() {
        StringBuilder buf = new StringBuilder();

        do {
            buf.append(c);
            consume();
        } while (isALPHA());

        if (c == EOF || c == ')' || isWS()) {
            return new Token(Type.ATOM, buf.toString());
        } else {
            throw new Error("invalid character: " + c);
        }
    }

    private Token INTEGER() {
        StringBuilder buf = new StringBuilder();

        if (isSIGN()) {
            buf.append(c);
            consume();
        }
        if (!isDIGIT()) {
            throw new Error("invalid character: " + c);
        }

        do {
            buf.append(c);
            consume();
        } while (isDIGIT());

        if (c == EOF || c == ')' || isWS()) {
            return new Token(Type.INTEGER, buf.toString());
        } else {
            throw new Error("invalid character: " + c);
        }
    }

    private void WS() {
        while (isWS()) {
            consume();
        }
    }
}