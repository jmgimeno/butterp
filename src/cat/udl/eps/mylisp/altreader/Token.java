package cat.udl.eps.mylisp.altreader;

// Based on an example from Language Implementation Patterns by Terrence Parr


public class Token {

    public enum Type {
        EOF, ATOM, INTEGER, LPAREN, RPAREN
    }

    public final Type   type;
    public final String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public String toString() {;
        return "<'" + text + "'," + type + ">";
    }
}