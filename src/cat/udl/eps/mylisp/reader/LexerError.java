package cat.udl.eps.mylisp.reader;

/**
 * Created by jmgimeno on 7/10/15.
 */
public class LexerError extends RuntimeException {

    public LexerError(String message) {
        super(message);
    }
}
