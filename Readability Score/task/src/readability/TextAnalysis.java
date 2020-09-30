package readability;

import java.util.stream.Stream;

public class TextAnalysis {

    private final long NOT_CALCULATED = -1;
    private final String text;

    private long words = NOT_CALCULATED;
    private long sentences = NOT_CALCULATED;
    private long characters = NOT_CALCULATED;
    private long syllables = NOT_CALCULATED;
    private long polysyllables = NOT_CALCULATED;


    public TextAnalysis(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public Stream<String> getStreamOfWords() {
        return Stream.of(text.split("\\s+"));
    }

    public long getWords() {
        if (words == NOT_CALCULATED) {
            words = getStreamOfWords()
                    .count();
        }
        return words;
    }

    public long getSentences() {
        if (sentences == NOT_CALCULATED) {
            sentences = text.split("[?!.]+").length;
        }
        return sentences;
    }

    public long getCharacters() {
        if (characters == NOT_CALCULATED) {
            characters = text.replaceAll("\\s+", "").length();
        }
        return characters;
    }

    public long countSyllablesInWord(final String word) {
        return Math.max(1, word.toLowerCase()
                .replaceAll("e$", "")   //if word end with -e, it's not syllable e.g. side
                .replaceAll("[aeiouy]{2,}", "a")  //if two or more vowels together ignore subsequent ones e.g. booze
                .replaceAll("[^aeiouy]", "") //delete all non-vowels
                .length());
    }

    public long getSyllables() {
        if (syllables == NOT_CALCULATED) {
            syllables = getStreamOfWords()
                    .mapToLong(this::countSyllablesInWord)
                    .sum();
        }
        return syllables;
    }

    public long getPolysyllables() {
        if (polysyllables == NOT_CALCULATED) {
            polysyllables = getStreamOfWords()
                    .filter(this::isPolysyllable)
                    .count();
        }
        return polysyllables;
    }

    public boolean isPolysyllable(final String word) {
        return countSyllablesInWord(word) > 2;
    }

    @Override
    public String toString() {
        return String.format(String.join("\n",
                "This text is: \n%s\n",
                "Words: %d",
                "Sentences: %d",
                "Characters: %d",
                "Syllables: %d",
                "Polysyllables: %d"),
                getText(), getWords(), getSentences(), getCharacters(), getSyllables(), getPolysyllables());
    }
}
