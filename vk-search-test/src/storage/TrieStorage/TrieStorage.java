package storage.TrieStorage;

import storage.Storage;
import tokensSplitter.TokensSplitter;

import java.util.*;
import java.util.stream.Collectors;

public class TrieStorage implements Storage {

    private final Trie trie;

    private final int numberOfResults = 5;

    public TrieStorage() {
        this.trie = new Trie();
    }

    @Override
    public void addStrings(List<String> strings) {
        for (String s : strings) {
            List<String> tokens = TokensSplitter.splitIntoTokens(s);
            addTokens(tokens);
        }
    }

    private void addTokens(List<String> tokens) {
        Trie curTrie = trie;
        for (int i = 0; i < tokens.size(); i++) {
            if (curTrie.getChild(tokens.get(i)) == null) {
                curTrie.addChild(tokens.get(i), new Trie(),
                        String.join(" ", tokens.subList(i, tokens.size())));
            }
            curTrie = curTrie.getChild(tokens.get(i));
        }
        curTrie.setFinal(true);
    }

    @Override
    public String[] getMatchedStrings(String pattern) {
        return getMatchedStringList(pattern)
                .stream()
                .sorted(Comparator.comparing(String::length))
                .toArray(String[]::new);
    }

    private List<String> getMatchedStringList(String pattern) {
        List<String> patternTokens = TokensSplitter.splitIntoTokens(pattern);
        Trie curTrie = trie;
        StringBuilder curString = new StringBuilder();
        for (String patternToken : patternTokens) {
            // to return at least something
            /*if (curTrie.getChild(patternToken) == null) {
                return curTrie.getStrings().stream()
                        .map(s -> s.isEmpty() ? curString.substring(0, curString.length() - 1) : curString + s)
                        .collect(Collectors.toList());
            }*/
            // to return nothing
            if (curTrie.getChild(patternToken) == null) {
                List<String> res = new ArrayList<>();
                for (Map.Entry<String, Trie> entry : curTrie.getChildren().entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey(Comparator.comparing(String::length)))
                        .collect(Collectors.toList())) {
                    if (entry.getKey().startsWith(patternToken)) {
                        for (var s : entry.getValue().getStrings()) {
                            res.add(curString + entry.getKey() + " " + s);
                        }
                        if (res.size() > numberOfResults) {
                            res = res.subList(0, numberOfResults);
                        }
                    }
                }
                return res;
            }
            curTrie = curTrie.getChild(patternToken);
            curString.append(patternToken).append(" ");
        }
        return curTrie.getStrings().stream()
                .map(s -> s.isEmpty() ? curString.substring(0, curString.length() - 1) : curString + s)
                .collect(Collectors.toList());
    }
}
