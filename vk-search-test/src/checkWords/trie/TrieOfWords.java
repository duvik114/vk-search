package checkWords.trie;

import java.util.Collection;

public class TrieOfWords {
    private final TrieOfWordsNode trieOfWords;

    public TrieOfWords() {
        trieOfWords = new TrieOfWordsNode();
    }

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

    public TrieOfWordsNode getTrieOfWordsNode() {
        return trieOfWords;
    }

    public boolean isFinal(TrieOfWordsNode node) {
        return node.isFinal();
    }
}
