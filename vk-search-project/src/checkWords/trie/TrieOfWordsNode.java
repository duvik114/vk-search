package checkWords.trie;

import java.util.HashMap;

/**
 * Node if {@link TrieOfWords} structure (dictionary)
 *
 * @author Nikita Beliaev
 */
public class TrieOfWordsNode {
    private final HashMap<Character, TrieOfWordsNode> transitions;
    private boolean isFinal;

    public TrieOfWordsNode() {
        this.transitions = new HashMap<>();
        this.isFinal = false;
    }

    public void addTransition(char ch, TrieOfWordsNode trieOfWordsNode) {
        transitions.put(ch, trieOfWordsNode);
    }

    public TrieOfWordsNode getTransition(char ch) {
        return transitions.get(ch);
    }

    public HashMap<Character, TrieOfWordsNode> getTransitions() {
        return transitions;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }
}
