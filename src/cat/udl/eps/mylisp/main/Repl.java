package cat.udl.eps.mylisp.main;

import cat.udl.eps.mylisp.data.*;
import cat.udl.eps.mylisp.environment.Environment;
import cat.udl.eps.mylisp.environment.NestedMap;
import cat.udl.eps.mylisp.reader.Lexer;
import cat.udl.eps.mylisp.reader.LexerError;
import cat.udl.eps.mylisp.reader.Parser;
import cat.udl.eps.mylisp.reader.ParserError;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class Repl {

    public static Environment createInitialEnvironment() {
        Environment env = new NestedMap();
        Primitives.loadPredefined(env);
        return env;
    }

    public static String readInput() {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        StringBuilder builder = new StringBuilder();
        String line;
        boolean first = true;
        do {
            System.out.print(first ? "mylisp> " : "mylisp# ");
            line = scanner.nextLine();
            builder.append(String.format("%s\n", line));
            first = false;
        } while (!line.isEmpty());
        return builder.toString();
    }

    public static void main(String[] args) {
        Environment environment = createInitialEnvironment();
        while (true) {
            String input = readInput();
            if (":exit".equals(input)) break;
            try {
                Parser parser = new Parser(new Lexer(input));
                SExpression sexpr = parser.sexpr();
                SExpression result = sexpr.eval(environment);
                System.out.println(String.format(">>>>>>> %s\n", result));
            } catch (LexerError | ParserError | EvaluationError ex) {
                System.out.println(String.format("~~~~~~~ %s\n", ex.getMessage()));
            }
        }
    }
}
