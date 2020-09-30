package readability;

import java.util.function.ToDoubleFunction;

public enum  ReadabilityScoresEnum {
    ARI("Automated Readability Index",
            textAnalysis -> 4.71 * textAnalysis.getCharacters() / textAnalysis.getWords()
            + 0.5 * textAnalysis.getWords() / textAnalysis.getSentences() - 21.43),

    FK("Flesch–Kincaid readability tests",
            textAnalysis -> 0.39 * textAnalysis.getWords() / textAnalysis.getSentences() +
            11.8 * textAnalysis.getSyllables() / textAnalysis.getWords() - 15.59),

    SMOG("Simple Measure of Gobbledygook",
            textAnalysis -> 1.043 * Math.sqrt(textAnalysis.getPolysyllables() * 30 /
                    textAnalysis.getSentences()) + 3.1291),

    CL("Coleman–Liau index",
            textAnalysis -> 0.0588 * textAnalysis.getCharacters() / textAnalysis.getWords() * 100 -
                    0.296 * textAnalysis.getSentences() / textAnalysis.getWords() * 100 - 15.8);

    private final String fullName;
    private final ToDoubleFunction<TextAnalysis> scoreFormula;

    public static final String ALL = "ALL";

    ReadabilityScoresEnum(String fullName, ToDoubleFunction<TextAnalysis> scoreFormula) {
        this.fullName = fullName;
        this.scoreFormula = scoreFormula;
    }

    public String getFullName() {
        return this.fullName;
    }

    public double getScore(final TextAnalysis textAnalysis) {
        return scoreFormula.applyAsDouble(textAnalysis);
    }

    public int getAge(final double score) {
        int[] arr = {6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 25};
        int level = Math.max(1, Math.min(14, (int) Math.ceil(score))) - 1;
        return arr[level];
    }

    public String indexAndScoreToString(final TextAnalysis textAnalysis) {
        return String.format("%s: %.2f (about %d year olds).",
                getFullName(), getScore(textAnalysis), getAge(getScore(textAnalysis)));
    }

}
