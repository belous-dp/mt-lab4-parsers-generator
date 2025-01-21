import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var inp = "U:\\5th semester\\TM\\lab4\\example\\calc.txt";
        var dir = "U:\\5th semester\\TM\\lab4\\example\\generated";
        try {
            new ParserGenerator(inp, dir).run();
        } catch (InvalidGrammarException e) {
            System.err.println("Provided grammar is invalid: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("An IO error occurred: " + e.getMessage());
        }
    }
}
