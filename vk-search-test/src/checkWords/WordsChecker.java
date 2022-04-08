package checkWords;

import checkWords.levenshtein.LevenshteinAutomata;
import checkWords.levenshtein.LevenshteinStatement;
import checkWords.trie.TrieOfWords;
import tokensSplitter.TokensSplitter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// missed space
// prefix

public class WordsChecker {
    private final TrieOfWords trie;
    private LevenshteinAutomata automata;
    private final double maxErrors;

    public WordsChecker(double maxErrors) {
        this.trie = new TrieOfWords();
        this.maxErrors = maxErrors;
    }

    public void addWordsToTrie(List<String> strings) {
        strings.forEach(s -> trie.addWords(TokensSplitter.splitIntoTokens(s)));
    }

    public boolean checkWord(String word) {
        return trie.checkWord(word);
    }

    public Set<String> correctWord(String word) {
        Set<String> strings = new HashSet<>();
        automata = new LevenshteinAutomata(maxErrors, word);
        ArrayList<Statement> statements =
                new ArrayList<>(List.of(
                        new Statement(trie.getTrieOfWordsNode(), new LevenshteinStatement(0, 0), "")
                ));
        while (!statements.isEmpty()) {
            ArrayList<Statement> newStatements = new ArrayList<>();
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

    public List<Statement> updateStatement(Statement statement) {
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
}
