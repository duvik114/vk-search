package storage;

import storage.HashStorage.StorageImpl;
import storage.TrieStorage.TrieStorage;

import java.util.List;

public class StorageManager {
    TrieStorage trieStorage;
    StorageImpl storageImpl;

    public StorageManager() {
        trieStorage = new TrieStorage();
        storageImpl = new StorageImpl();
    }

    public String[] getFill(String pattern) {
        return trieStorage.getMatchedStrings(pattern);
    }

    public void setAnswers(List<String> answers) {
        trieStorage.addStrings(answers);
    }
}
