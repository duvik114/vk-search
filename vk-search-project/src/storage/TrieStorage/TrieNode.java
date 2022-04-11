package storage.TrieStorage;

import tokensSplitter.TokensSplitter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Node of the Trie structure in {@link TrieStorage}
 * It has a link to {@link List<String>} of answers (<code>answers</code>)
 * which words are written on each edge (presented in <code>children</code> {@link Map})
 * Each node also has no more, then <code>numberOfResults</code> best answers {@link TreeSet<Integer>} <code>strings</code>
 * as indexes of <code>answers</code> list
 * Also there is a flag <code>isFinal</code> to mark each final node
 *
 * @author Nikita Beliaev
 */
public class TrieNode {
    private final ArrayList<String> answers;
    private final Map<String, TrieNode> children;
    private final TreeSet<Integer> strings;
    private boolean isFinal;

    private static final int numberOfResults = 5;

    public TrieNode(ArrayList<String> answers) {
        this.answers = answers;
        this.children = new HashMap<>();
        this.strings = new TreeSet<>((Integer i1, Integer i2) -> {
            List<String> answer1Words = TokensSplitter.splitIntoTokens(answers.get(i1));
            List<String> answer2Words = TokensSplitter.splitIntoTokens(answers.get(i2));
            return Double.valueOf(answer2Words.get(answer2Words.size() - 1))
                    .compareTo(Double.valueOf(answer1Words.get(answer1Words.size() - 1)));
        } );
    }

    /**
     * function to add new edge and node by token (word)
     *
     * @param token word to write on edge
     * @param trieNode {@link TrieNode} where to add new edge
     * @param answerNumber index of answer in <code>answers</code>
     */
    public void addChild(String token, TrieNode trieNode, int answerNumber) {
        children.put(token, trieNode);
        strings.add(answerNumber);
        removeExtraStrings();
    }

    // returns null if map have not the given token

    /**
     * get Node by token (word)
     *
     * @param token word to use as a key
     * @return {@link TrieNode} or null if map <code>children</code> doesn't have the given token
     */
    public TrieNode getChild(String token) {
        return children.get(token);
    }

    public Map<String, TrieNode> getChildren() {
        return children;
    }

    public List<String> getStrings() {
        return strings.stream().map(answers::get).collect(Collectors.toList());
    }

    public boolean isFinal() {
        return isFinal;
    }

    /**
     * function to mark the current {@link TrieNode} as final
     * Each final node has his answer (as index in <code>answers</code>)
     *
     * @param answerNumber index of answer
     */
    public void setFinal(int answerNumber) {
        isFinal = true;
        strings.add(answerNumber);
        removeExtraStrings();
    }

    /**
     * function to keep the size of <code>strings</code> (no more than <code>numberOfResults</code>)
     * only answers with the best ratings are left
     */
    private void removeExtraStrings() {
        while (strings.size() > numberOfResults) {
            strings.pollLast();
        }
    }
}
