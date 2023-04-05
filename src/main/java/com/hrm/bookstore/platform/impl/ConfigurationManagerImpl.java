package com.hrm.bookstore.platform.impl;

import com.hrm.bookstore.platform.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationManagerImpl implements ConfigurationManager {
    private final List<Properties> configs;

    public ConfigurationManagerImpl(String... classpathFiles) {
        configs = new ArrayList<>();
        for (String classpathFile : classpathFiles) {
            try (InputStream in = getClass().getResourceAsStream(classpathFile)) {
                Properties properties = new Properties();
                properties.load(in);
                configs.add(properties);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String getProperty(String key) {
        String prop = System.getenv(key);
        if (prop != null) {
            return prop;
        }

        for (Properties config : configs) {
            prop = config.getProperty(key);
            if (prop != null) {
                return prop;
            }
        }

        return null;
    }
}
