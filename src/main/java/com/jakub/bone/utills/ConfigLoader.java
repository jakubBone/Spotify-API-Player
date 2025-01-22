package com.jakub.bone.utills;

import com.jakub.bone.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    //Properties object to hold all configuration key-value pairs
    private static final Properties properties = new Properties();

    // ConfigLoader load and provide access to configuration properties from the 'config.properties'
    // Static bloc initializing the Properties object when the class is loaded
    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new InvalidConfigurationException("Missing 'config.properties' in classpath");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new InvalidConfigurationException("Error during file configuration", ex);
        }
    }

    // Retrieves the value of a given configuration key
    public static String get(String key) {
        // Fetch the property value based on the provided key
        String value = properties.getProperty(key);

        if(value == null || value.isEmpty()) {
            throw new InvalidConfigurationException("Missing configuration for key: " + key);
        }
        return properties.getProperty(key);
    }
}
