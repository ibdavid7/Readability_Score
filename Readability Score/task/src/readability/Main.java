package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//This code is not mine and was was heavily inspired by https://github.com/rabestro

public class Main {
    public static void main(String[] args) {

        try {
            new App(
                    new TextAnalysis(
                            Files.readString(
                                    Path.of(args[0])))).run();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
