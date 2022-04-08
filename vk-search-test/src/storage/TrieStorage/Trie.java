package storage.TrieStorage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Trie {
    private Map<String, Trie> children;
    private TreeSet<String> strings;
    private boolean isFinal;

    private final int numberOfResults = 5;

    public Trie() {
        this.children = new HashMap<>();
        this.strings = new TreeSet<>();
    }

    public void addChild(String token, Trie trie, String suffix) {
        children.put(token, trie);
        strings.add(suffix);
        if (strings.size() > numberOfResults) {
            strings.remove(strings.stream().max(Comparator.comparing(String::length)).get());
        }
    }

    // returns null if map have not the given token
    public Trie getChild(String token) {
        return children.get(token);
    }

    public Map<String, Trie> getChildren() {
        return children;
    }

    public void setChildren(Map<String, Trie> children) {
        this.children = children;
    }

    public TreeSet<String> getStrings() {
        if (isFinal) {
            strings.add("");
        }
        return strings;
    }

    public void setStrings(TreeSet<String> strings) {
        this.strings = strings;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }
}
