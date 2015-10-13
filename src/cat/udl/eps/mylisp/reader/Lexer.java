package cat.udl.eps.mylisp.reader;

// Based on an example from Language Implementation Patterns by Terrence Parr

public class Lexer {

    public static final char EOF = (char) -1; // represent end of file char

    private final String input; // input string
    private int  p = 0;         // index into input of current character
    private char c;             // current character

    public Lexer(String input) {
        this.input = input.isEmpty() ? " " : input;
        c = this.input.charAt(p); // prime lookahead
    }

    public void consume() {
        p++;
        c = p < input.length() ? input.charAt(p) : EOF;
    }

    public void match(char x) {
        if (c == x) { consume(); }
        else throw new LexerError("expecting " + x + " but got " + c);
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
                return Token.LPAREN;
            } else if (c == ')') {
                consume();
                return Token.RPAREN;
            } else if (c =='\'') {
                consume();
                if (!isWS()) {
                    return Token.QUOTE;
                } else {
                    throw new LexerError("No spaces allowed after quote character.");
                }
            } else if (isLETTER()) {
                return ATOM();
            } else if (isNUMBER()) {
                return INTEGER();
            } else {
                throw new LexerError("invalid character: " + c);
            }
        }
        return Token.EOF;
    }

    private Token ATOM() {
        StringBuilder buf = new StringBuilder();

        do {
            buf.append(c);
            consume();
        } while (isALPHA());

        if (c == EOF || c == ')' || isWS()) {
            return Token.ATOM(buf.toString());
        } else {
            throw new LexerError("invalid character: " + c);
        }
    }

    private Token INTEGER() {
        StringBuilder buf = new StringBuilder();

        if (isSIGN()) {
            buf.append(c);
            consume();
        }
        if (!isDIGIT()) {
            throw new LexerError("invalid character: " + c);
        }

        do {
            buf.append(c);
            consume();
        } while (isDIGIT());

        if (c == EOF || c == ')' || isWS()) {
            return Token.INTEGER(buf.toString());
        } else {
            throw new LexerError("invalid character: " + c);
        }
    }

    private void WS() {
        while (isWS()) {
            consume();
        }
    }
}