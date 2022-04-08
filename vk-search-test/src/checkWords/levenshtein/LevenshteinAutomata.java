package checkWords.levenshtein;

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
