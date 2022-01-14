package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class TestData {

    private static ISettingsFile environment = new JsonSettingsFile("testData.json");

    public static String get(String key) {
        return environment.getValue("/" + key).toString();
    }
}
