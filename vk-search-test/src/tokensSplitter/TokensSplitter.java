package tokensSplitter;

import java.util.Arrays;
import java.util.List;

public class TokensSplitter {
    public static List<String> splitIntoTokens(String s) {
        List<String> res = Arrays.asList(s.split("\\s+"));
        if (res.stream().allMatch(String::isEmpty)) {
            res = List.of("");
        } else {
            res.removeIf(String::isEmpty);
        }
        return res;
    }
}
