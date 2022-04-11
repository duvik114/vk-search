package tokensSplitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for splitting Strings into words
 *
 * @author Nikita Beliaev
 */
public class TokensSplitter {
    /**
     * main function for splitting
     *
     * @param s {@link String} to split
     * @return {@link List<String>} of words or empty {@link String}
     */
    public static List<String> splitIntoTokens(String s) {
        List<String> res = new ArrayList<>(Arrays.asList(s.split("\\s+")));
        if (res.stream().allMatch(String::isEmpty)) {
            res = List.of("");
        } else {
            res.removeIf(String::isEmpty);
        }
        return res;
    }
}
