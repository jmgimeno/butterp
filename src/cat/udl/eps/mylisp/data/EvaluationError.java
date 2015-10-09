package cat.udl.eps.mylisp.data;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class EvaluationError extends RuntimeException {
    public EvaluationError(String message) {
        super(message);
    }
}
