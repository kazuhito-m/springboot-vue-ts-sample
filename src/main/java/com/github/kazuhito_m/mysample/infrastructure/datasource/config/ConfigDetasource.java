package com.github.kazuhito_m.mysample.infrastructure.datasource.config;

import com.github.kazuhito_m.mysample.domain.model.config.Config;
import com.github.kazuhito_m.mysample.domain.model.config.ConfigRepository;
import com.github.kazuhito_m.mysample.domain.model.config.Datasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Repository
public class ConfigDetasource implements ConfigRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDetasource.class);

    Path configFilePath;

    @Override
    public Config get() {
        Properties properties = loadPropertiesOf(configFilePath);
        return createConfigBy(properties);
    }

    private Config createConfigBy(Properties properties) {
        String prefix = "main.datasource.";
        Datasource main = createDatasourceOf("main.datasource.", properties);
        Datasource log = createDatasourceOf("log.datasource.", properties);
        return new Config(main, log);
    }

    private Datasource createDatasourceOf(String prefix, Properties properties) {
        return new Datasource(
                propertyOf(prefix + "driver-class-name", properties),
                propertyOf(prefix + "url", properties),
                propertyOf(prefix + "name", properties),
                propertyOf(prefix + "password", properties)
        );
    }

    private String propertyOf(String name, Properties properties) {
        String value = properties.getProperty(name);
        if (value == null) {
            String message = String.format("設定ファイルから期待した値が読めませんでした。key=%s, file=%s", name, configFilePath);
            throw new IllegalStateException(message);
        }
        return value;
    }

    private Properties loadPropertiesOf(Path path) {
        try (InputStream stream = Files.newInputStream(path)) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("外部設定ファイルの読み込みに失敗しました。- " + path, e);
        }
    }

    public ConfigDetasource(@Value("${environment.config.path}") Path configFilePath) {
        this.configFilePath = configFilePath;
    }
}
