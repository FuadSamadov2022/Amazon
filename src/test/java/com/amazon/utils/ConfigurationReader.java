package com.amazon.utils;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationReader {

    private static Properties properties;
    private static Logger testLogger = LogManager.getLogger(ConfigurationReader.class.getSimpleName());

    static {
        try {
            // LOAD GENERAL PROPERTIES
            String path = "configuration.properties";
            String environment;
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            // LOAD ENVIRONMENT SPECIFIC PROPERTIES
            if (System.getProperty("env") != null) {
                environment = System.getProperty("env");
            } else {
                environment = properties.getProperty("env");
            }
            path = "src/test/resources/environments/" + environment + ".properties";

            testLogger.info("Executing tests in " + environment.toUpperCase() + " environment!");

            input = new FileInputStream(path);
            properties.load(input);
            input.close();
        } catch (Exception e) {
            testLogger.error(e.getMessage());
        }
    }

    public static String get(String keyName) {
        testLogger.info("About to get key = " + keyName);
        return properties.getProperty(keyName);
    }
}
