import tokensSplitter.TokensSplitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        /*try {
            LinkedHashSet<String> wordsSet = new LinkedHashSet<>();

            Files.readAllLines(Paths.get("bigData.txt"))
                    .forEach(s -> {
                                if (!s.isEmpty()) {
                                    List<String> list = TokensSplitter.splitIntoTokens(s);
                                    List<String> subList = list.subList(1, (int) (list.size() / 2.0) - 1);
                                    wordsSet.addAll(subList);
                                }
                            }
                    );

            try {
                Random random = new Random();
                ArrayList<String> words = new ArrayList<>(wordsSet);

                for (int i = 0; i < 111111; i++) {
                    StringBuilder string = new StringBuilder();
                    int wordSize = 1 + random.nextInt(9);
                    for (int j = 0; j < wordSize; j++) {
                        string.append(words.get(random.nextInt(words.size()))).append(" ");
                    }

                    string.append(random.nextInt(1000000000));

                    Files.writeString(
                            Paths.get("input.txt"),
                            string + "\n",
                            StandardOpenOption.APPEND);
                }
            } catch (Exception e) {
                System.out.println("Error here) ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // This is an example of using AutoFiller
        double maxErrorPercent = 0.25;
        String hintsFileName = "input.txt";
        String outputFileName = "output.txt";
        String request = "";

        AutoFiller autoFiller = new AutoFiller();
        autoFiller.setMaxErrors(maxErrorPercent);
        autoFiller.setup(hintsFileName);
        System.out.println("Setup successfully finished!");

        autoFiller.printHints(request, outputFileName);
    }
}
