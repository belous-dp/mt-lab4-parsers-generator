import generated.GrammarLexer;
import generated.GrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParserGenerator {
    private final Path grammarPath, outDir;

    ParserGenerator(String grammarPath, String outDir) throws FileNotFoundException {
        this.grammarPath = Path.of(grammarPath);
        if (!Files.exists(this.grammarPath)) {
            throw new FileNotFoundException("Grammar file not found: " + grammarPath);
        }
        this.outDir = Path.of(outDir);
        if (!Files.exists(this.outDir)) {
            throw new FileNotFoundException("Output directory not found: " + outDir);
        }
    }

    public void run() throws IOException {
        var inpParser = new GrammarParser(
                new CommonTokenStream(new GrammarLexer(CharStreams.fromPath(grammarPath)))
        );
        var inpVisitor = new InputGrammarVisitor();
        inpVisitor.visit(inpParser.lang());

        var ll1m = checkLL1();
        if (ll1m != null) {
            throw new InvalidGrammarException("grammar is not an LL(1): " + ll1m);
        }

        createTokensEnum();
        createLexer();
        createParser();
    }

    private void createTokensEnum() {

    }

    private void createParser() {

    }

    private void createLexer() {

    }

    private String checkLL1() {
        return null;
    }

}
