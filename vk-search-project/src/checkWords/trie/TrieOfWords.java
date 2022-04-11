package checkWords.trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <code>Class</code> Trie structure (dictionary)
 *
 * @author Nikita Beliaev
 */
public class TrieOfWords {
    private final TrieOfWordsNode trieOfWords;

    public TrieOfWords() {
        trieOfWords = new TrieOfWordsNode();
    }

    /**
     * add words from {@link Collection<String>} to Trie
     *
     * @param words {@link Collection<String>} to add
     */
    public void addWords(Collection<String> words) {
        words.forEach(word -> {
            TrieOfWordsNode curNode = trieOfWords;
            for (char ch : word.toCharArray()) {
                if (curNode.getTransition(ch) == null) {
                    curNode.addTransition(ch, new TrieOfWordsNode());
                }
                curNode = curNode.getTransition(ch);
            }
            curNode.setFinal(true);
        });
    }

    /**
     * function for checking if the word is correct
     * (if the word is in the Trie)
     *
     * @param word {@link String} to check
     * @return <code>boolean</code>
     */
    public boolean checkWord(String word) {
        TrieOfWordsNode curNode = trieOfWords;
        for (char ch : word.toCharArray()) {
            if (curNode.getTransition(ch) == null) {
                return false;
            }
            curNode = curNode.getTransition(ch);
        }
        return curNode.isFinal();
    }

    /**
     * function to check for sticky words
     *
     * @param word incorrect {@link String} to split
     * @return {@link List<String>} of split words or empty {@link List} if it is not possible
     */
    public List<String> checkMissedSpace(String word) {
        List<String> splitWords = new ArrayList<>();
        TrieOfWordsNode curNode = trieOfWords;
        StringBuilder curWord = new StringBuilder();
        for (char ch : word.toCharArray()) {
            if (curNode.getTransition(ch) == null) {
                if (curNode.isFinal()) {
                    splitWords.add(curWord.toString());
                    curNode = trieOfWords;
                    curWord.setLength(0);
                    if (curNode.getTransition(ch) == null) {
                        return Collections.emptyList();
                    }
                } else {
                    return Collections.emptyList();
                }
            }
            curNode = curNode.getTransition(ch);
            curWord.append(ch);
        }
        splitWords.add(curWord.toString());
        return splitWords;
    }

    public TrieOfWordsNode getTrieOfWordsNode() {
        return trieOfWords;
    }

    public boolean isFinal(TrieOfWordsNode node) {
        return node.isFinal();
    }
}
