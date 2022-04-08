package storage;

import java.util.List;

public interface Storage {
    public void addStrings(List<String> strings);
    public String[] getMatchedStrings(String pattern);
}
