package com.nexusvision.config;

import lombok.Getter;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author felixwr
 */
public class AppConfig {
    @Getter
    private static final AppConfig instance = new AppConfig();
    private final Logger logger;
    private Properties properties;

    private AppConfig() {
        logger = LogManager.getLogger(AppConfig.class);
        loadConfiguration();
    }

    private void loadConfiguration() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/java/com/nexusvision/config/config.properties"));
        } catch (IOException e) {
            logger.error("Error while reading the properties file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public List<Integer> getPropertyIntList(String key) {
        List<Integer> result = new LinkedList<>();
        String value;
        for(int i = 0; (value = properties.getProperty(key + "." + i)) != null; i++) {
            result.add(Integer.parseInt(value));
        }
        return result;
    }
}
