package cat.udl.eps.mylisp.main;

import cat.udl.eps.mylisp.altreader.Lexer;
import cat.udl.eps.mylisp.altreader.LexerError;
import cat.udl.eps.mylisp.altreader.Token;

import java.util.Scanner;

import static cat.udl.eps.mylisp.altreader.Token.Type.EOF;

public class ListLexerMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();
            try {
                Lexer lexer = new Lexer(line);
                Token t = lexer.nextToken();
                while (t.type != EOF) {
                    System.out.println(t);
                    t = lexer.nextToken();
                }
                System.out.println(t); // EOF
            } catch (LexerError ex) {
                System.out.println(ex.getMessage());
            }

        }
    }
}
