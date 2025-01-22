package com.jakub.bone.utills;

import com.jakub.bone.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

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

    public static String get(String key) {
        String value = properties.getProperty(key);
        if(value == null || value.isEmpty()) {
            throw new InvalidConfigurationException("Missing configuration for key: " + key);
        }
        return properties.getProperty(key);
    }
}
