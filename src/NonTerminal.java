import java.util.ArrayList;
import java.util.List;

public class NonTerminal {
    String name;
    String inhAttrs;
    String synthAttrs;
    List<Branch> branches = new ArrayList<>();

    private static String strip(String attrs) {
        return attrs == null ? "" : attrs.substring(1, attrs.length() - 1);
    }

    NonTerminal(String name, String inh, String synth) {
        this.name = name;
        this.inhAttrs = strip(inh);
        this.synthAttrs = strip(synth);
    }

    public static class Branch {
        List<Symb> symbs = new ArrayList<>();
        String synthCode;

        Branch(String synthCode) {
            this.synthCode = strip(synthCode);
        }

        public static class Symb {
            String name;
            String inhAttrsCall;

            Symb(String name, String inhAttrsCall) {
                this.name = name;
                this.inhAttrsCall = strip(inhAttrsCall);
            }
        }
    }
}
