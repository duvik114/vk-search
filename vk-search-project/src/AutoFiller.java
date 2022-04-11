import checkWords.WordsChecker;
import storage.StorageManager;
import tokensSplitter.TokensSplitter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class for collecting data in storage and getting hints
 *
 * @author Nikita Beliaev
 */
public class AutoFiller {
    private double maxErrors;
    private final StorageManager storageManager;
    private final WordsChecker wordsChecker;
    private final int numberOfResults;

    public AutoFiller() {
        this.maxErrors = 0.25;
        this.wordsChecker = new WordsChecker(maxErrors);
        this.storageManager = new StorageManager();
        this.numberOfResults = 5;
    }

    /**
     * To <code>setup</code> and get data from hintsFile to {@link WordsChecker} and {@link StorageManager}
     *
     * @param hintsFile file with possible answers
     */
    public void setup(String hintsFile) {
        Path inputFile = Paths.get(hintsFile);

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(hintsFile));
            String stringLine = bufferedReader.readLine();
            while (stringLine != null) {
                wordsChecker.addWordsToTrie(stringLine);
                storageManager.setAnswers(stringLine);
                stringLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("Error during reading file: " + inputFile + " " + e.getMessage());
        }
    }

    /**
     * main function for getting the most suitable answers from {@link StorageManager} for given request
     * and writing them into <code>outputFile</code>
     *
     * @param request {@link String} with question
     * @param outputFile name of file where to put answers
     */
    public void printHints(String request, String outputFile) {
        if (request.replaceAll("\\s+", "").isEmpty()) {
            return;
        }

        LinkedHashSet<String> results = new LinkedHashSet<>();
        String mergedString;
        if (!(mergedString = wordsChecker.mergeWords(request)).isEmpty()) {
            results.addAll(storageManager.getHints(mergedString));
        }

        for (String cs : getCorrectStrings(request)
                .stream()
                .map(String::new)
                .collect(Collectors.toList())) {
            results.addAll(storageManager.getHints(cs));
            if (results.size() > numberOfResults) {
                break;
            }
        }

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            for (String s : results.stream().limit(numberOfResults).collect(Collectors.toList())) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * main function for checking orthography of words in request
     *
     * @param request  {@link String} with question
     * @return {@link List<StringBuilder>}
     */
    private List<StringBuilder> getCorrectStrings(String request) {
        List<StringBuilder> correctedStrings = new ArrayList<>();
        correctedStrings.add(new StringBuilder());
        for (String word : TokensSplitter.splitIntoTokens(request)) {
            if (!wordsChecker.checkWord(word)) {
                int listSize = correctedStrings.size();
                List<String> correctedWords = new ArrayList<>(List.of(word));
                correctedWords.addAll(wordsChecker.correctWord(word));

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

        return correctedStrings;
    }

    public void setMaxErrors(double maxErrors) {
        this.maxErrors = maxErrors;
    }

}
