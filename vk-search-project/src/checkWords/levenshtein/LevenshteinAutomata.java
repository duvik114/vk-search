package checkWords.levenshtein;

/**
 * <code>Class</code> LevenshteinAutomata which is build for incorrect word
 * Max errors count is calculated by <code>errorPercent</code> but cannot be more than 9
 *
 * @author Nikita Beliaev
 */
public class LevenshteinAutomata {
    private final double errorPercent;
    private final String mainWord;

    public LevenshteinAutomata(double errorPercent, String mainWord) {
        if (Math.ceil(mainWord.length() * errorPercent) > 9) {
            errorPercent = Math.floor(9.0 / mainWord.length());
        }
        this.errorPercent = errorPercent;
        this.mainWord = mainWord;
    }

    public boolean isFinal(LevenshteinStatement statement) {
        return statement.getSymbol() >= mainWord.length();
    }

    public boolean isErrorAcceptable(LevenshteinStatement statement) {
        return statement.getErrors() < Math.ceil(mainWord.length() * errorPercent);
    }

    public boolean isSymbolAcceptable(char ch, LevenshteinStatement statement) {
        return (!isFinal(statement)) && (mainWord.charAt(statement.getSymbol()) == ch);
    }

    public String getMainWord() {
        return mainWord;
    }
}
