package storage;

import java.util.List;

/**
 * <code>Interface</code> of all storages in {@link StorageManager}
 *
 * @author Nikita Beliaev
 */
public interface Storage {
    /**
     * function to add given {@link String} to storage
     *
     * @param string answer to add
     */
    void addAnswer(String string);

    /**
     * function to get the most suitable hints
     *
     * @param pattern {@link String} get the most suitable hints for <code>pattern</code>
     * @return {@link List<String>} of most suitable hints
     */
    List<String> getMatchedStrings(String pattern);
}
