package readability;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    private final TextAnalysis textAnalysis;

    public App(TextAnalysis textAnalysis) {
        this.textAnalysis = textAnalysis;
    }

    void run() {

        System.out.println(textAnalysis.toString());

        System.out.format("Enter the score you want to calculate (%s, all): ",
                Stream.of(ReadabilityScoresEnum.values())
                    .map(Enum::toString)
                    .collect(Collectors.joining(", ")));

        System.out.println();

        final String rsMethod = new Scanner(System.in).next().toUpperCase();
        final boolean rsAllMethods = rsMethod.equals(ReadabilityScoresEnum.ALL);

        Stream.of(ReadabilityScoresEnum.values())
                .filter(rsEnum -> rsAllMethods || rsEnum.name().equals(rsMethod))
                .peek(rsEnum -> System.out.println(rsEnum.indexAndScoreToString(textAnalysis)))
                .mapToDouble(rsEnum -> rsEnum.getAge(rsEnum.getScore(textAnalysis)))
                .average()
                .ifPresentOrElse(this::success, this::fail);

    }

    public void success(double ageAverage) {
        System.out.format("\nThis text should be understood on average by %.2f year olds.", ageAverage);
    }

    public void fail() {
        System.out.format("Error: Wrong name of Readability Score Method");
    }
}
