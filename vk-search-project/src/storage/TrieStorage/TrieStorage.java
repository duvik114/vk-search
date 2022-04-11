package storage.TrieStorage;

import storage.Storage;
import tokensSplitter.TokensSplitter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link Storage} based on Trie and list with links to answers
 * This structure allow us to do fast search on big amounts of data (answers)
 *
 * @author Nikita Beliaev
 */
public class TrieStorage implements Storage {

    private final TrieNode trieNode;
    private final ArrayList<String> answers;

    private final int numberOfResults = 5;

    public TrieStorage() {
        this.answers = new ArrayList<>();
        this.trieNode = new TrieNode(answers);
    }

    /**
     * function to add answer
     *
     * @param string answer to add
     */
    @Override
    public void addAnswer(String string) {
        answers.add(string);
        List<String> words = TokensSplitter.splitIntoTokens(string);
        addTokens(words.subList(0, words.size() - 1), answers.size() - 1);
    }

    private void addTokens(List<String> tokens, int answerNumber) {
        TrieNode curTrieNode = trieNode;
        for (String token : tokens) {
            if (curTrieNode.getChild(token) == null) {
                curTrieNode.addChild(token, new TrieNode(answers), answerNumber);
            }
            curTrieNode = curTrieNode.getChild(token);
        }
        curTrieNode.setFinal(answerNumber);
    }

    /**
     * function to get the most suitable hints
     *
     * @param pattern {@link String} get the most suitable hints for <code>pattern</code>
     * @return {@link List<String>} of most suitable hints
     */
    @Override
    public List<String> getMatchedStrings(String pattern) {
        return getMatchedStringList(pattern)
                .stream()
                .map(s -> {
                    List<String> words = TokensSplitter.splitIntoTokens(s);
                    return String.join(" ", words.subList(0, words.size() - 1));
                })
                .collect(Collectors.toList());
    }

    /**
     * function to get the most suitable hints with {@link Double} ratings
     *
     * @param pattern {@link String} get the most suitable hints for <code>pattern</code>
     * @return {@link List<String>} of most suitable hints
     */
    private List<String> getMatchedStringList(String pattern) {
        List<String> patternTokens = TokensSplitter.splitIntoTokens(pattern);
        TrieNode curTrieNode = trieNode;
        for (String patternToken : patternTokens) {
            // to return most common by prefix
            if (curTrieNode.getChild(patternToken) == null) {
                return prefixReturn(curTrieNode, patternToken);
            }
            curTrieNode = curTrieNode.getChild(patternToken);
        }
        return curTrieNode.getStrings();
    }

    /**
     * function to search answers which substrings from <code>curNode</code> have the <code>patternToken</code>
     * as prefix
     *
     * @param curTrieNode Node of {@link TrieNode} from where to search for prefixes equals to <code>patternToken</code>
     * @param patternToken the last word of request
     * @return {@link List<String>} answers which prefixes with request are common
     */
    private List<String> prefixReturn(TrieNode curTrieNode, String patternToken) {
        List<String> res = new ArrayList<>();
        for (Map.Entry<String, TrieNode> entry : curTrieNode.getChildren().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(String::length)))
                .collect(Collectors.toList())) {
            if (entry.getKey().startsWith(patternToken)) {
                res.addAll(entry.getValue().getStrings());
                if (res.size() > numberOfResults) {
                    return res.subList(0, numberOfResults);
                }
            }
        }
        return res;
    }
}
