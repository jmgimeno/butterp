package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.*;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.environment.NestedMap;
import cat.udl.eps.butterp.reader.*;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by jmgimeno on 2/10/15.
 */
public class Repl {

    public static Environment createInitialEnvironment() {
        Environment env = new NestedMap();
        Primitives.loadPrimitives(env);
        Predefined.loadPredefined(env);
        return env;
    }

    public static String readInput() {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        StringBuilder builder = new StringBuilder();
        String line;
        boolean first = true;
        int parenthesesBalance = 0;
        do {
            System.out.print(first ? "mylisp> " : "mylisp# ");
            line = scanner.nextLine();
            builder.append(String.format("%s%n", line));
            first = false;
            parenthesesBalance += parenthesesBalance(line);
        } while (!line.isEmpty() && parenthesesBalance != 0);
        return builder.toString();
    }

    private static int parenthesesBalance(String line) {
        int balance = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '(') balance += 1;
            else if (c == ')') balance -= 1;
        }
        return balance;
    }

    public static void main(String[] args) {
        Environment environment = createInitialEnvironment();
        while (true) {
            String input = readInput();
            if (":exit".equals(input)) break;
            try {
                Parser parser = new Parser(new StringLexer(input));
                SExpression sexpr = parser.sexpr();
                SExpression result = sexpr.eval(environment);
                System.out.printf(">>>>>>> %s%n", result);
            } catch (LexerError | ParserError | EvaluationError ex) {
                System.out.printf("~~~~~~~ %s%n", ex.getMessage());
            }
        }
    }
}
