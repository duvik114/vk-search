package checkWords.levenshtein;

/**
 * Statement for dfs in {@link LevenshteinAutomata}
 *
 * @author Nikita Beliaev
 */
public class LevenshteinStatement {
    private int errors;
    private int symbol;

    public LevenshteinStatement(int errors, int symbol) {
        this.errors = errors;
        this.symbol = symbol;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
}
