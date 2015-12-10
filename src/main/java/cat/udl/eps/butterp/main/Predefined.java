package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.reader.LexerError;
import cat.udl.eps.butterp.reader.Parser;
import cat.udl.eps.butterp.reader.ParserError;
import cat.udl.eps.butterp.reader.ReaderLexer;

import java.io.*;
import java.net.URL;

public class Predefined {

    private static final String predefined = "core.lisp";

    public static void loadPredefined(Environment env) {
        try (Reader reader = getReader(predefined)) {
            Parser parser = new Parser(new ReaderLexer(reader));
            parsePredefined(env, parser);
        } catch (FileNotFoundException e) {
            System.err.printf("%s does not exist.%n", predefined);
        } catch (IOException e) {
            throw new EvaluationError(e.getMessage());
        }
    }

    private static void parsePredefined(Environment env, Parser parser) {
        try {
            while (parser.hasMoreInput()) {
                SExpression sexpr = parser.sexpr();
                tryEval(env, sexpr);
            }
        } catch (LexerError | ParserError ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void tryEval(Environment env, SExpression sexpr) {
        try {
            sexpr.eval(env);
        } catch (EvaluationError ex) {
            System.out.printf(ex.getMessage());
        }
    }

    private static Reader getReader(String name) throws FileNotFoundException {
        ClassLoader classLoader = Predefined.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(name);
        return new InputStreamReader(inputStream);
    }
}
