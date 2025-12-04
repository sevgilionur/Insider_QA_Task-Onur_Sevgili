package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            String path = System.getProperty("user.dir") + "/src/test/resources/global.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Global.properties file not loaded! Path: " + System.getProperty("user.dir") + "/src/test/resources/global.properties");
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Searhing '" + key + "' value is not found in global.properties file!");
        }
        return value;
    }
}