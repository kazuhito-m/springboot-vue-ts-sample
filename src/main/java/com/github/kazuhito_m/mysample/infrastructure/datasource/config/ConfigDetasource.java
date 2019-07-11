package com.github.kazuhito_m.mysample.infrastructure.datasource.config;

import com.github.kazuhito_m.mysample.domain.model.config.Config;
import com.github.kazuhito_m.mysample.domain.model.config.ConfigRepository;
import com.github.kazuhito_m.mysample.domain.model.config.Datasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
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
        LOGGER.info("設定値はきている？ " + configFilePath);

        String path = new File(".").getAbsoluteFile().getParent();
        LOGGER.info(path);
        LOGGER.info(System.getProperty("user.dir"));
        LOGGER.info("getParent: " + configFilePath.getParent());

        LOGGER.info("ファイル:" + configFilePath + "は存在する？:" + Files.exists(configFilePath));

        Properties properties = loadPropertiesOf(configFilePath);
        LOGGER.info("まずとれてるか？" + properties);
        LOGGER.info("期待されてる値が取れるか？" + properties.getProperty("main.datasource.driver-class-name"));

        // TODO 実装。以下は仮。
        return new Config(
                new Datasource(
                        "org.h2.Driver",
                        "jdbc:h2:mem:testdb;MODE=PostgreSQL",
                        "sa",
                        "sa"
                )
        );
    }

    private Properties loadPropertiesOf(Path path) {
        try (InputStream stream = Files.newInputStream(path)) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConfigDetasource(@Value("${environment.config.path}") Path configFilePath) {
        this.configFilePath = configFilePath;
    }
}
