package checkWords;

import checkWords.levenshtein.LevenshteinStatement;
import checkWords.trie.TrieOfWordsNode;

public class Statement {
    private TrieOfWordsNode trieOfWordsNode;
    private LevenshteinStatement levenshteinStatement;
    private String string;

    public Statement(TrieOfWordsNode trieOfWordsNode, LevenshteinStatement levenshteinStatement, String string) {
        this.trieOfWordsNode = trieOfWordsNode;
        this.levenshteinStatement = levenshteinStatement;
        this.string = string;
    }

    public TrieOfWordsNode getTrieOfWordsNode() {
        return trieOfWordsNode;
    }

    public void setTrieOfWordsNode(TrieOfWordsNode trieOfWordsNode) {
        this.trieOfWordsNode = trieOfWordsNode;
    }

    public LevenshteinStatement getLevenshteinStatement() {
        return levenshteinStatement;
    }

    public void setLevenshteinStatement(LevenshteinStatement levenshteinStatement) {
        this.levenshteinStatement = levenshteinStatement;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
