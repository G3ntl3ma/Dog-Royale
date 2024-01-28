package com.nexusvision.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

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
@Log4j2
public class AppConfig {
    @Getter
    private static final AppConfig instance = new AppConfig();
    private Properties properties;

    private AppConfig() {
        loadConfiguration();
    }

    private void loadConfiguration() {
        properties = new Properties();
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            log.error("Error while reading the properties file: " + e.getMessage());
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
