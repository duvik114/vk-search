import tokensSplitter.TokensSplitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*try {
            Files.readAllLines(Paths.get("input2.txt"))
                    .forEach(s -> {
                                if (!s.isEmpty()) {
                                    List<String> list = TokensSplitter.splitIntoTokens(s);
                                    try {
                                        Files.writeString(
                                                Paths.get("input.txt"),
                                                String.join(" ", list.subList(1, (int)(list.size() / 2.2))) + "\n", StandardOpenOption.APPEND);
                                    } catch (Exception e) {
                                        System.out.println(s);
                                    }
                                }
                            }
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        AutoFiller autoFiller = new AutoFiller();
        autoFiller.setMaxErrors(0.25);
        autoFiller.printHints("what", "input.txt", "output.txt");
    }
}
