package tech.noetzold.APItester.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TokenApp {

    public static String getTokenPass() {
        Properties props = new Properties();
        FileInputStream file = null;
        try {
            file = new FileInputStream(
                    "./src/main/resources/application.properties");
            props.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return props.getProperty("app.token");

    }
}
