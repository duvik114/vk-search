import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test implements Testable {/*
    private final AutoFiller autoFiller;*/
    private final String outputFile;
    private final double maxErrors;
    private final String hintsFile;
    private final String question;

    private static int count = 1;

    public Test(String hintsFile, String outputFile, String question, double maxErrors) {/*
        this.autoFiller = new AutoFiller();*/
        this.outputFile = outputFile;
        this.maxErrors = maxErrors;
        this.hintsFile = hintsFile;
        this.question = question;

        try {
            if (!Files.exists(Paths.get(outputFile))) {
                Files.createFile(Paths.get(outputFile));
            }
        } catch (IOException e) {
            System.err.println("Error while creating output file: " + e.getMessage());
        }
    }

    @Override
    public void test() {
        AutoFiller autoFiller = new AutoFiller();
        autoFiller.setMaxErrors(maxErrors);
        autoFiller.setup(hintsFile);
        autoFiller.printHints(question, outputFile);
        try {
            System.out.println("Test" + (count++) + " output: ");
            Files.readAllLines(Paths.get(outputFile)).forEach(System.out::println);
            System.out.println();
            System.out.println("=====================================================================================");
        } catch (IOException e) {
            System.err.println("Error while testing: " + e.getMessage());
        }
    }
}
