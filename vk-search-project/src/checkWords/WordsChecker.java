package checkWords;

import checkWords.levenshtein.LevenshteinAutomata;
import checkWords.levenshtein.LevenshteinStatement;
import checkWords.trie.TrieOfWords;
import tokensSplitter.TokensSplitter;

import java.util.*;

/**
 * <code>Class</code> for checking orthography of words
 * {@link TrieOfWords} dictionary based on Trie to check if the word are correct
 * {@link LevenshteinAutomata} to correct words
 *
 * @author Nikita Beliaev
 */
public class WordsChecker {
    private final TrieOfWords trie;
    private LevenshteinAutomata automata;
    private final double maxErrors;

    public WordsChecker(double maxErrors) {
        this.trie = new TrieOfWords();
        this.maxErrors = maxErrors;
    }

    /**
     * function to add correct word to dictionary
     *
     * @param string word to add
     */
    public void addWordsToTrie(String string) {
        List<String> tokens = TokensSplitter.splitIntoTokens(string);
        trie.addWords(tokens.subList(0, tokens.size() - 1));
    }

    /**
     * check if the given word is correct
     * @param word {@link String} to check
     * @return <code>boolean</code>
     */
    public boolean checkWord(String word) {
        return trie.checkWord(word);
    }

    /**
     * function for correcting word
     * it uses standard algorithm with synchronized dfs on {@link LevenshteinAutomata}
     * and {@link TrieOfWords}. And returns the Set of correct variants sorted by
     * non-descending order of Levenshtein distance between incorrect
     * <code>word</code> and correct variants
     *
     * @param word incorrect {@link String} to correct
     * @return {@link Set<String>} with corrected variants of word
     */
    public Set<String> correctWord(String word) {
        Set<String> strings = new LinkedHashSet<>();

        String addedSpacesString;
        if (!(addedSpacesString = checkForMissedSpaces(word)).isEmpty()) {
            strings.add(addedSpacesString);
        }

        automata = new LevenshteinAutomata(maxErrors, word);
        Set<Statement> statements =
                new TreeSet<>(List.of(
                        new Statement(trie.getTrieOfWordsNode(), new LevenshteinStatement(0, 0), "")
                ));
        while (!statements.isEmpty()) {
            Set<Statement> newStatements = new TreeSet<>();
            for (Statement statement : statements) {
                List<Statement> updatedStatements = updateStatement(statement);
                for (Statement st : updatedStatements) {
                    if (automata.isFinal(st.getLevenshteinStatement()) && trie.isFinal(st.getTrieOfWordsNode())) {
                        strings.add(st.getString());
                    } else {
                        newStatements.add(st);
                    }
                }
            }
            statements = newStatements;
        }
        return strings;
    }

    /**
     * function to check if some spaces in word are missed
     * (can we split the incorrect word in 2 or more correct words)
     *
     * @param word {@link String} to check
     * @return joined correct strings or empty string if <code>word</code> is not splittable
     */
    private String checkForMissedSpaces(String word) {
        return String.join(" ", trie.checkMissedSpace(word));
    }

    private List<Statement> updateStatement(Statement statement) {
        ArrayList<Statement> result = new ArrayList<>();
        for (var transition : statement.getTrieOfWordsNode().getTransitions().entrySet()) {
            if (automata.isSymbolAcceptable(transition.getKey(), statement.getLevenshteinStatement())) {
                result.add(new Statement(transition.getValue(),
                        new LevenshteinStatement(
                                statement.getLevenshteinStatement().getErrors(),
                                statement.getLevenshteinStatement().getSymbol() + 1),
                        statement.getString() + transition.getKey()
                ));
            }
            if (automata.isErrorAcceptable(statement.getLevenshteinStatement())) {
                if (!automata.isFinal(statement.getLevenshteinStatement())) {
                    result.add(new Statement(transition.getValue(),
                            new LevenshteinStatement(
                                    statement.getLevenshteinStatement().getErrors() + 1,
                                    statement.getLevenshteinStatement().getSymbol() + 1),
                            statement.getString() + transition.getKey()
                    ));
                }
                result.add(new Statement(transition.getValue(),
                        new LevenshteinStatement(
                                statement.getLevenshteinStatement().getErrors() + 1,
                                statement.getLevenshteinStatement().getSymbol()),
                        statement.getString() + transition.getKey()
                ));
            }
        }
        if (automata.isErrorAcceptable(statement.getLevenshteinStatement())
                && !automata.isFinal(statement.getLevenshteinStatement())) {
            result.add(new Statement(statement.getTrieOfWordsNode(),
                    new LevenshteinStatement(
                            statement.getLevenshteinStatement().getErrors() + 1,
                            statement.getLevenshteinStatement().getSymbol() + 1),
                    statement.getString()
            ));
        }
        return result;
    }

    /**
     * function to check if correct word is split by spaces in request
     *
     * @param request {@link String} to check
     * @return {@link String} request with the most possible amount of merged incorrect words
     */
    public String mergeWords(String request) {
        StringBuilder res = new StringBuilder();
        StringBuilder curString = new StringBuilder();
        List<String> words = TokensSplitter.splitIntoTokens(request);
        for (String word : words) {
            if (!checkWord(word)) {
                curString.append(word);
                if (checkWord(curString.toString())) {
                    res.append(curString).append(" ");
                    curString.setLength(0);
                }
            } else {
                res.append(word).append(" ");
                curString.setLength(0);
            }
        }
        return res.append(curString).toString();
    }
}
