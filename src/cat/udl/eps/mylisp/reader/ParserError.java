package cat.udl.eps.mylisp.reader;

/**
 * Created by jmgimeno on 7/10/15.
 */
public class ParserError extends RuntimeException {
    public ParserError(String message) {
        super(message);
    }
}
