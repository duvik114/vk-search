package storage;

import storage.hashStorage.HashStorage;
import storage.TrieStorage.TrieStorage;

import java.util.List;

/**
 * Manager of all {@link Storage} implementations
 *
 * @author Nikita Beliaev
 */
public class StorageManager {
    TrieStorage trieStorage;
    HashStorage storageImpl;

    public StorageManager() {
        trieStorage = new TrieStorage();
        storageImpl = new HashStorage();
    }

    /**
     * function to get all hints from all {@link Storage} implementations
     *
     * @param pattern {@link String} with request
     * @return {@link List<String>} of hints
     */
    public List<String> getHints(String pattern) {
        return trieStorage.getMatchedStrings(pattern);
    }

    /**
     * add answer to {@link Storage} implementations
     *
     * @param answer answer
     */
    public void setAnswers(String answer) {
        trieStorage.addAnswer(answer);
    }
}
