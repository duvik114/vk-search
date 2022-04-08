package storage.HashStorage;

import java.util.*;

public class StorageImpl {

    private final Map<String, PriorityQueue<TokenPosition>> tokenStorage;

    public StorageImpl() {
        tokenStorage = new HashMap<>();
    }

    public void addStrings(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            List<String> tokens = getTokens(strings.get(i));
            addTokens(tokens, i);
        }
    }

    public String[] getMatchingStrings(String pattern) {
        List<String> patternTokens = getTokens(pattern);
        return new String[0];
    }

    private void addTokens(List<String> tokens, int variantNumber) {
        for (int i = 0; i < tokens.size(); i++) {
            if (!tokenStorage.containsKey(tokens.get(i))) {
                tokenStorage.put(tokens.get(i), new PriorityQueue<>());
            }

            tokenStorage.get(tokens.get(i)).add(new TokenPosition(variantNumber, i));
        }
    }

    private List<String> getTokens(String s) {
        List<String> res = Arrays.asList(s.split("\\s+"));
        res.removeIf(String::isEmpty);
        return res;
    }
}
