import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static final List<Testable> tests = new ArrayList<>();

    public static void main(String[] args) {
        tests.add(new Test(
                "tests/test01_input.txt",
                "tests/test01_output.txt",
                "",
                0.25
        ));

        tests.add(new Test(
                "tests/test02_input.txt",
                "tests/test02_output.txt",
                "",
                0.25
        ));

        tests.add(new Test(
                "tests/test01_input.txt",
                "tests/test03_output.txt",
                "abc",
                0.25
        ));

        tests.add(new Test(
                "tests/test04_input.txt",
                "tests/test04_output.txt",
                "sas",
                0.25
        ));

        tests.add(new Test(
                "tests/test05_input.txt",
                "tests/test05_output.txt",
                "asd",
                0.25
        ));

        tests.add(new Test(
                "tests/test06_input.txt",
                "tests/test06_output.txt",
                "ada mada",
                0.25
        ));

        tests.add(new Test(
                "tests/test07_input.txt",
                "tests/test07_output.txt",
                "01101 1001001 0101",
                0.25
        ));

        tests.add(new Test(
                "tests/test08_input.txt",
                "tests/test08_output.txt",
                "cheeseburger",
                0.25
        ));

        tests.add(new Test(
                "tests/test09_input.txt",
                "tests/test09_output.txt",
                "sdfbakjsbakjsdbkajsbkdjasbkdjashdbasjdkjfjsbdfjsbdkfjsbdhfbsdkfjb",
                1
        ));

        System.out.println("=====================================================================================");

        tests.forEach(Testable::test);

        System.out.println("All tests successfully passed!");
    }
}
