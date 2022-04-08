import checkWords.WordsChecker;
import storage.StorageManager;
import tokensSplitter.TokensSplitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AutoFiller {
    private double maxErrors;
    private final StorageManager storageManager;
    private final int numberOfResults;

    public AutoFiller() {
        this.maxErrors = 0.25;
        this.storageManager = new StorageManager();
        this.numberOfResults = 5;
    }

    public void printHints(String request, String hintsFile, String outputFile) {
        WordsChecker wordsChecker = new WordsChecker(maxErrors);
        StorageManager autoFilling = new StorageManager();
        Path inputFile = Paths.get(hintsFile);

        try {
            List<String> answers = Files.readAllLines(inputFile);
            wordsChecker.addWordsToTrie(answers);
            autoFilling.setAnswers(answers);
        } catch (IOException e) {
            System.err.println("Error during reading file: " + inputFile + " " + e.getMessage());
            return;
        }

        List<StringBuilder> correctedStrings = new ArrayList<>();
        correctedStrings.add(new StringBuilder());
        for (String word : TokensSplitter.splitIntoTokens(request)) {
            if (!wordsChecker.checkWord(word)) {
                int listSize = correctedStrings.size();
                List<String> correctedWords = new ArrayList<>(List.of(word));
                correctedWords.addAll(wordsChecker.correctWord(word));

                // will return wrong word if it's too wrong or request is empty
                if (correctedWords.isEmpty()) {
                    correctedStrings.forEach(s -> s.append(word).append(" "));
                    continue;
                }

                for (int i = 1; i < correctedWords.size(); i++) {
                    for (int j = 0; j < listSize; j++) {
                        correctedStrings.add(new StringBuilder(correctedStrings.get(j)));
                    }
                }
                int curPos = 0;
                for (String cs : correctedWords) {
                    for (int i = 0; i < listSize; i++) {
                        correctedStrings.get(curPos + i).append(cs).append(" ");
                    }
                    curPos += listSize;
                }
            } else {
                correctedStrings.forEach(s -> s.append(word).append(" "));
            }
        }

        BufferedWriter writer;
        try {
            int count = 0;
            writer = new BufferedWriter(new FileWriter(outputFile));
            for (String cs : correctedStrings
                    .stream()
                    .map(String::new)
                    .collect(Collectors.toList())
                    /*.stream()
                    .sorted(Comparator.comparing(String::length))
                    .collect(Collectors.toList())*/) {
                for (String s : autoFilling.getFill(cs)) {
                    if (count++ > numberOfResults) {
                        writer.close();
                        return;
                    }
                    writer.write(s);
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getMaxErrors() {
        return maxErrors;
    }

    public void setMaxErrors(double maxErrors) {
        this.maxErrors = maxErrors;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }
}
