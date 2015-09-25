package cat.udl.eps.mylisp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmgimeno on 18/9/15.
 */

// A translation of Peter Norvig's parser written in python

public class Reader {

    public SExpression read(String program) {
        String[] tokens = program
                            .replace("(", " ( ")
                            .replace(")", " ) ")
                            .replaceFirst("\\s+", "")
                            .split("\\s+");
        return parse(listify(tokens));
    }

    private List<String> listify(String[] tokens) {
        return new ArrayList<>(Arrays.asList(tokens));
    }

    private SExpression parse(List<String> tokens) {
        if ( tokens.isEmpty() )
            throw new ReadingError("unexpected EOF while reading");
        String token = tokens.remove(0);
        if ( "(".equals(token) ) {
            LispList l = new LispList();
            while ( ! ")".equals(tokens.get(0)) ) {
                l.append(parse(tokens));
            }
            tokens.remove(0); // pop off ')'
            return l;
        } else if ( ")".equals(token) )
            throw new ReadingError("unexpected )");
        return makeAtom(token);
    }

    private SExpression makeAtom(String token) {
        try {
            return new LispInteger(Integer.parseInt(token));
        } catch (NumberFormatException ex) {
            return new Symbol(token);
        }
    }

}
