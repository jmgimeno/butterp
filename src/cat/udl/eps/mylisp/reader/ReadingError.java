package cat.udl.eps.mylisp.reader;

/**
 * Created by jmgimeno on 18/9/15.
 */
public class ReadingError extends RuntimeException {
    public ReadingError(String message) {
        super(message);
    }
}
