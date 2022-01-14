package utils;

import java.util.ArrayList;
import java.util.Collections;

public class DataGenerator {

    public static String generateText(int minLength) {
        ArrayList<String> generatedParts = new ArrayList<>();

        while(generatedParts.size() < minLength) {
            generatedParts.add(generateUpperCaseLetter());
            generatedParts.add(generateLowerCaseLetter());
            generatedParts.add(generateNumber());
        }

        Collections.shuffle(generatedParts);

        return String.join("", generatedParts);
    }

    private static String generateUpperCaseLetter() {
        return Character.toString((char) RandomUtility.randomValue('A', 'Z' + 1));
    }

    private static String generateLowerCaseLetter() {
        return Character.toString((char) RandomUtility.randomValue('a', 'z' + 1));
    }

    private static String generateNumber() {
        return String.valueOf(RandomUtility.randomValue(0, 10));
    }
}
