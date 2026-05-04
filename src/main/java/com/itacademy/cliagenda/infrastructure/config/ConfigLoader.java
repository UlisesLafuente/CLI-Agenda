package com.itacademy.cliagenda.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;

    public ConfigLoader() {
        this.properties = loadProperties();
    }

    public ConfigLoader(Properties testProperties) {
        this.properties = testProperties;
    }

    private Properties loadProperties() {
        Properties props = new Properties();

        String testUrl = System.getProperty("jdbc.url");
        String testUser = System.getProperty("jdbc.username");
        String testPass = System.getProperty("jdbc.password");

        if (testUrl != null) {
            props.setProperty("jdbc.url", testUrl);
            props.setProperty("jdbc.username", testUser != null ? testUser : "");
            props.setProperty("jdbc.password", testPass != null ? testPass : "");
            return props;
        }

        InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
        if (input == null) {
            input = getClass().getClassLoader().getResourceAsStream("com/itacademy/cliagenda/application/config/application.properties");
        }
        if (input == null) {
            throw new RuntimeException("Could not find application.properties");
        }
        try {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        } finally {
            try {
                input.close();
            } catch (IOException ignored) {
            }
        }
        return props;
    }

    public String getUrl() {
        return properties.getProperty("jdbc.url");
    }

    public String getUsername() {
        return properties.getProperty("jdbc.username");
    }

    public String getPassword() {
        return properties.getProperty("jdbc.password");
    }
}