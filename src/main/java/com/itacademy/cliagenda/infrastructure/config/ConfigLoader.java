package com.itacademy.cliagenda.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Cargador de configuración de la aplicación.
 * Lee las propiedades de conexión a la base de datos desde archivos de configuración
 * o variables del sistema.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
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

        String systemUrl = System.getProperty("jdbc.url");
        String systemUser = System.getProperty("jdbc.username");
        String systemPass = System.getProperty("jdbc.password");

        if (systemUrl != null) {
            props.setProperty("jdbc.url", systemUrl);
            props.setProperty("jdbc.username", systemUser != null ? systemUser : "");
            props.setProperty("jdbc.password", systemPass != null ? systemPass : "");
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